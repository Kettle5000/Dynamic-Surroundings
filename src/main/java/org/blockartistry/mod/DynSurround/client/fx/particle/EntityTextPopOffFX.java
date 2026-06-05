package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.util.Color;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityTextPopOffFX extends EntityFX {
   protected static final float GRAVITY = 0.8F;
   protected static final float SIZE = 3.0F;
   protected static final int LIFESPAN = 12;
   protected static final double BOUNCE_STRENGTH = (double)1.5F;
   protected Color renderColor;
   protected String text;
   protected boolean shouldOnTop;
   protected boolean grow;
   protected float scale;

   public EntityTextPopOffFX(World world, String text, Color color, float scale, double x, double y, double z, double dX, double dY, double dZ) {
      super(world, x, y, z, dX, dY, dZ);
      this.renderColor = Color.WHITE;
      this.shouldOnTop = false;
      this.grow = true;
      this.scale = 1.0F;
      this.text = text;
      this.renderColor = color;
      this.motionX = dX;
      this.motionY = dY;
      this.motionZ = dZ;
      float dist = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      this.motionX = this.motionX / (double)dist * 0.12;
      this.motionY = this.motionY / (double)dist * 0.12;
      this.motionZ = this.motionZ / (double)dist * 0.12;
      this.particleTextureJitterX = 1.5F;
      this.particleTextureJitterY = 1.5F;
      this.particleGravity = 0.8F;
      this.particleScale = 3.0F;
      this.particleMaxAge = 12;
   }

   public void renderParticle(Tessellator p_70539_1_, float x, float y, float z, float dX, float dY, float dZ) {
      this.rotationYaw = -Minecraft.getMinecraft().thePlayer.rotationYaw;
      this.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
      float locX = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)x - interpPosX);
      float locY = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)y - interpPosY);
      float locZ = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)z - interpPosZ);
      GL11.glPushMatrix();
      if (this.shouldOnTop) {
         GL11.glDepthFunc(519);
      } else {
         GL11.glDepthFunc(515);
      }

      GL11.glTranslatef(locX, locY, locZ);
      GL11.glRotatef(this.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(this.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      GL11.glScaled((double)this.particleScale * 0.008, (double)this.particleScale * 0.008, (double)this.particleScale * 0.008);
      GL11.glScaled((double)this.scale, (double)this.scale, (double)this.scale);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2896);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
      fontRenderer.drawStringWithShadow(this.text, -MathHelper.floor_float((float)fontRenderer.getStringWidth(this.text) / 2.0F) + 1, -MathHelper.floor_float((float)fontRenderer.FONT_HEIGHT / 2.0F) + 1, this.renderColor.rgb());
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthFunc(515);
      GL11.glPopMatrix();
      if (this.grow) {
         this.particleScale *= 1.08F;
         if ((double)this.particleScale > (double)9.0F) {
            this.grow = false;
         }
      } else {
         this.particleScale *= 0.96F;
      }

   }

   public int getFXLayer() {
      return 3;
   }
}
