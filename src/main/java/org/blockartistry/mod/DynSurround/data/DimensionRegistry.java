package org.blockartistry.mod.DynSurround.data;

import gnu.trove.map.hash.TIntObjectHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.Module;
import org.blockartistry.mod.DynSurround.data.config.DimensionConfig;
import org.blockartistry.mod.DynSurround.util.DiurnalUtils;

public final class DimensionRegistry {
   private static final int SPACE_HEIGHT_OFFSET = 32;
   private static final String SEASON_NOT_AVAILABLE = "noseason";
   private static final List<DimensionConfig.Entry> cache = new ArrayList();
   private static final TIntObjectHashMap<DimensionRegistry> dimensionData = new TIntObjectHashMap();
   private static boolean isFlatWorld = false;
   protected final int dimensionId;
   protected boolean initialized;
   protected String name = "<NOT SET>";
   protected Integer seaLevel;
   protected Integer skyHeight;
   protected Integer cloudHeight;
   protected Integer spaceHeight;
   protected Boolean hasHaze;
   protected Boolean hasAuroras;
   protected Boolean hasWeather;
   private static final String CONDITION_TOKEN_RAINING = "raining";
   private static final String CONDITION_TOKEN_DAY = "day";
   private static final String CONDITION_TOKEN_NIGHT = "night";
   private static final char CONDITION_SEPARATOR = '#';

   public static void initialize() {
      try {
         process(DimensionConfig.load("dimensions"));
      } catch (Exception e) {
         e.printStackTrace();
      }

      for(String file : ModOptions.dimensionConfigFiles) {
         File theFile = new File(Module.dataDirectory(), file);
         if (theFile.exists()) {
            try {
               DimensionConfig config = DimensionConfig.load(theFile);
               if (config != null) {
                  process(config);
               } else {
                  ModLog.warn("Unable to process dimension config file " + file);
               }
            } catch (Exception ex) {
               ModLog.error("Unable to process dimension config file " + file, ex);
            }
         } else {
            ModLog.warn("Could not locate dimension config file [%s]", file);
         }
      }

      ModLog.info("*** DIMENSION REGISTRY (delay init) ***");

      for(DimensionRegistry reg : dimensionData.valueCollection()) {
         ModLog.info(reg.toString());
      }

   }

   public static void loading(World world) {
      getData(world).initialize(world.provider);
      if (world.provider.dimensionId == 0) {
         isFlatWorld = world.getWorldInfo().getTerrainType() == WorldType.FLAT;
      }

   }

   private static DimensionConfig.Entry getData(DimensionConfig.Entry entry) {
      for(DimensionConfig.Entry e : cache) {
         if (e.dimensionId != null && e.dimensionId.equals(entry.dimensionId) || e.name != null && e.name.equals(entry.name)) {
            return e;
         }
      }

      cache.add(entry);
      return entry;
   }

   private static void process(DimensionConfig config) {
      for(DimensionConfig.Entry entry : config.entries) {
         if (entry.dimensionId != null || entry.name != null) {
            DimensionConfig.Entry data = getData(entry);
            if (data != entry) {
               if (data.dimensionId == null) {
                  data.dimensionId = entry.dimensionId;
               }

               if (data.name == null) {
                  data.name = entry.name;
               }

               if (entry.hasAurora != null) {
                  data.hasAurora = entry.hasAurora;
               }

               if (entry.hasHaze != null) {
                  data.hasHaze = entry.hasHaze;
               }

               if (entry.hasWeather != null) {
                  data.hasWeather = entry.hasWeather;
               }

               if (entry.cloudHeight != null) {
                  data.cloudHeight = entry.cloudHeight;
               }

               if (entry.seaLevel != null) {
                  data.seaLevel = entry.seaLevel;
               }

               if (entry.skyHeight != null) {
                  data.skyHeight = entry.skyHeight;
               }
            }
         }
      }

   }

   protected DimensionRegistry(World world) {
      this.dimensionId = world.provider.dimensionId;
      this.initialize(world.provider);
   }

   protected DimensionRegistry(World world, DimensionConfig.Entry entry) {
      this.dimensionId = world.provider.dimensionId;
      this.name = world.provider.getDimensionName();
      this.seaLevel = entry.seaLevel;
      this.skyHeight = entry.skyHeight;
      this.hasHaze = entry.hasHaze;
      this.hasAuroras = entry.hasAurora;
      this.hasWeather = entry.hasWeather;
      this.cloudHeight = entry.cloudHeight;
      this.initialize(world.provider);
   }

   protected DimensionRegistry initialize(WorldProvider provider) {
      if (!this.initialized) {
         this.name = provider.getDimensionName();
         if (this.seaLevel == null) {
            this.seaLevel = provider.getAverageGroundLevel();
         }

         if (this.skyHeight == null) {
            this.skyHeight = provider.getHeight();
         }

         if (this.hasHaze == null) {
            this.hasHaze = !provider.hasNoSky;
         }

         if (this.hasAuroras == null) {
            this.hasAuroras = !provider.hasNoSky;
         }

         if (this.hasWeather == null) {
            this.hasWeather = !provider.hasNoSky;
         }

         if (this.cloudHeight == null) {
            this.cloudHeight = this.hasHaze ? this.skyHeight / 2 : this.skyHeight;
         }

         if (this.spaceHeight == null) {
            this.spaceHeight = this.skyHeight + 32;
         }

         this.initialized = true;
         ModLog.info("Dimension initialized " + this.toString());
      }

      return this;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public String getName() {
      return this.name;
   }

   public int getSeaLevel() {
      return this.seaLevel;
   }

   public int getSkyHeight() {
      return this.skyHeight;
   }

   public int getCloudHeight() {
      return this.cloudHeight;
   }

   public int getSpaceHeight() {
      return this.spaceHeight;
   }

   public boolean getHasHaze() {
      return this.hasHaze;
   }

   public boolean getHasAuroras() {
      return this.hasAuroras;
   }

   public boolean getHasWeather() {
      return this.hasWeather;
   }

   public String getSeason() {
      return "noseason";
   }

   public static DimensionRegistry getData(World world) {
      DimensionRegistry data = (DimensionRegistry)dimensionData.get(world.provider.dimensionId);
      if (data == null) {
         DimensionConfig.Entry entry = null;

         for(DimensionConfig.Entry e : cache) {
            if (e.dimensionId != null && e.dimensionId == world.provider.dimensionId || e.name != null && e.name.equals(world.provider.getDimensionName())) {
               entry = e;
               break;
            }
         }

         if (entry == null) {
            data = new DimensionRegistry(world);
         } else {
            data = new DimensionRegistry(world, entry);
         }

         dimensionData.put(world.provider.dimensionId, data);
      }

      return data;
   }

   public static boolean hasHaze(World world) {
      return getData(world).getHasHaze();
   }

   public static int getSeaLevel(World world) {
      return world.provider.dimensionId == 0 && isFlatWorld ? 0 : getData(world).getSeaLevel();
   }

   public static int getSkyHeight(World world) {
      return getData(world).getSkyHeight();
   }

   public static int getCloudHeight(World world) {
      return getData(world).getCloudHeight();
   }

   public static int getSpaceHeight(World world) {
      return getData(world).getSpaceHeight();
   }

   public static boolean hasAuroras(World world) {
      return getData(world).getHasAuroras();
   }

   public static boolean hasWeather(World world) {
      return getData(world).getHasWeather();
   }

   public static String getSeason(World world) {
      return getData(world).getSeason();
   }

   public static String getConditions(World world) {
      StringBuilder builder = new StringBuilder();
      builder.append('#');
      if (DiurnalUtils.isDaytime(world)) {
         builder.append("day");
      } else {
         builder.append("night");
      }

      builder.append('#').append(world.provider.getDimensionName());
      if (world.getRainStrength(1.0F) > 0.0F) {
         builder.append('#').append("raining");
      }

      builder.append('#').append(getSeason(world));
      builder.append('#');
      return builder.toString();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.dimensionId).append('/').append(this.name).append(':');
      builder.append(" seaLevel:").append(this.seaLevel);
      builder.append(" cloudH:").append(this.cloudHeight);
      builder.append(" skyH:").append(this.skyHeight);
      builder.append(" haze:").append(Boolean.toString(this.hasHaze));
      builder.append(" aurora:").append(Boolean.toString(this.hasAuroras));
      builder.append(" weather:").append(Boolean.toString(this.hasWeather));
      return builder.toString();
   }
}
