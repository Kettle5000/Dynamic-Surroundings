package org.blockartistry.mod.DynSurround.client.weather;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.blockartistry.mod.DynSurround.client.IAtmosRenderer;
import org.blockartistry.mod.DynSurround.client.WeatherUtils;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;
import org.blockartistry.mod.DynSurround.util.Color;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class StormRenderer implements IAtmosRenderer {
   private static final XorShiftRandom random = new XorShiftRandom();
   public static ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
   public static ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
   public static ResourceLocation locationDustPng = new ResourceLocation("dsurround", "textures/environment/dust.png");
   private static final float[] RAIN_X_COORDS = new float[1024];
   private static final float[] RAIN_Y_COORDS = new float[1024];

   private static int getPrecipitationHeight(World world, int x, int z) {
      return world.provider.dimensionId == -1 ? 0 : world.getPrecipitationHeight(x, z);
   }

   public void render(EntityRenderer renderer, float partialTicks) {
      Weather.setTextures();
      WorldClient world = renderer.mc.theWorld;
      IRenderHandler r = null;
      if ((r = world.provider.getWeatherRenderer()) != null) {
         r.render(partialTicks, world, renderer.mc);
      } else if (DimensionRegistry.hasWeather(world)) {
         float rainStrength = StormSplashRenderer.getEffectiveRainStrength(world, partialTicks);
         if (!(rainStrength <= 0.0F)) {
            float alphaRatio;
            if (Weather.isRaining()) {
               alphaRatio = Weather.getIntensityLevel() / Weather.getMaxIntensityLevel();
            } else {
               alphaRatio = rainStrength;
            }

            renderer.enableLightmap((double)partialTicks);
            EntityLivingBase entity = renderer.mc.renderViewEntity;
            int playerX = MathHelper.floor_double(entity.posX);
            int playerY = MathHelper.floor_double(entity.posY);
            int playerZ = MathHelper.floor_double(entity.posZ);
            GL11.glDisable(2884);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(516, 0.1F);
            double spawnX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            double spawnY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            double spawnZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            int locY = MathHelper.floor_double(spawnY);
            int b0 = renderer.mc.gameSettings.fancyGraphics ? 10 : 5;
            byte b1 = -1;
            float f5 = (float)renderer.rendererUpdateCount + partialTicks;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;

            for(int locZ = playerZ - b0; locZ <= playerZ + b0; ++locZ) {
               for(int locX = playerX - b0; locX <= playerX + b0; ++locX) {
                  int idx = (locZ - playerZ + 16) * 32 + locX - playerX + 16;
                  float f6 = RAIN_X_COORDS[idx] * 0.5F;
                  float f7 = RAIN_Y_COORDS[idx] * 0.5F;
                  BiomeGenBase biome = world.getBiomeGenForCoords(locX, locZ);
                  boolean hasDust = WeatherUtils.biomeHasDust(biome);
                  if (hasDust || BiomeRegistry.hasPrecipitation(biome)) {
                     int k1 = getPrecipitationHeight(world, locX, locZ);
                     int l1 = playerY - b0;
                     int i2 = playerY + b0;
                     if (l1 < k1) {
                        l1 = k1;
                     }

                     if (i2 < k1) {
                        i2 = k1;
                     }

                     float f8 = 1.0F;
                     int j2 = k1;
                     if (k1 < locY) {
                        j2 = locY;
                     }

                     if (l1 != i2) {
                        random.setSeed((long)(locX * locX * 3121 + locX * 45238971 ^ locZ * locZ * 418711 + locZ * 13761));
                        float heightTemp = world.getWorldChunkManager().getTemperatureAtHeight(biome.getFloatTemperature(locX, l1, locZ), k1);
                        if (!hasDust && heightTemp >= 0.15F) {
                           if (b1 != 0) {
                              if (b1 >= 0) {
                                 tessellator.draw();
                              }

                              b1 = 0;
                              renderer.mc.getTextureManager().bindTexture(locationRainPng);
                              tessellator.startDrawingQuads();
                           }

                           float f10 = ((float)(renderer.rendererUpdateCount + locX * locX * 3121 + locX * 45238971 + locZ * locZ * 418711 + locZ * 13761 & 31) + partialTicks) / 32.0F * (3.0F + random.nextFloat());
                           double deltaX = (double)((float)locX + 0.5F) - entity.posX;
                           double deltaZ = (double)((float)locZ + 0.5F) - entity.posZ;
                           float dist = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ) / (float)b0;
                           tessellator.setBrightness(world.getLightBrightnessForSkyBlocks(locX, j2, locZ, 0));
                           tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, ((1.0F - dist * dist) * 0.5F + 0.5F) * alphaRatio);
                           tessellator.setTranslation(-spawnX * (double)1.0F, -spawnY * (double)1.0F, -spawnZ * (double)1.0F);
                           tessellator.addVertexWithUV((double)((float)locX - f6) + (double)0.5F, (double)l1, (double)((float)locZ - f7) + (double)0.5F, (double)0.0F, (double)((float)l1 * 1.0F / 4.0F + f10 * 1.0F));
                           tessellator.addVertexWithUV((double)((float)locX + f6) + (double)0.5F, (double)l1, (double)((float)locZ + f7) + (double)0.5F, (double)1.0F, (double)((float)l1 * 1.0F / 4.0F + f10 * 1.0F));
                           tessellator.addVertexWithUV((double)((float)locX + f6) + (double)0.5F, (double)i2, (double)((float)locZ + f7) + (double)0.5F, (double)1.0F, (double)((float)i2 * 1.0F / 4.0F + f10 * 1.0F));
                           tessellator.addVertexWithUV((double)((float)locX - f6) + (double)0.5F, (double)i2, (double)((float)locZ - f7) + (double)0.5F, (double)0.0F, (double)((float)i2 * 1.0F / 4.0F + f10 * 1.0F));
                           tessellator.setTranslation((double)0.0F, (double)0.0F, (double)0.0F);
                        } else {
                           if (b1 != 1) {
                              if (b1 >= 0) {
                                 tessellator.draw();
                              }

                              ResourceLocation texture = locationSnowPng;
                              if (hasDust && heightTemp >= 0.15F) {
                                 texture = locationDustPng;
                              }

                              b1 = 1;
                              renderer.mc.getTextureManager().bindTexture(texture);
                              tessellator.startDrawingQuads();
                           }

                           float f10 = ((float)(renderer.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                           float factor = hasDust ? 0.2F : 0.01F;
                           float f16 = random.nextFloat() + f5 * factor * (float)random.nextGaussian();
                           float f11 = random.nextFloat() + f5 * (float)random.nextGaussian() * 0.001F;
                           double deltaX = (double)((float)locX + 0.5F) - entity.posX;
                           double deltaZ = (double)((float)locZ + 0.5F) - entity.posZ;
                           float dist = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ) / (float)b0;
                           tessellator.setBrightness((world.getLightBrightnessForSkyBlocks(locX, j2, locZ, 0) * 3 + 15728880) / 4);
                           Color color = new Color(1.0F, 1.0F, 1.0F);
                           if (hasDust) {
                              Color c = BiomeRegistry.getDustColor(biome);
                              if (c != null) {
                                 color.mix(c);
                              }
                           } else if (world.provider.dimensionId == -1) {
                              Color c = BiomeRegistry.getDustColor(biome);
                              if (c != null) {
                                 color.mix(c);
                              }
                           }

                           tessellator.setColorRGBA_F(color.red, color.green, color.blue, ((1.0F - dist * dist) * 0.3F + 0.5F) * alphaRatio);
                           tessellator.setTranslation(-spawnX * (double)1.0F, -spawnY * (double)1.0F, -spawnZ * (double)1.0F);
                           tessellator.addVertexWithUV((double)((float)locX - f6) + (double)0.5F, (double)l1, (double)((float)locZ - f7) + (double)0.5F, (double)(0.0F + f16), (double)((float)l1 * 1.0F / 4.0F + f10 * 1.0F + f11));
                           tessellator.addVertexWithUV((double)((float)locX + f6) + (double)0.5F, (double)l1, (double)((float)locZ + f7) + (double)0.5F, (double)(1.0F + f16), (double)((float)l1 * 1.0F / 4.0F + f10 * 1.0F + f11));
                           tessellator.addVertexWithUV((double)((float)locX + f6) + (double)0.5F, (double)i2, (double)((float)locZ + f7) + (double)0.5F, (double)(1.0F + f16), (double)((float)i2 * 1.0F / 4.0F + f10 * 1.0F + f11));
                           tessellator.addVertexWithUV((double)((float)locX - f6) + (double)0.5F, (double)i2, (double)((float)locZ - f7) + (double)0.5F, (double)(0.0F + f16), (double)((float)i2 * 1.0F / 4.0F + f10 * 1.0F + f11));
                           tessellator.setTranslation((double)0.0F, (double)0.0F, (double)0.0F);
                        }
                     }
                  }
               }
            }

            if (b1 >= 0) {
               tessellator.draw();
            }

            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1F);
            renderer.disableLightmap((double)partialTicks);
         }
      }
   }

   static {
      for(int i = 0; i < 32; ++i) {
         for(int j = 0; j < 32; ++j) {
            float f2 = (float)(j - 16);
            float f3 = (float)(i - 16);
            float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
            RAIN_X_COORDS[i << 5 | j] = -f3 / f4;
            RAIN_Y_COORDS[i << 5 | j] = f2 / f4;
         }
      }

   }
}
