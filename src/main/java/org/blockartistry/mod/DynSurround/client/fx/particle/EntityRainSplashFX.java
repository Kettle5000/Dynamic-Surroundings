package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityRainSplashFX extends EntityFX {
   private static final ResourceLocation TEXTURE = new ResourceLocation("dsurround", "textures/particles/rainsplash.png");
   private final float renderScale;
   private final float texU1;
   private final float texU2;
   private final float texV1;
   private final float texV2;

   public EntityRainSplashFX(World world, double x, double y, double z) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.noClip = true;
      this.particleMaxAge = 8 + this.rand.nextInt(4);
      float scale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
      int textureIdx = this.rand.nextInt(4);
      int texX = textureIdx % 2;
      int texY = textureIdx / 2;
      this.texU1 = texX * 0.5F;
      this.texU2 = this.texU1 + 0.5F;
      this.texV1 = texY * 0.5F;
      this.texV2 = this.texV1 + 0.5F;
      this.renderScale = 0.12F * scale;

      this.motionX = (this.rand.nextDouble() * 2.0D - 1.0D) * 0.4D;
      this.motionY = (this.rand.nextDouble() * 2.0D - 1.0D) * 0.4D;
      this.motionZ = (this.rand.nextDouble() * 2.0D - 1.0D) * 0.4D;
      float speed = (float)(this.rand.nextDouble() + this.rand.nextDouble() + 1.0D) * 0.15F;
      float magnitude = MathHelper.sqrt_double(
         (float)(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ));
      this.motionX = this.motionX / (double)magnitude * (double)speed * 0.4D;
      this.motionY = this.motionY / (double)magnitude * (double)speed * 0.4D + 0.1D;
      this.motionZ = this.motionZ / (double)magnitude * (double)speed * 0.4D;
      this.motionX *= 0.3D;
      this.motionY = this.rand.nextDouble() * 0.2D + 0.1D;
      this.motionZ *= 0.3D;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleAlpha = 1.0F;
      this.particleScale = scale;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= 0.06D;
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      this.motionX *= 0.98D;
      this.motionY *= 0.98D;
      this.motionZ *= 0.98D;

      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }
   }

   public void renderParticle(Tessellator tessellator, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
      float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
      float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
      float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
      float alpha = 1.0F - (float)this.particleAge / (float)this.particleMaxAge;

      GL11.glPushMatrix();
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(false);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
      Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
      GL11.glColor4f(this.particleRed, this.particleGreen, this.particleBlue, alpha);
      tessellator.startDrawingQuads();
      tessellator.setBrightness(240);
      tessellator.addVertexWithUV((double)(x - rotationX * this.renderScale - rotationXY * this.renderScale), (double)(y - rotationZ * this.renderScale), (double)(z - rotationYZ * this.renderScale - rotationXZ * this.renderScale), (double)this.texU2, (double)this.texV2);
      tessellator.addVertexWithUV((double)(x - rotationX * this.renderScale + rotationXY * this.renderScale), (double)(y + rotationZ * this.renderScale), (double)(z - rotationYZ * this.renderScale + rotationXZ * this.renderScale), (double)this.texU2, (double)this.texV1);
      tessellator.addVertexWithUV((double)(x + rotationX * this.renderScale + rotationXY * this.renderScale), (double)(y + rotationZ * this.renderScale), (double)(z + rotationYZ * this.renderScale + rotationXZ * this.renderScale), (double)this.texU1, (double)this.texV1);
      tessellator.addVertexWithUV((double)(x + rotationX * this.renderScale - rotationXY * this.renderScale), (double)(y - rotationZ * this.renderScale), (double)(z + rotationYZ * this.renderScale - rotationXZ * this.renderScale), (double)this.texU1, (double)this.texV2);
      tessellator.draw();
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
   }

   public int getFXLayer() {
      return 3;
   }
}
