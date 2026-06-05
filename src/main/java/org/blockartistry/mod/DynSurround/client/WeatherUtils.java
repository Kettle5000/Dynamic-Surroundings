package org.blockartistry.mod.DynSurround.client;

import net.minecraft.world.biome.BiomeGenBase;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.weather.Weather;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;

public class WeatherUtils {
   public static boolean biomeHasDust(BiomeGenBase biome) {
      return ModOptions.allowDesertFog && BiomeRegistry.hasDust(biome) && !Weather.doVanilla();
   }
}
