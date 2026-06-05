package org.blockartistry.mod.DynSurround.util;

import javax.annotation.Nonnull;
import net.minecraft.world.World;

public final class DiurnalUtils {
   private DiurnalUtils() {
   }

   public static boolean isDaytime(@Nonnull World world) {
      return getCycle(world) == DiurnalUtils.DayCycle.DAYTIME;
   }

   public static boolean isNighttime(@Nonnull World world) {
      return getCycle(world) == DiurnalUtils.DayCycle.NIGHTTIME;
   }

   public static boolean isSunrise(@Nonnull World world) {
      return getCycle(world) == DiurnalUtils.DayCycle.SUNRISE;
   }

   public static boolean isSunset(@Nonnull World world) {
      return getCycle(world) == DiurnalUtils.DayCycle.SUNSET;
   }

   public static DayCycle getCycle(@Nonnull World world) {
      if (world != null && !world.provider.hasNoSky) {
         float brFactor = world.provider.getSunBrightnessFactor(1.0F);
         if (brFactor > 0.6F) {
            return DiurnalUtils.DayCycle.DAYTIME;
         } else if (brFactor < 0.1F) {
            return DiurnalUtils.DayCycle.NIGHTTIME;
         } else {
            return (double)MathStuff.sin(world.getCelestialAngleRadians(1.0F)) > (double)0.0F ? DiurnalUtils.DayCycle.SUNSET : DiurnalUtils.DayCycle.SUNRISE;
         }
      } else {
         return DiurnalUtils.DayCycle.NO_SKY;
      }
   }

   public static float getMoonPhaseFactor(@Nonnull World world) {
      return world == null ? 0.0F : world.getCurrentMoonPhaseFactor();
   }

   public static boolean isAuroraVisible(@Nonnull World world) {
      return !isAuroraInvisible(world);
   }

   public static boolean isAuroraInvisible(@Nonnull World world) {
      DayCycle cycle = getCycle(world);
      return cycle == DiurnalUtils.DayCycle.SUNRISE || cycle == DiurnalUtils.DayCycle.DAYTIME;
   }

   public static long getClockTime(World world) {
      return world.getWorldTime() % 24000L;
   }

   public static enum DayCycle {
      NO_SKY,
      SUNRISE,
      SUNSET,
      DAYTIME,
      NIGHTTIME;
   }
}
