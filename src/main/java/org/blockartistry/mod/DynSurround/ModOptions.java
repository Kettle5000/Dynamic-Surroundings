package org.blockartistry.mod.DynSurround;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.util.ConfigProcessor;

public final class ModOptions {
   public static final String CATEGORY_LOGGING_CONTROL = "logging";
   public static final String CONFIG_ENABLE_DEBUG_LOGGING = "Enable Debug Logging";
   public static final String CONFIG_ENABLE_ONLINE_VERSION_CHECK = "Enable Online Version Check";
   private static final List<String> loggingSort = Arrays.asList("Enable Online Version Check", "Enable Debug Logging");
   @ConfigProcessor.Parameter(
      category = "logging",
      property = "Enable Debug Logging",
      defaultValue = "false"
   )
   @ConfigProcessor.Comment("Enables/disables debug logging of the mod")
   @ConfigProcessor.RestartRequired
   public static boolean enableDebugLogging = false;
   @ConfigProcessor.Parameter(
      category = "logging",
      property = "Enable Online Version Check",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enables/disables online version checking")
   @ConfigProcessor.RestartRequired
   public static boolean enableVersionChecking = true;
   public static final String CATEGORY_RAIN = "rain";
   public static final String CONFIG_DISABLE_WEATHER_EFFECTS = "Disable Weather Effects";
   public static final String CONFIG_RAIN_VOLUME = "Sound Level";
   public static final String CONFIG_RAIN_PARTICLE_BASE = "Particle Count Base";
   public static final String CONFIG_ALWAYS_OVERRIDE_SOUND = "Always Override Sound";
   public static final String CONFIG_ALLOW_DESERT_DUST = "Desert Dust";
   public static final String CONFIG_RESET_RAIN_ON_SLEEP = "Reset Rain on Sleep";
   private static final List<String> rainSort = Arrays.asList("Sound Level", "Disable Weather Effects", "Desert Dust", "Reset Rain on Sleep", "Always Override Sound", "Particle Count Base");
   @ConfigProcessor.Parameter(
      category = "rain",
      property = "Sound Level",
      defaultValue = "1.0"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Factor to apply to rain sound level to adjust")
   public static float soundLevel = 1.0F;
   @ConfigProcessor.Parameter(
      category = "rain",
      property = "Disable Weather Effects",
      defaultValue = "false"
   )
   @ConfigProcessor.Comment("Disable ASM related to weather effects")
   @ConfigProcessor.RestartRequired
   public static boolean disableWeatherEffects = false;
   @ConfigProcessor.Parameter(
      category = "rain",
      property = "Particle Count Base",
      defaultValue = "100"
   )
   @ConfigProcessor.MinMaxInt(
      min = 0,
      max = 500
   )
   @ConfigProcessor.Comment("Base count of rain splash particles to generate per tick")
   public static int particleCountBase = 100;
   @ConfigProcessor.Parameter(
      category = "rain",
      property = "Always Override Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Always override Vanilla rain sound even when dimension is blacklisted")
   public static boolean alwaysOverrideSound = true;
   @ConfigProcessor.Parameter(
      category = "rain",
      property = "Reset Rain on Sleep",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Reset rain/thunder when all players sleep")
   public static boolean resetRainOnSleep = true;
   public static final String CATEGORY_FOG = "fog";
   public static final String CONFIG_ALLOW_DESERT_FOG = "Desert Fog";
   public static final String CONFIG_ENABLE_ELEVATION_HAZE = "Elevation Haze";
   public static final String CONFIG_ENABLE_BIOME_FOG = "Biome Fog";
   public static final String CONFIG_ENABLE_MORNING_FOG = "Morning Fog";
   public static final String CONFIG_MORNING_FOG_CHANCE = "Morning Fog Chance";
   public static final String CONFIG_ENABLE_WEATHER_FOG = "Weather Fog";
   private static final List<String> fogSort = Arrays.asList("Desert Fog", "Elevation Haze", "Biome Fog", "Morning Fog", "Morning Fog Chance", "Weather Fog");
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Desert Fog",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Allow desert fog when raining")
   public static boolean allowDesertFog = true;
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Elevation Haze",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Higher the player elevation the more haze that is experienced")
   public static boolean enableElevationHaze = true;
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Biome Fog",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable biome specific fog density and color")
   public static boolean enableBiomeFog = true;
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Morning Fog",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable morning fog")
   public static boolean enableMorningFog = true;
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Morning Fog Chance",
      defaultValue = "1"
   )
   @ConfigProcessor.Comment("Enable morning fog")
   @ConfigProcessor.MinMaxInt(
      min = 1,
      max = 10
   )
   public static int morningFogChance = 1;
   @ConfigProcessor.Parameter(
      category = "fog",
      property = "Weather Fog",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable morning fog")
   public static boolean enableWeatherFog = true;
   public static final String CATEGORY_GENERAL = "general";
   public static final String CONFIG_MIN_RAIN_STRENGTH = "Default Minimum Rain Strength";
   public static final String CONFIG_MAX_RAIN_STRENGTH = "Default Maximum Rain Strength";
   public static final String CONFIG_FX_RANGE = "Special Effect Range";
   private static final List<String> generalSort = ImmutableList.of("Special Effect Range", "Default Minimum Rain Strength", "Default Maximum Rain Strength");
   @ConfigProcessor.Parameter(
      category = "general",
      property = "Default Minimum Rain Strength",
      defaultValue = "0.0"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Default minimum rain strength for a dimension")
   public static float defaultMinRainStrength = 0.0F;
   @ConfigProcessor.Parameter(
      category = "general",
      property = "Default Maximum Rain Strength",
      defaultValue = "1.0"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Default maximum rain strength for a dimension")
   public static float defaultMaxRainStrength = 1.0F;
   @ConfigProcessor.Parameter(
      category = "general",
      property = "Special Effect Range",
      defaultValue = "16"
   )
   @ConfigProcessor.MinMaxInt(
      min = 16,
      max = 32
   )
   @ConfigProcessor.Comment("Block radius/range around player for special effect application")
   public static int specialEffectRange = 16;
   public static final String CATEGORY_AURORA = "aurora";
   public static final String CONFIG_AURORA_ENABLED = "Enabled";
   public static final String CONFIG_Y_PLAYER_RELATIVE = "Height Player Relative";
   public static final String CONFIG_PLAYER_FIXED_HEIGHT = "Player Fixed Height";
   public static final String CONFIG_MULTIPLE_BANDS = "Multiple Bands";
   public static final String CONFIG_AURORA_ANIMATE = "Animate";
   public static final String CONFIG_AURORA_SPAWN_OFFSET = "Spawn Offset";
   private static final List<String> auroraSort = Arrays.asList("Enabled", "Animate", "Multiple Bands", "Height Player Relative", "Player Fixed Height", "Spawn Offset");
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Enabled",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Whether to enable Aurora processing on server/client")
   @ConfigProcessor.RestartRequired
   public static boolean auroraEnable = true;
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Height Player Relative",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("true to keep the aurora at a height above player; false to fix it to an altitude")
   public static boolean auroraHeightPlayerRelative = true;
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Player Fixed Height",
      defaultValue = "64.0"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 16.0F,
      max = 2048.0F
   )
   @ConfigProcessor.Comment("Number of blocks to say fixed above player if Aurora is player relative")
   public static float playerFixedHeight = 64.0F;
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Multiple Bands",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Allow Auroras with multiple bands")
   public static boolean auroraMultipleBands = true;
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Animate",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Animate Aurora so it waves")
   public static boolean auroraAnimate = true;
   @ConfigProcessor.Parameter(
      category = "aurora",
      property = "Spawn Offset",
      defaultValue = "150"
   )
   @ConfigProcessor.MinMaxInt(
      min = 0,
      max = 200
   )
   @ConfigProcessor.Comment("Number of blocks north of player location to spawn an aurora")
   public static int auroraSpawnOffset = 150;
   public static final String CATEGORY_BIOMES = "biomes";
   public static final String CONFIG_BIOME_CONFIG_FILES = "Config Files";
   public static final String CONFIG_BIOME_ALIASES = "Biome Alias";
   private static final List<String> biomesSort = Arrays.asList("Config Files", "Biome Alias");
   @ConfigProcessor.Parameter(
      category = "biomes",
      property = "Config Files",
      defaultValue = ""
   )
   @ConfigProcessor.Comment("Configuration files for configuring Biome Registry")
   @ConfigProcessor.RestartRequired
   public static String[] biomeConfigFiles = new String[0];
   @ConfigProcessor.Parameter(
      category = "biomes",
      property = "Biome Alias",
      defaultValue = ""
   )
   @ConfigProcessor.Comment("Biome alias list")
   @ConfigProcessor.RestartRequired
   public static String[] biomeAliases = new String[0];
   public static final String CATEGORY_DIMENSIONS = "dimensions";
   public static final String CONFIG_DIMENSION_CONFIG_FILES = "Config Files";
   @ConfigProcessor.Parameter(
      category = "dimensions",
      property = "Config Files",
      defaultValue = ""
   )
   @ConfigProcessor.Comment("Configuration files for configuring Dimension Registry")
   @ConfigProcessor.RestartRequired
   public static String[] dimensionConfigFiles = new String[0];
   public static final String CATEGORY_BLOCK = "block";
   public static final String CONFIG_BLOCK_CONFIG_FILES = "Config Files";
   @ConfigProcessor.Parameter(
      category = "block",
      property = "Config Files",
      defaultValue = ""
   )
   @ConfigProcessor.Comment("Configuration files for configuring Block sounds and behavior")
   @ConfigProcessor.RestartRequired
   public static String[] blockConfigFiles = new String[0];
   public static final String CATEGORY_SOUND = "sound";
   public static final String CONFIG_ENABLE_BIOME_SOUNDS = "Enable Biome Sounds";
   public static final String CONFIG_MASTER_SOUND_FACTOR = "Master Sound Scale Factor";
   public static final String CONFIG_AUTO_CONFIG_CHANNELS = "Autoconfigure Channels";
   public static final String CONFIG_NORMAL_CHANNEL_COUNT = "Number Normal Channels";
   public static final String CONFIG_STREAMING_CHANNEL_COUNT = "Number Streaming Channels";
   public static final String CONFIG_ENABLE_JUMP_SOUND = "Jump Sound";
   public static final String CONFIG_ENABLE_SWING_SOUND = "Swing Sound";
   public static final String CONFIG_ENABLE_EQUIP_SOUND = "Equip Sound";
   public static final String CONFIG_ENABLE_CRAFTING_SOUND = "Crafting Sound";
   public static final String CONFIG_ENABLE_BOW_PULL_SOUND = "Bow Pull Sound";
   public static final String CONFIG_ENABLE_FOOTSTEPS_SOUND = "Footsteps";
   public static final String CONFIG_FOOTSTEPS_SOUND_FACTOR = "Footsteps Sound Factor";
   public static final String CONFIG_SOUND_CULL_THRESHOLD = "Sound Culling Threshold";
   public static final String CONFIG_CULLED_SOUNDS = "Culled Sounds";
   public static final String CONFIG_BLOCKED_SOUNDS = "Blocked Sounds";
   public static final String CONFIG_SOUND_VOLUMES = "Sound Volume";
   private static final List<String> soundsSort = Arrays.asList("Enable Biome Sounds", "Master Sound Scale Factor", "Footsteps", "Footsteps Sound Factor", "Jump Sound", "Swing Sound", "Equip Sound", "Crafting Sound", "Bow Pull Sound", "Autoconfigure Channels", "Number Normal Channels", "Number Streaming Channels", "Blocked Sounds", "Sound Culling Threshold", "Culled Sounds", "Sound Volume");
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Enable Biome Sounds",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable biome background and spot sounds")
   @ConfigProcessor.RestartRequired
   public static boolean enableBiomeSounds = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Master Sound Scale Factor",
      defaultValue = "1.0"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Master sound scale factor for biome and block sounds")
   public static float masterSoundScaleFactor = 1.0F;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Autoconfigure Channels",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Automatically configure sound channels")
   @ConfigProcessor.RestartRequired
   public static boolean autoConfigureChannels = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Number Normal Channels",
      defaultValue = "28"
   )
   @ConfigProcessor.MinMaxInt(
      min = 28
   )
   @ConfigProcessor.Comment("Number of normal sound channels to configure in the sound system (manual)")
   @ConfigProcessor.RestartRequired
   public static int normalSoundChannelCount = 28;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Number Streaming Channels",
      defaultValue = "4"
   )
   @ConfigProcessor.MinMaxInt(
      min = 4
   )
   @ConfigProcessor.Comment("Number of streaming sound channels to configure in the sound system (manual)")
   @ConfigProcessor.RestartRequired
   public static int streamingSoundChannelCount = 4;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Jump Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable sound effect when jumping")
   @ConfigProcessor.RestartRequired
   public static boolean enableJumpSound = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Swing Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable weapons swing sound effect when attacking")
   @ConfigProcessor.RestartRequired
   public static boolean enableSwingSound = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Equip Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable weapon/tool equip sound when switching hotbar items")
   @ConfigProcessor.RestartRequired
   public static boolean enableEquipSound = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Crafting Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable sound when item crafted")
   @ConfigProcessor.RestartRequired
   public static boolean enableCraftingSound = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Bow Pull Sound",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable sound when bow is pulled")
   @ConfigProcessor.RestartRequired
   public static boolean enableBowPullSound = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Footsteps",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable footstep sounds")
   @ConfigProcessor.RestartRequired
   public static boolean enableFootstepSounds = true;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Footsteps Sound Factor",
      defaultValue = "0.15"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Sound scale factor for footstep sounds")
   public static float footstepsSoundFactor = 0.15F;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Sound Culling Threshold",
      defaultValue = "20"
   )
   @ConfigProcessor.MinMaxInt(
      min = 0
   )
   @ConfigProcessor.Comment("Ticks between culled sound events (0 to disable culling)")
   public static int soundCullingThreshold = 20;
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Culled Sounds",
      defaultValue = "^minecraft:liquid.*,minecraft:mob.sheep.say,minecraft:mob.chicken.say,minecraft:mob.cow.say,minecraft:mob.pig.say"
   )
   @ConfigProcessor.Comment("Sounds to cull from frequent playing")
   @ConfigProcessor.RestartRequired
   public static String[] culledSounds = new String[]{"^minecraft:liquid.*", "minecraft:mob.sheep.say", "minecraft:mob.chicken.say", "minecraft:mob.cow.say", "minecraft:mob.pig.say"};
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Blocked Sounds",
      defaultValue = "dsurround:bison"
   )
   @ConfigProcessor.Comment("Sounds to block from playing")
   @ConfigProcessor.Hidden
   public static String[] blockedSounds = new String[]{"dsurround:bison"};
   @ConfigProcessor.Parameter(
      category = "sound",
      property = "Sound Volume",
      defaultValue = ""
   )
   @ConfigProcessor.Comment("Individual sound scaling factors")
   @ConfigProcessor.Hidden
   public static String[] soundVolumes = new String[0];
   public static final String CATEGORY_PLAYER = "player";
   public static final String CONFIG_SUPPRESS_POTION_PARTICLES = "Suppress Potion Particles";
   public static final String CONFIG_ENABLE_POPOFFS = "Damage Popoffs";
   public static final String CONFIG_HURT_THRESHOLD = "Hurt Threshold";
   public static final String CONFIG_HUNGER_THRESHOLD = "Hunger Threshold";
   private static final List<String> playerSort = Arrays.asList("Suppress Potion Particles", "Damage Popoffs", "Hurt Threshold", "Hunger Threshold");
   @ConfigProcessor.Parameter(
      category = "player",
      property = "Suppress Potion Particles",
      defaultValue = "false"
   )
   @ConfigProcessor.Comment("Suppress player's potion particles from rendering")
   @ConfigProcessor.RestartRequired
   public static boolean suppressPotionParticles = false;
   @ConfigProcessor.Parameter(
      category = "player",
      property = "Damage Popoffs",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Controls display of damage pop-offs when an entity is damaged")
   @ConfigProcessor.RestartRequired
   public static boolean enableDamagePopoffs = true;
   @ConfigProcessor.Parameter(
      category = "player",
      property = "Hurt Threshold",
      defaultValue = "8"
   )
   @ConfigProcessor.Comment("Amount of health bar remaining to trigger player hurt sound")
   @ConfigProcessor.MinMaxInt(
      min = 0,
      max = 10
   )
   public static int playerHurtThreshold = 8;
   @ConfigProcessor.Parameter(
      category = "player",
      property = "Hunger Threshold",
      defaultValue = "8"
   )
   @ConfigProcessor.Comment("Amount of food bar remaining to trigger player hunger sound")
   @ConfigProcessor.MinMaxInt(
      min = 0,
      max = 10
   )
   public static int playerHungerThreshold = 8;
   public static final String CATEGORY_POTION_HUD = "player.potion hud";
   public static final String CONFIG_POTION_HUD_ENABLE = "Enable";
   public static final String CONFIG_POTION_HUD_TRANSPARENCY = "Transparency";
   public static final String CONFIG_POTION_HUD_LEFT_OFFSET = "Left Offset";
   public static final String CONFIG_POTION_HUD_TOP_OFFSET = "Top Offset";
   public static final String CONFIG_POTION_HUD_SCALE = "Display Scale";
   private static final List<String> potionHudSort = Arrays.asList("Enable", "Transparency", "Display Scale", "Top Offset", "Left Offset");
   @ConfigProcessor.Parameter(
      category = "player.potion hud",
      property = "Enable",
      defaultValue = "true"
   )
   @ConfigProcessor.Comment("Enable display of potion icons in display")
   @ConfigProcessor.RestartRequired
   public static boolean potionHudEnabled = true;
   @ConfigProcessor.Parameter(
      category = "player.potion hud",
      property = "Transparency",
      defaultValue = "0.5"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Transparency factor for icons (higher more solid)")
   public static float potionHudTransparency = 0.5F;
   @ConfigProcessor.Parameter(
      category = "player.potion hud",
      property = "Left Offset",
      defaultValue = "5"
   )
   @ConfigProcessor.MinMaxInt(
      min = 0
   )
   @ConfigProcessor.Comment("Offset from left side of screen")
   public static int potionHudLeftOffset = 5;
   @ConfigProcessor.Parameter(
      category = "player.potion hud",
      property = "Top Offset",
      defaultValue = "5"
   )
   @ConfigProcessor.MinMaxInt(
      min = 0
   )
   @ConfigProcessor.Comment("Offset from top of screen")
   public static int potionHudTopOffset = 5;
   @ConfigProcessor.Parameter(
      category = "player.potion hud",
      property = "Display Scale",
      defaultValue = "0.5"
   )
   @ConfigProcessor.MinMaxFloat(
      min = 0.0F,
      max = 1.0F
   )
   @ConfigProcessor.Comment("Size scale of icons (lower is smaller)")
   public static float potionHudScale = 0.5F;

   private ModOptions() {
   }

   public static void load(Configuration config) {
      ConfigProcessor.process(config, ModOptions.class);
      config.setCategoryRequiresMcRestart("logging", false);
      config.setCategoryRequiresWorldRestart("logging", false);
      config.setCategoryComment("logging", "Defines how Dynamic Surroundings logging will behave");
      config.setCategoryPropertyOrder("logging", new ArrayList(loggingSort));
      config.setCategoryRequiresMcRestart("rain", false);
      config.setCategoryRequiresWorldRestart("rain", false);
      config.setCategoryComment("rain", "Options that control rain effects in the client");
      config.setCategoryPropertyOrder("rain", new ArrayList(rainSort));
      config.setCategoryRequiresMcRestart("general", false);
      config.setCategoryRequiresWorldRestart("general", false);
      config.setCategoryComment("general", "Miscellaneous settings");
      config.setCategoryPropertyOrder("general", new ArrayList(generalSort));
      config.setCategoryRequiresMcRestart("player", false);
      config.setCategoryRequiresWorldRestart("player", false);
      config.setCategoryComment("player", "General options for defining sound and effects the player entity");
      config.setCategoryPropertyOrder("player", new ArrayList(playerSort));
      config.setCategoryRequiresMcRestart("aurora", false);
      config.setCategoryRequiresWorldRestart("aurora", false);
      config.setCategoryComment("aurora", "Options that control Aurora behavior and rendering");
      config.setCategoryPropertyOrder("aurora", new ArrayList(auroraSort));
      config.setCategoryRequiresMcRestart("fog", false);
      config.setCategoryRequiresWorldRestart("fog", false);
      config.setCategoryComment("fog", "Options that control the various fog effects in the client");
      config.setCategoryPropertyOrder("fog", new ArrayList(fogSort));
      config.setCategoryRequiresMcRestart("biomes", false);
      config.setCategoryRequiresWorldRestart("biomes", false);
      config.setCategoryComment("biomes", "Options for controlling biome sound/effects");
      config.setCategoryPropertyOrder("biomes", new ArrayList(biomesSort));
      config.setCategoryRequiresMcRestart("dimensions", false);
      config.setCategoryRequiresWorldRestart("dimensions", false);
      config.setCategoryComment("dimensions", "Options for defining per dimension parameters for Dynamic Surroundings");
      config.setCategoryRequiresMcRestart("block", false);
      config.setCategoryRequiresWorldRestart("block", false);
      config.setCategoryComment("block", "Options for defining block specific sounds/effects");
      config.setCategoryRequiresMcRestart("sound", false);
      config.setCategoryRequiresWorldRestart("sound", false);
      config.setCategoryComment("sound", "General options for defining sound effects");
      config.setCategoryPropertyOrder("sound", new ArrayList(soundsSort));
      config.setCategoryRequiresMcRestart("player.potion hud", false);
      config.setCategoryRequiresWorldRestart("player.potion hud", false);
      config.setCategoryComment("player.potion hud", "Options for the Potion HUD overlay");
      config.setCategoryPropertyOrder("player.potion hud", new ArrayList(potionHudSort));

      for(String cat : config.getCategoryNames()) {
         scrubCategory(config.getCategory(cat));
      }

   }

   private static void scrubCategory(ConfigCategory category) {
      List<String> killList = new ArrayList();

      for(Map.Entry<String, Property> entry : category.entrySet()) {
         if (StringUtils.isEmpty(((Property)entry.getValue()).comment)) {
            killList.add(entry.getKey());
         }
      }

      for(String kill : killList) {
         category.remove(kill);
      }

   }
}
