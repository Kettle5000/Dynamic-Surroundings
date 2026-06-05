package org.blockartistry.mod.DynSurround.client.weather;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.client.fx.particle.ParticleFactory;

public class NetherSplashRenderer extends StormSplashRenderer {
   protected String getBlockSoundFX(Block block, boolean hasDust, World world) {
      return hasDust ? Weather.getIntensity().getDustSound() : null;
   }

   protected EntityFX getBlockParticleFX(Block block, boolean dust, World world, double x, double y, double z) {
      return dust ? ParticleFactory.smoke.getEntityFX(0, world, x, y, z, (double)0.0F, (double)0.0F, (double)0.0F) : null;
   }

   protected int getPrecipitationHeight(World world, int range, int x, int z) {
      int y = MathHelper.floor_double(EnvironStateHandler.EnvironState.getPlayer().posY);
      boolean airBlockFound = false;

      for(int i = range; i >= -range; --i) {
         int yEffective = y + i;
         Block block = world.getBlock(x, yEffective, z);
         if (airBlockFound && block != Blocks.air && block.getMaterial().isSolid()) {
            return yEffective + 1;
         }

         if (block == Blocks.air) {
            airBlockFound = true;
         }
      }

      return 128;
   }
}
