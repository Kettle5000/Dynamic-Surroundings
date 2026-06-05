package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityWaterRippleFX extends EntityFX {
   private static final ResourceLocation TEXTURE = new ResourceLocation("dsurround", "textures/particles/ripple.png");
   private static final float TEX_SIZE_HALF = 0.5F;
   private final float growthRate;
   private float rippleScale;
   private float scaledWidth;

   public EntityWaterRippleFX(World world, double x, double y, double z) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.noClip = true;
      this.particleMaxAge = 12 + this.rand.nextInt(8);
      this.growthRate = this.particleMaxAge / 500.0F;
      this.rippleScale = this.growthRate;
      this.scaledWidth = this.rippleScale * TEX_SIZE_HALF;
      this.particleRed = 0.45F;
      this.particleGreen = 0.55F;
      this.particleBlue = 0.85F;
      this.particleAlpha = 0.0F;
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.rippleScale += this.growthRate;
      this.scaledWidth = this.rippleScale * TEX_SIZE_HALF;
      this.particleAlpha = (float)(this.particleMaxAge - this.particleAge) / (float)(this.particleMaxAge + 3);

      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }
   }

   public void renderParticle(Tessellator tessellator, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
      if (this.particleAlpha <= 0.0F) {
         return;
      }

      float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
      float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
      float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);

      GL11.glPushMatrix();
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(false);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
      Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
      GL11.glColor4f(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
      tessellator.startDrawingQuads();
      tessellator.setBrightness(240);
      tessellator.addVertexWithUV((double)(-this.scaledWidth + x), (double)y, (double)(this.scaledWidth + z), 1.0D, 1.0D);
      tessellator.addVertexWithUV((double)(this.scaledWidth + x), (double)y, (double)(this.scaledWidth + z), 1.0D, 0.0D);
      tessellator.addVertexWithUV((double)(this.scaledWidth + x), (double)y, (double)(-this.scaledWidth + z), 0.0D, 0.0D);
      tessellator.addVertexWithUV((double)(-this.scaledWidth + x), (double)y, (double)(-this.scaledWidth + z), 0.0D, 1.0D);
      tessellator.draw();
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
   }

   public int getFXLayer() {
      return 3;
   }
}
