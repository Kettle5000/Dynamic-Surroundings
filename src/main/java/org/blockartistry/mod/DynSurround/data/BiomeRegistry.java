package org.blockartistry.mod.DynSurround.data;

import cpw.mods.fml.relauncher.ReflectionHelper;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.Module;
import org.blockartistry.mod.DynSurround.client.sound.SoundEffect;
import org.blockartistry.mod.DynSurround.data.config.BiomeConfig;
import org.blockartistry.mod.DynSurround.data.config.SoundConfig;
import org.blockartistry.mod.DynSurround.event.RegistryReloadEvent;
import org.blockartistry.mod.DynSurround.util.Color;
import org.blockartistry.mod.DynSurround.util.MyUtils;

public final class BiomeRegistry {
   private static final TIntObjectHashMap<Entry> registry = new TIntObjectHashMap();
   private static final Map<String, String> biomeAliases = new HashMap();
   public static final BiomeGenBase UNDERGROUND = new FakeBiome(-1, "Underground");
   public static final BiomeGenBase PLAYER = new FakeBiome(-2, "Player");
   public static final BiomeGenBase UNDERWATER = new FakeBiome(-3, "Underwater");
   public static final BiomeGenBase UNDEROCEAN = new FakeBiome(-4, "UnderOCN");
   public static final BiomeGenBase UNDERDEEPOCEAN = new FakeBiome(-5, "UnderDOCN");
   public static final BiomeGenBase UNDERRIVER = new FakeBiome(-6, "UnderRVR");
   public static final BiomeGenBase OUTERSPACE = new FakeBiome(-7, "OuterSpace");
   public static final BiomeGenBase CLOUDS = new FakeBiome(-8, "Clouds");
   public static final SoundEffect WATER_DRIP = new SoundEffect("dsurround:waterdrops");
   private static final BiomeGenBase WTF = new FakeBiome(-256, "(FooBar)");

   public static String resolveName(BiomeGenBase biome) {
      if (biome == null) {
         return "(Bad Biome)";
      } else {
         return StringUtils.isEmpty(biome.biomeName) ? "" + '#' + biome.biomeID : biome.biomeName;
      }
   }

   public static void initialize() {
      synchronized(registry) {
         biomeAliases.clear();

         for(String entry : ModOptions.biomeAliases) {
            String[] parts = StringUtils.split(entry, "=");
            if (parts.length == 2) {
               biomeAliases.put(parts[0], parts[1]);
            }
         }

         registry.clear();
         BiomeGenBase[] biomeArray = BiomeGenBase.getBiomeGenArray();

         for(int i = 0; i < biomeArray.length; ++i) {
            if (biomeArray[i] != null) {
               registry.put(biomeArray[i].biomeID, new Entry(biomeArray[i]));
            }
         }

         registry.put(UNDERGROUND.biomeID, new Entry(UNDERGROUND));
         registry.put(UNDERWATER.biomeID, new Entry(UNDERWATER));
         registry.put(UNDEROCEAN.biomeID, new Entry(UNDEROCEAN));
         registry.put(UNDERDEEPOCEAN.biomeID, new Entry(UNDERDEEPOCEAN));
         registry.put(UNDERRIVER.biomeID, new Entry(UNDERRIVER));
         registry.put(OUTERSPACE.biomeID, new Entry(OUTERSPACE));
         registry.put(CLOUDS.biomeID, new Entry(CLOUDS));
         registry.put(PLAYER.biomeID, new Entry(PLAYER));
         registry.put(WTF.biomeID, new Entry(WTF));
         processConfig();
         if (ModOptions.enableDebugLogging) {
            ModLog.info("*** BIOME REGISTRY ***");

            for(Entry entry : registry.valueCollection()) {
               ModLog.info(entry.toString());
            }
         }

         biomeAliases.clear();
      }

      MinecraftForge.EVENT_BUS.post(new RegistryReloadEvent.Biome());
   }

   private static Entry get(BiomeGenBase biome) {
      synchronized(registry) {
         Entry entry = (Entry)registry.get(biome == null ? WTF.biomeID : biome.biomeID);
         if (entry == null) {
            ModLog.warn("Biome [%s] was not detected during initial scan! Reloading config...", resolveName(biome));
            initialize();
            entry = (Entry)registry.get(biome.biomeID);
            if (entry == null) {
               ModLog.warn("Still can't find biome [%s]! Explicitly adding at defaults", resolveName(biome));
               entry = new Entry(biome);
               registry.put(biome.biomeID, entry);
            }
         }

         return entry;
      }
   }

   public static boolean hasDust(BiomeGenBase biome) {
      return get(biome).hasDust;
   }

   public static boolean hasPrecipitation(BiomeGenBase biome) {
      return get(biome).hasPrecipitation;
   }

   public static boolean hasAurora(BiomeGenBase biome) {
      return get(biome).hasAurora;
   }

   public static boolean hasFog(BiomeGenBase biome) {
      return get(biome).hasFog;
   }

   public static Color getDustColor(BiomeGenBase biome) {
      return get(biome).dustColor;
   }

   public static Color getFogColor(BiomeGenBase biome) {
      return get(biome).fogColor;
   }

   public static float getFogDensity(BiomeGenBase biome) {
      return get(biome).fogDensity;
   }

   public static SoundEffect getSound(BiomeGenBase biome, String conditions) {
      return get(biome).findSoundMatch(conditions);
   }

   public static List<SoundEffect> getSounds(BiomeGenBase biome, String conditions) {
      return get(biome).findSoundMatches(conditions);
   }

   public static SoundEffect getSpotSound(BiomeGenBase biome, String conditions, Random random) {
      Entry e = get(biome);
      if (e != null && !e.spotSounds.isEmpty() && random.nextInt(e.spotSoundChance) == 0) {
         int totalWeight = 0;
         List<SoundEffect> candidates = new ArrayList();

         for(SoundEffect s : e.spotSounds) {
            if (s.matches(conditions)) {
               candidates.add(s);
               totalWeight += s.weight;
            }
         }

         if (totalWeight <= 0) {
            return null;
         } else if (candidates.size() == 1) {
            return (SoundEffect)candidates.get(0);
         } else {
            int targetWeight = random.nextInt(totalWeight);
            int i = 0;

            for(i = candidates.size(); (targetWeight -= ((SoundEffect)candidates.get(i - 1)).weight) >= 0; --i) {
            }

            return (SoundEffect)candidates.get(i - 1);
         }
      } else {
         return null;
      }
   }

   private static void processConfig() {
      try {
         process(BiomeConfig.load("dsurround"));
      } catch (Exception e) {
         e.printStackTrace();
      }

      String[] configFiles = ModOptions.biomeConfigFiles;

      for(String file : configFiles) {
         File theFile = new File(Module.dataDirectory(), file);
         if (theFile.exists()) {
            try {
               BiomeConfig config = BiomeConfig.load(theFile);
               if (config != null) {
                  process(config);
               } else {
                  ModLog.warn("Unable to process biome config file " + file);
               }
            } catch (Exception ex) {
               ModLog.error("Unable to process biome config file " + file, ex);
            }
         } else {
            ModLog.warn("Could not locate biome config file [%s]", file);
         }
      }

   }

   static final boolean isBiomeMatch(BiomeConfig.Entry entry, String biomeName) {
      if (Pattern.matches(entry.biomeName, biomeName)) {
         return true;
      } else {
         String alias = (String)biomeAliases.get(biomeName);
         return alias == null ? false : Pattern.matches(entry.biomeName, alias);
      }
   }

   private static void process(BiomeConfig config) {
      for(BiomeConfig.Entry entry : config.entries) {
         for(Entry biomeEntry : registry.valueCollection()) {
            if (isBiomeMatch(entry, resolveName(biomeEntry.biome))) {
               if (entry.hasPrecipitation != null) {
                  biomeEntry.hasPrecipitation = entry.hasPrecipitation;
               }

               if (entry.hasAurora != null) {
                  biomeEntry.hasAurora = entry.hasAurora;
               }

               if (entry.hasDust != null) {
                  biomeEntry.hasDust = entry.hasDust;
               }

               if (entry.hasFog != null) {
                  biomeEntry.hasFog = entry.hasFog;
               }

               if (entry.fogDensity != null) {
                  biomeEntry.fogDensity = entry.fogDensity;
               }

               if (entry.fogColor != null) {
                  int[] rgb = MyUtils.splitToInts(entry.fogColor, ',');
                  if (rgb.length == 3) {
                     biomeEntry.fogColor = new Color(rgb[0], rgb[1], rgb[2]);
                  }
               }

               if (entry.dustColor != null) {
                  int[] rgb = MyUtils.splitToInts(entry.dustColor, ',');
                  if (rgb.length == 3) {
                     biomeEntry.dustColor = new Color(rgb[0], rgb[1], rgb[2]);
                  }
               }

               if (entry.soundReset != null && entry.soundReset) {
                  biomeEntry.sounds = new ArrayList();
                  biomeEntry.spotSounds = new ArrayList();
               }

               if (entry.spotSoundChance != null) {
                  biomeEntry.spotSoundChance = entry.spotSoundChance;
               }

               for(SoundConfig sr : entry.sounds) {
                  if (!SoundRegistry.isSoundBlocked(sr.sound)) {
                     SoundEffect s = new SoundEffect(sr);
                     if (s.type == SoundEffect.SoundType.SPOT) {
                        biomeEntry.spotSounds.add(s);
                     } else {
                        biomeEntry.sounds.add(s);
                     }
                  }
               }
            }
         }
      }

   }

   private static class Entry {
      private static Class<?> bopBiome = null;
      private static Field bopBiomeFogDensity = null;
      private static Field bopBiomeFogColor = null;
      public final BiomeGenBase biome;
      public boolean hasPrecipitation;
      public boolean hasDust;
      public boolean hasAurora;
      public boolean hasFog;
      public Color dustColor;
      public Color fogColor;
      public float fogDensity;
      public List<SoundEffect> sounds;
      public int spotSoundChance;
      public List<SoundEffect> spotSounds;

      public Entry(BiomeGenBase biome) {
         this.biome = biome;
         this.hasPrecipitation = biome.canSpawnLightningBolt() || biome.getEnableSnow();
         this.sounds = new ArrayList();
         this.spotSounds = new ArrayList();
         this.spotSoundChance = 1200;
         if (bopBiome != null && bopBiome.isInstance(biome)) {
            try {
               int color = bopBiomeFogColor.getInt(biome);
               if (color > 0) {
                  this.hasFog = true;
                  this.fogColor = new Color(color);
                  this.fogDensity = bopBiomeFogDensity.getFloat(biome);
               }
            } catch (Exception var3) {
            }
         }

      }

      public SoundEffect findSoundMatch(String conditions) {
         for(SoundEffect sound : this.sounds) {
            if (sound.matches(conditions)) {
               return sound;
            }
         }

         return null;
      }

      public List<SoundEffect> findSoundMatches(String conditions) {
         List<SoundEffect> results = new ArrayList();

         for(SoundEffect sound : this.sounds) {
            if (sound.matches(conditions)) {
               results.add(sound);
            }
         }

         return results;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(String.format("Biome %d [%s]:", this.biome.biomeID, BiomeRegistry.resolveName(this.biome)));
         if (this.hasPrecipitation) {
            builder.append(" PRECIPITATION");
         }

         if (this.hasDust) {
            builder.append(" DUST");
         }

         if (this.hasAurora) {
            builder.append(" AURORA");
         }

         if (this.hasFog) {
            builder.append(" FOG");
         }

         if (!this.hasPrecipitation && !this.hasDust && !this.hasAurora && !this.hasFog) {
            builder.append(" NONE");
         }

         if (this.dustColor != null) {
            builder.append(" dustColor:").append(this.dustColor.toString());
         }

         if (this.fogColor != null) {
            builder.append(" fogColor:").append(this.fogColor.toString());
            builder.append(" fogDensity:").append(this.fogDensity);
         }

         if (!this.sounds.isEmpty()) {
            builder.append("; sounds [");

            for(SoundEffect sound : this.sounds) {
               builder.append(sound.toString()).append(',');
            }

            builder.append(']');
         }

         if (!this.spotSounds.isEmpty()) {
            builder.append("; spot sound chance:").append(this.spotSoundChance);
            builder.append(" spot sounds [");

            for(SoundEffect sound : this.spotSounds) {
               builder.append(sound.toString()).append(',');
            }

            builder.append(']');
         }

         return builder.toString();
      }

      static {
         try {
            bopBiome = Class.forName("biomesoplenty.common.biome.BOPBiome");
            bopBiomeFogDensity = ReflectionHelper.findField(bopBiome, new String[]{"fogDensity"});
            bopBiomeFogColor = ReflectionHelper.findField(bopBiome, new String[]{"fogColor"});
         } catch (Throwable var1) {
            bopBiome = null;
            bopBiomeFogDensity = null;
            bopBiomeFogColor = null;
         }

      }
   }
}
