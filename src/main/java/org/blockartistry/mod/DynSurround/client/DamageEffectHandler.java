package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.Potion;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.fx.particle.EntityCriticalPopOffFX;
import org.blockartistry.mod.DynSurround.client.fx.particle.EntityDamagePopOffFX;
import org.blockartistry.mod.DynSurround.client.fx.particle.EntityHealPopOffFX;
import org.blockartistry.mod.DynSurround.network.Network;

public final class DamageEffectHandler {
   private static final double DISTANCE_THRESHOLD_SQ = (double)1024.0F;

   private DamageEffectHandler() {
   }

   public static void initialize() {
      if (ModOptions.enableDamagePopoffs) {
         MinecraftForge.EVENT_BUS.register(new DamageEffectHandler());
      }

   }

   private static boolean isCritical(EntityPlayer player, Entity target) {
      return player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && target instanceof EntityLivingBase;
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onLivingHurt(LivingHurtEvent event) {
      if (event != null && event.entity != null && event.entity.worldObj != null && !event.entity.worldObj.isRemote) {
         if (!(event.ammount <= 0.0F) && event.entityLiving != null) {
            boolean isCrit = false;
            if (event.source instanceof EntityDamageSourceIndirect) {
               EntityDamageSourceIndirect dmgSource = (EntityDamageSourceIndirect)event.source;
               if (dmgSource.getSourceOfDamage() instanceof EntityArrow) {
                  EntityArrow arrow = (EntityArrow)dmgSource.getSourceOfDamage();
                  isCrit = arrow.getIsCritical();
               }
            } else if (event.source instanceof EntityDamageSource) {
               EntityDamageSource dmgSource = (EntityDamageSource)event.source;
               if (dmgSource.getSourceOfDamage() instanceof EntityPlayer) {
                  EntityPlayer player = (EntityPlayer)dmgSource.getSourceOfDamage();
                  isCrit = isCritical(player, event.entityLiving);
               }
            }

            HealthData data = new HealthData(event.entityLiving, isCrit, (int)event.ammount);
            Network.sendHealthUpdate(data, event.entity.worldObj.provider.dimensionId);
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onLivingHeal(LivingHealEvent event) {
      if (event != null && event.entity != null && event.entity.worldObj != null && !event.entity.worldObj.isRemote) {
         if (!(event.amount <= 0.0F) && event.entityLiving != null && event.entityLiving.getHealth() != event.entityLiving.getMaxHealth()) {
            HealthData data = new HealthData(event.entityLiving, false, -((int)event.amount));
            Network.sendHealthUpdate(data, event.entity.worldObj.provider.dimensionId);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public static void handleEvent(HealthData data) {
      if (ModOptions.enableDamagePopoffs) {
         if (!EnvironStateHandler.EnvironState.isPlayer(data.entityId)) {
            double distance = EnvironStateHandler.EnvironState.distanceToPlayer((double)data.posX, (double)data.posY, (double)data.posZ);
            if (!(distance >= (double)1024.0F)) {
               World world = EnvironStateHandler.EnvironState.getWorld();
               EffectRenderer renderer = Minecraft.getMinecraft().effectRenderer;
               if (data.isCritical) {
                  EntityFX fx = new EntityCriticalPopOffFX(world, (double)data.posX, (double)data.posY, (double)data.posZ);
                  renderer.addEffect(fx);
               }

               EntityFX fx;
               if (data.amount > 0) {
                  fx = new EntityDamagePopOffFX(world, (double)data.posX, (double)data.posY, (double)data.posZ, data.amount);
               } else {
                  fx = new EntityHealPopOffFX(world, (double)data.posX, (double)data.posY, (double)data.posZ, MathHelper.abs_int(data.amount));
               }

               renderer.addEffect(fx);
            }
         }
      }
   }

   public static class HealthData {
      public final UUID entityId;
      public final float posX;
      public final float posY;
      public final float posZ;
      public final boolean isCritical;
      public final int amount;

      public HealthData(Entity entity, boolean isCritical, int amount) {
         this.entityId = entity.getUniqueID();
         this.posX = (float)entity.posX;
         this.posY = (float)entity.posY + entity.height;
         this.posZ = (float)entity.posZ;
         this.isCritical = isCritical;
         this.amount = amount;
      }

      public HealthData(UUID id, float x, float y, float z, boolean isCritical, int amount) {
         this.entityId = id;
         this.posX = x;
         this.posY = y;
         this.posZ = z;
         this.isCritical = isCritical;
         this.amount = amount;
      }
   }
}
