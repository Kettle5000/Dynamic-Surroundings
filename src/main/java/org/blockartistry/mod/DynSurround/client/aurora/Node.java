package org.blockartistry.mod.DynSurround.client.aurora;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.blockartistry.mod.DynSurround.util.MathStuff;

@SideOnly(Side.CLIENT)
public final class Node {
   private static final float COS_DEG90_FACTOR = MathStuff.cos(((float)Math.PI / 2F));
   private static final float COS_DEG270_FACTOR = MathStuff.cos(((float)Math.PI * 1.5F));
   private static final float SIN_DEG90_FACTOR = MathStuff.sin(((float)Math.PI / 2F));
   private static final float SIN_DEG270_FACTOR = MathStuff.sin(((float)Math.PI * 1.5F));
   private float dZ = 0.0F;
   private float dY = 0.0F;
   private float cosDeg90 = 0.0F;
   private float cosDeg270 = 0.0F;
   private float sinDeg90 = 0.0F;
   private float sinDeg270 = 0.0F;
   public float angle;
   public float posX;
   public float posY;
   public float posZ;
   public float tetX = 0.0F;
   public float tetX2 = 0.0F;
   public float tetZ = 0.0F;
   public float tetZ2 = 0.0F;

   public Node(Node template, int offset) {
      float rads = MathStuff.toRadians(90.0F + template.angle);
      this.posX = template.posX + MathStuff.cos(rads) * (float)offset;
      this.posY = template.posY - 2.0F;
      this.posZ = template.posZ + MathStuff.sin(rads) * (float)offset;
      this.angle = template.angle;
   }

   public Node(float x, float y, float z, float theta) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.angle = theta;
   }

   public void setDeltaZ(float f) {
      this.dZ = f;
   }

   public void setDeltaY(float f) {
      this.dY = f;
   }

   public float getModdedZ() {
      return this.posZ + this.dZ;
   }

   public float getModdedY() {
      float y = this.posY + this.dY;
      return y < 0.0F ? 0.0F : y;
   }

   public void setWidth(float w) {
      this.cosDeg270 = COS_DEG270_FACTOR * w;
      this.cosDeg90 = COS_DEG90_FACTOR * w;
      this.sinDeg270 = SIN_DEG270_FACTOR * w;
      this.sinDeg90 = SIN_DEG90_FACTOR * w;
   }

   public void findAngles(Node next) {
      this.tetX = this.tetX2 = this.posX;
      this.tetZ = this.tetZ2 = this.getModdedZ();
      if (next != null) {
         this.tetX += this.cosDeg90;
         this.tetX2 += this.cosDeg270;
         this.tetZ += this.sinDeg90;
         this.tetZ2 += this.sinDeg270;
      }

   }
}
