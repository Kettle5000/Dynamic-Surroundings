package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityFountainJetFX extends EntityJetFX {
   protected final Block block;

   public EntityFountainJetFX(int strength, World world, double x, double y, double z, Block block) {
      super(strength, world, x, y, z, 1);
      this.block = block;
   }

   protected EntityFX getJetParticle() {
      double motionX = RANDOM.nextGaussian() * 0.03;
      double motionZ = RANDOM.nextGaussian() * 0.03;
      return new EntityFoundtainParitcleFX(this.worldObj, this.posX, this.posY, this.posZ, motionX, (double)0.5F, motionZ, this.block);
   }

   protected static final class EntityFoundtainParitcleFX extends EntityBlockDustFX {
      public EntityFoundtainParitcleFX(World world, double x, double y, double z, double dX, double dY, double dZ, Block block) {
         super(world, x + EntityJetFX.RANDOM.nextGaussian() * 0.2, y, z + EntityJetFX.RANDOM.nextGaussian() * 0.2, dX, dY, dZ, block, 0);
         this.multipleParticleScaleBy((float)((double)0.3F + EntityJetFX.RANDOM.nextGaussian() / (double)10.0F));
         this.setPosition(this.posX, this.posY, this.posZ);
      }
   }
}
