package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityBubbleJetFX extends EntityJetFX {
   public EntityBubbleJetFX(int strength, World world, double x, double y, double z) {
      super(strength, world, x, y, z);
   }

   protected EntityFX getJetParticle() {
      return new EntityBubbleFX(this.worldObj, this.posX, this.posY, this.posZ, (double)0.0F, (double)0.5F + (double)this.jetStrength / (double)10.0F, (double)0.0F);
   }
}
