package org.blockartistry.mod.DynSurround.data.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.List;
import org.blockartistry.mod.DynSurround.util.JsonUtils;

public final class BiomeConfig {
   public List<Entry> entries = ImmutableList.of();

   public static BiomeConfig load(File file) throws Exception {
      return (BiomeConfig)JsonUtils.load(file, BiomeConfig.class);
   }

   public static BiomeConfig load(String modId) throws Exception {
      return (BiomeConfig)JsonUtils.load(modId, BiomeConfig.class);
   }

   public static class Entry {
      @SerializedName("biomeName")
      public String biomeName = null;
      @SerializedName("precipitation")
      public Boolean hasPrecipitation = null;
      @SerializedName("dust")
      public Boolean hasDust = null;
      @SerializedName("aurora")
      public Boolean hasAurora = null;
      @SerializedName("fog")
      public Boolean hasFog = null;
      @SerializedName("dustColor")
      public String dustColor = null;
      @SerializedName("fogColor")
      public String fogColor = null;
      @SerializedName("fogDensity")
      public Float fogDensity = null;
      @SerializedName("soundReset")
      public Boolean soundReset = null;
      @SerializedName("spotSoundChance")
      public Integer spotSoundChance = null;
      @SerializedName("sounds")
      public List<SoundConfig> sounds = ImmutableList.of();
   }
}
