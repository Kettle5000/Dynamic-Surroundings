package org.blockartistry.mod.DynSurround.client.aurora;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.AuroraEffectHandler;
import org.blockartistry.mod.DynSurround.client.IAtmosRenderer;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;
import org.blockartistry.mod.DynSurround.util.Color;
import org.blockartistry.mod.DynSurround.util.DiurnalUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

@SideOnly(Side.CLIENT)
public final class AuroraRenderer implements IAtmosRenderer {
   public void render(EntityRenderer renderer, float partialTick) {
      if (AuroraEffectHandler.currentAurora != null) {
         renderAurora(partialTick, AuroraEffectHandler.currentAurora);
      }

   }

   private static void setColor(Color color, float alpha) {
      Tessellator.instance.setColorRGBA_F(color.red, color.green, color.blue, alpha);
   }

   public static float moonlightFactor(World world) {
      float moonFactor = 1.0F - DiurnalUtils.getMoonPhaseFactor(world) * 1.1F;
      return moonFactor <= 0.0F ? 0.0F : MathHelper.clamp_float(moonFactor * moonFactor, 0.0F, 1.0F);
   }

   public static void renderAurora(float partialTick, Aurora aurora) {
      Minecraft mc = FMLClientHandler.instance().getClient();
      float alpha = (float)aurora.getAlpha() * moonlightFactor(mc.theWorld) / 255.0F;
      if (!(alpha <= 0.0F)) {
         Tessellator tess = Tessellator.instance;
         float tranY;
         if (ModOptions.auroraHeightPlayerRelative) {
            tranY = ModOptions.playerFixedHeight;
         } else {
            tranY = (float)(DimensionRegistry.getCloudHeight(mc.theWorld) + 5) - (float)(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)partialTick);
         }

         double tranX = (double)aurora.posX - (mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)partialTick);
         double tranZ = (double)aurora.posZ - (mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)partialTick);
         if (ModOptions.auroraAnimate) {
            aurora.translate(partialTick);
         }

         Color base = aurora.getBaseColor();
         Color fade = aurora.getFadeColor();
         double lowY = (double)0.0F;
         double lowY2 = (double)0.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)tranX, tranY, (float)tranZ);
         GL11.glScaled((double)0.5F, (double)8.0F, (double)0.5F);
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glShadeModel(7425);
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 1, 1, 0);
         GL11.glDisable(3008);
         GL11.glDisable(2884);
         GL11.glDepthMask(false);
         tess.startDrawing(4);

         for(Node[] array : aurora.getNodeList()) {
            for(int i = 0; i < array.length - 1; ++i) {
               Node node = array[i];
               double posY = (double)node.getModdedY();
               double posX = (double)node.tetX;
               double posZ = (double)node.tetZ;
               double tetX = (double)node.tetX2;
               double tetZ = (double)node.tetZ2;
               double posX2;
               double posZ2;
               double tetX2;
               double tetZ2;
               double posY2;
               if (i < array.length - 2) {
                  Node nodePlus = array[i + 1];
                  posX2 = (double)nodePlus.tetX;
                  posZ2 = (double)nodePlus.tetZ;
                  tetX2 = (double)nodePlus.tetX2;
                  tetZ2 = (double)nodePlus.tetZ2;
                  posY2 = (double)nodePlus.getModdedY();
               } else {
                  posX2 = tetX2 = (double)node.posX;
                  posZ2 = tetZ2 = (double)node.getModdedZ();
                  posY2 = (double)0.0F;
               }

               setColor(base, alpha);
               tess.addVertex(posX, (double)0.0F, posZ);
               setColor(fade, 0.0F);
               tess.addVertex(posX, posY, posZ);
               tess.addVertex(posX2, posY2, posZ2);
               tess.addVertex(posX2, posY2, posZ2);
               setColor(base, alpha);
               tess.addVertex(posX2, (double)0.0F, posZ2);
               tess.addVertex(posX, (double)0.0F, posZ);
               setColor(base, alpha);
               tess.addVertex(posX, (double)0.0F, posZ);
               tess.addVertex(posX2, (double)0.0F, posZ2);
               tess.addVertex(tetX2, (double)0.0F, tetZ2);
               tess.addVertex(tetX2, (double)0.0F, tetZ2);
               tess.addVertex(tetX, (double)0.0F, tetZ);
               tess.addVertex(posX, (double)0.0F, posZ);
               setColor(base, alpha);
               tess.addVertex(tetX, (double)0.0F, tetZ);
               setColor(fade, 0.0F);
               tess.addVertex(tetX, posY, tetZ);
               tess.addVertex(tetX2, posY2, tetZ2);
               tess.addVertex(tetX2, posY2, tetZ2);
               setColor(base, alpha);
               tess.addVertex(tetX2, (double)0.0F, tetZ2);
               tess.addVertex(tetX, (double)0.0F, tetZ);
            }
         }

         tess.draw();
         GL11.glScaled((double)3.5F, (double)25.0F, (double)3.5F);
         GL11.glDepthMask(true);
         GL11.glEnable(2884);
         GL11.glEnable(2896);
         GL11.glDisable(3042);
         GL11.glShadeModel(7424);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(3553);
         GL11.glEnable(3008);
         GL11.glPopMatrix();
      }
   }
}
