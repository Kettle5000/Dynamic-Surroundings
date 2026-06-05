package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.compat.IParticleFactory;

@SideOnly(Side.CLIENT)
public class ParticleFactory {
   public static final IParticleFactory lavaSpark = new IParticleFactory() {
      public EntityFX getEntityFX(int particleID, World world, double x, double y, double z, double dX, double dY, double dZ, int... misc) {
         EntityLavaFX fx = new EntityLavaFX(world, x, y, z);
         return fx;
      }
   };
   public static final IParticleFactory smoke = new IParticleFactory() {
      public EntityFX getEntityFX(int particleID, World world, double x, double y, double z, double dX, double dY, double dZ, int... misc) {
         EntitySmokeFX fx = new EntitySmokeFX(world, x, y, z, dX, dY, dZ);
         return fx;
      }
   };
   public static final IParticleFactory rain = new IParticleFactory() {
      public EntityFX getEntityFX(int particleID, World world, double x, double y, double z, double dX, double dY, double dZ, int... misc) {
         EntityRainFX fx = new EntityRainFX(world, x, y, z);
         return fx;
      }
   };
   public static final IParticleFactory rainSplash = new IParticleFactory() {
      public EntityFX getEntityFX(int particleID, World world, double x, double y, double z, double dX, double dY, double dZ, int... misc) {
         return new EntityRainSplashFX(world, x, y, z);
      }
   };
   public static final IParticleFactory waterRipple = new IParticleFactory() {
      public EntityFX getEntityFX(int particleID, World world, double x, double y, double z, double dX, double dY, double dZ, int... misc) {
         return new EntityWaterRippleFX(world, x, y, z);
      }
   };

   private ParticleFactory() {
   }
}
