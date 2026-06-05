package org.blockartistry.mod.DynSurround.client.weather;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;

@SideOnly(Side.CLIENT)
public enum Weather {
   VANILLA,
   NONE(0.0F, "calm"),
   CALM(0.1F, "calm"),
   LIGHT(0.33F, "light"),
   NORMAL(0.66F, "normal"),
   HEAVY(1.0F, "heavy");

   private static float intensityLevel = 0.0F;
   private static Weather intensity = VANILLA;
   private final float level;
   private final ResourceLocation rainTexture;
   private final ResourceLocation snowTexture;
   private final ResourceLocation dustTexture;
   private final String rainSound;
   private final String dustSound;

   private Weather() {
      this.level = -10.0F;
      this.rainTexture = EntityRenderer.locationRainPng;
      this.snowTexture = EntityRenderer.locationSnowPng;
      this.dustTexture = new ResourceLocation("dsurround", "textures/environment/dust_calm.png");
      this.rainSound = String.format("%s:%s", "dsurround", "rain");
      this.dustSound = String.format("%s:%s", "dsurround", "dust");
   }

   private Weather(float level, String intensity) {
      this.level = level;
      this.rainTexture = new ResourceLocation("dsurround", String.format("textures/environment/rain_%s.png", intensity));
      this.snowTexture = new ResourceLocation("dsurround", String.format("textures/environment/snow_%s.png", intensity));
      this.dustTexture = new ResourceLocation("dsurround", String.format("textures/environment/dust_%s.png", intensity));
      this.rainSound = String.format("%s:%s", "dsurround", "rain");
      this.dustSound = String.format("%s:%s", "dsurround", "dust");
   }

   private static World getWorld() {
      return Minecraft.getMinecraft().theWorld;
   }

   public static Weather getIntensity() {
      return intensity;
   }

   public static float getIntensityLevel() {
      return Math.min(getWorld().rainingStrength, intensityLevel);
   }

   public static float getMaxIntensityLevel() {
      return intensityLevel;
   }

   public static boolean isRaining() {
      return getIntensityLevel() > 0.0F;
   }

   public static float getRainStrength() {
      return getWorld().rainingStrength;
   }

   public static float getThunderStrength() {
      return getWorld().thunderingStrength;
   }

   public String getStormSound() {
      return this.rainSound;
   }

   public String getDustSound() {
      return this.dustSound;
   }

   public static float getCurrentVolume() {
      return (doVanilla() ? 0.66F : intensityLevel) * ModOptions.soundLevel;
   }

   public static ResourceLocation getCurrentStormSound() {
      return new ResourceLocation(intensity.rainSound);
   }

   public static ResourceLocation getCurrentDustSound() {
      return new ResourceLocation(intensity.dustSound);
   }

   public static boolean doVanilla() {
      return intensity == VANILLA || ModOptions.disableWeatherEffects;
   }

   public static void setIntensity(float level) {
      if (level == VANILLA.level) {
         intensity = VANILLA;
         intensityLevel = 0.0F;
         setTextures();
      } else {
         level = MathHelper.clamp_float(level, 0.0F, 1.0F);
         if (intensityLevel != level) {
            intensityLevel = level;
            if (level > 0.0F) {
               level = (float)((double)level + 0.01);
            }

            if (intensityLevel <= NONE.level) {
               intensity = NONE;
            } else if (intensityLevel < CALM.level) {
               intensity = CALM;
            } else if (intensityLevel < LIGHT.level) {
               intensity = LIGHT;
            } else if (intensityLevel < NORMAL.level) {
               intensity = NORMAL;
            } else {
               intensity = HEAVY;
            }
         }

      }
   }

   public static void setTextures() {
      StormRenderer.locationRainPng = intensity.rainTexture;
      StormRenderer.locationSnowPng = intensity.snowTexture;
      StormRenderer.locationDustPng = intensity.dustTexture;
   }

   public static String diagnostic() {
      StringBuilder builder = new StringBuilder();
      builder.append("Storm: ").append(intensity.name());
      builder.append(" level:").append(intensityLevel);
      builder.append(" str:").append(EnvironStateHandler.EnvironState.getWorld().getRainStrength(1.0F));
      return builder.toString();
   }
}
