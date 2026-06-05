package org.blockartistry.mod.DynSurround.data.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.List;
import org.blockartistry.mod.DynSurround.util.JsonUtils;

public final class BlockConfig {
   public List<Entry> entries = ImmutableList.of();

   public static BlockConfig load(File file) throws Exception {
      return (BlockConfig)JsonUtils.load(file, BlockConfig.class);
   }

   public static BlockConfig load(String modId) throws Exception {
      return (BlockConfig)JsonUtils.load(modId, BlockConfig.class);
   }

   public static class Effect {
      @SerializedName("effect")
      public String effect = null;
      @SerializedName("chance")
      public Integer chance = null;
   }

   public static class Entry {
      @SerializedName("blocks")
      public List<String> blocks = ImmutableList.of();
      @SerializedName("soundReset")
      public Boolean soundReset = null;
      @SerializedName("effectReset")
      public Boolean effectReset = null;
      @SerializedName("stepSoundReset")
      public Boolean stepSoundReset = null;
      @SerializedName("chance")
      public Integer chance = null;
      @SerializedName("stepChance")
      public Integer stepChance = null;
      @SerializedName("sounds")
      public List<SoundConfig> sounds = ImmutableList.of();
      @SerializedName("effects")
      public List<Effect> effects = ImmutableList.of();
   }
}
