package org.blockartistry.mod.DynSurround.server;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.data.DimensionEffectData;
import org.blockartistry.mod.DynSurround.data.DimensionEffectDataFile;

public class WorldHandler {
   /**
    * Applies mod rain intensity on top of vanilla weather. Called from world tick events
    * instead of patching {@code World.updateWeatherBody} so mixin mods (e.g. ArchaicFix) stay compatible.
    */
   public static void applyRainIntensity(World world) {
      if (world.isRemote) {
         return;
      }

      DimensionEffectData data = DimensionEffectDataFile.get(world);
      WorldInfo info = world.getWorldInfo();
      int dimensionId = world.provider.dimensionId;

      if (info.isRaining() && data.getRainIntensity() == 0.0F) {
         data.randomizeRain();
         ModLog.debug(String.format("dim %d rain strength set to %f", dimensionId, data.getRainIntensity()));
      }

      if (info.isRaining()) {
         if (world.rainingStrength > data.getRainIntensity()) {
            world.rainingStrength -= 0.01F;
            if (world.rainingStrength < 0.0F) {
               world.rainingStrength = 0.0F;
            }
         } else if (world.rainingStrength < data.getRainIntensity()) {
            world.rainingStrength += 0.01F;
            if (world.rainingStrength > data.getRainIntensity()) {
               world.rainingStrength = data.getRainIntensity();
            }
         }
      } else if (world.rainingStrength > 0.0F) {
         world.rainingStrength -= 0.01F;
         if (world.rainingStrength < 0.0F) {
            world.rainingStrength = 0.0F;
         }
      } else if (data.getRainIntensity() > 0.0F) {
         data.setRainIntensity(0.0F);
         ModLog.debug(String.format("dim %d rain has stopped", dimensionId));
      }
   }
}
