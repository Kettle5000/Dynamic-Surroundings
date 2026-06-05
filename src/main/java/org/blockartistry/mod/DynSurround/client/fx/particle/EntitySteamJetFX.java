package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntitySteamJetFX extends EntityJetFX {
   public EntitySteamJetFX(int strength, World world, double x, double y, double z) {
      super(strength, world, x, y, z);
   }

   protected EntityFX getJetParticle() {
      double motionX = RANDOM.nextGaussian() * 0.02;
      double motionZ = RANDOM.nextGaussian() * 0.02;
      return new EntitySteamCloudFX(this.worldObj, this.posX, this.posY, this.posZ, motionX, (double)0.1F, motionZ);
   }

   protected static final class EntitySteamCloudFX extends EntityCloudFX {
      public EntitySteamCloudFX(World world, double x, double y, double z, double dX, double dY, double dZ) {
         super(world, x, y, z, dX, dY, dZ);
      }

      public void onUpdate() {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
         }

         this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         this.motionX *= (double)0.96F;
         this.motionY *= (double)0.96F;
         this.motionZ *= (double)0.96F;
         if (this.onGround) {
            this.motionX *= (double)0.7F;
            this.motionZ *= (double)0.7F;
         }

      }
   }
}
