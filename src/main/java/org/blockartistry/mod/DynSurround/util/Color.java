package org.blockartistry.mod.DynSurround.util;

import javax.annotation.Nonnull;
import net.minecraft.util.Vec3;

public class Color {
   public static final Color RED = new ImmutableColor(255, 0, 0);
   public static final Color ORANGE = new ImmutableColor(255, 127, 0);
   public static final Color YELLOW = new ImmutableColor(255, 255, 0);
   public static final Color LGREEN = new ImmutableColor(127, 255, 0);
   public static final Color GREEN = new ImmutableColor(0, 255, 0);
   public static final Color TURQOISE = new ImmutableColor(0, 255, 127);
   public static final Color CYAN = new ImmutableColor(0, 255, 255);
   public static final Color AUQUAMARINE = new ImmutableColor(0, 127, 255);
   public static final Color BLUE = new ImmutableColor(0, 0, 255);
   public static final Color VIOLET = new ImmutableColor(127, 0, 255);
   public static final Color MAGENTA = new ImmutableColor(255, 0, 255);
   public static final Color RASPBERRY = new ImmutableColor(255, 0, 127);
   public static final Color BLACK = new ImmutableColor(0, 0, 0);
   public static final Color WHITE = new ImmutableColor(255, 255, 255);
   public static final Color PURPLE = new ImmutableColor(80, 0, 80);
   public static final Color INDIGO = new ImmutableColor(75, 0, 130);
   public static final Color NAVY = new ImmutableColor(0, 0, 128);
   public static final Color TAN = new ImmutableColor(210, 180, 140);
   public static final Color GOLD = new ImmutableColor(255, 215, 0);
   public static final Color GRAY = new ImmutableColor(128, 128, 128);
   public static final Color LGRAY = new ImmutableColor(192, 192, 192);
   public static final Color SLATEGRAY = new ImmutableColor(112, 128, 144);
   public static final Color DARKSLATEGRAY = new ImmutableColor(47, 79, 79);
   public static final Color MC_BLACK = new ImmutableColor(0, 0, 0);
   public static final Color MC_DARKBLUE = new ImmutableColor(0, 0, 170);
   public static final Color MC_DARKGREEN = new ImmutableColor(0, 170, 0);
   public static final Color MC_DARKAQUA = new ImmutableColor(0, 170, 170);
   public static final Color MC_DARKRED = new ImmutableColor(170, 0, 0);
   public static final Color MC_DARKPURPLE = new ImmutableColor(170, 0, 170);
   public static final Color MC_GOLD = new ImmutableColor(255, 170, 0);
   public static final Color MC_GRAY = new ImmutableColor(170, 170, 170);
   public static final Color MC_DARKGRAY = new ImmutableColor(85, 85, 85);
   public static final Color MC_BLUE = new ImmutableColor(85, 85, 255);
   public static final Color MC_GREEN = new ImmutableColor(85, 255, 85);
   public static final Color MC_AQUA = new ImmutableColor(85, 255, 255);
   public static final Color MC_RED = new ImmutableColor(255, 85, 85);
   public static final Color MC_LIGHTPURPLE = new ImmutableColor(255, 85, 255);
   public static final Color MC_YELLOW = new ImmutableColor(255, 255, 85);
   public static final Color MC_WHITE = new ImmutableColor(255, 255, 255);
   public static final Color AURORA_RED = new ImmutableColor(1.0F, 0.0F, 0.0F);
   public static final Color AURORA_GREEN = new ImmutableColor(0.5F, 1.0F, 0.0F);
   public static final Color AURORA_BLUE = new ImmutableColor(0.0F, 0.8F, 1.0F);
   public float red;
   public float green;
   public float blue;

   public Color(@Nonnull Color color) {
      this(color.red, color.green, color.blue);
   }

   public Color(int red, int green, int blue) {
      this((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F);
   }

   public Color(@Nonnull Vec3 vec) {
      this((float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
   }

   public Color(int rgb) {
      this(rgb >> 16 & 255, rgb >> 8 & 255, rgb & 255);
   }

   public Color(float red, float green, float blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public Color(double red, double green, double blue) {
      this((float)red, (float)green, (float)blue);
   }

   @Nonnull
   public Vec3 toVec3d() {
      return Vec3.createVectorHelper((double)this.red, (double)this.green, (double)this.blue);
   }

   @Nonnull
   public Vec3 transitionTo(@Nonnull Color target, int iterations) {
      double deltaRed = (double)((target.red - this.red) / (float)iterations);
      double deltaGreen = (double)((target.green - this.green) / (float)iterations);
      double deltaBlue = (double)((target.blue - this.blue) / (float)iterations);
      return Vec3.createVectorHelper(deltaRed, deltaGreen, deltaBlue);
   }

   @Nonnull
   public Color scale(float scaleFactor) {
      return this.scale(scaleFactor, scaleFactor, scaleFactor);
   }

   @Nonnull
   public Color scale(float scaleRed, float scaleGreen, float scaleBlue) {
      this.red *= scaleRed;
      this.green *= scaleGreen;
      this.blue *= scaleBlue;
      return this;
   }

   @Nonnull
   public static Color scale(@Nonnull Color color, float scaleFactor) {
      return (new Color(color)).scale(scaleFactor);
   }

   @Nonnull
   public Color add(@Nonnull Color color) {
      this.red += color.red;
      this.green += color.green;
      this.blue += color.blue;
      return this;
   }

   @Nonnull
   public Color add(float red, float green, float blue) {
      this.red += red;
      this.green += green;
      this.blue += blue;
      return this;
   }

   private static float blend(float c1, float c2, float factor) {
      return (float)Math.sqrt((double)((1.0F - factor) * c1 * c1 + factor * c2 * c2));
   }

   @Nonnull
   public Color blend(@Nonnull Color color, float factor) {
      this.red = blend(this.red, color.red, factor);
      this.green = blend(this.green, color.green, factor);
      this.blue = blend(this.blue, color.blue, factor);
      return this;
   }

   @Nonnull
   public Color mix(@Nonnull Color color) {
      return this.mix(color.red, color.green, color.blue);
   }

   @Nonnull
   public Color mix(float red, float green, float blue) {
      this.red = (this.red + red) / 2.0F;
      this.green = (this.green + green) / 2.0F;
      this.blue = (this.blue + blue) / 2.0F;
      return this;
   }

   @Nonnull
   public Color adjust(@Nonnull Vec3 adjust, @Nonnull Color target) {
      this.red = (float)((double)this.red + adjust.xCoord);
      if (adjust.xCoord < (double)0.0F && this.red < target.red || adjust.xCoord > (double)0.0F && this.red > target.red) {
         this.red = target.red;
      }

      this.green = (float)((double)this.green + adjust.yCoord);
      if (adjust.yCoord < (double)0.0F && this.green < target.green || adjust.yCoord > (double)0.0F && this.green > target.green) {
         this.green = target.green;
      }

      this.blue = (float)((double)this.blue + adjust.zCoord);
      if (adjust.zCoord < (double)0.0F && this.blue < target.blue || adjust.zCoord > (double)0.0F && this.blue > target.blue) {
         this.blue = target.blue;
      }

      return this;
   }

   @Nonnull
   public Color luminance(float percent) {
      float r = Math.min(Math.max(0.0F, this.red + this.red * percent), 1.0F);
      float g = Math.min(Math.max(0.0F, this.green + this.green * percent), 1.0F);
      float b = Math.min(Math.max(0.0F, this.blue + this.blue * percent), 1.0F);
      return new Color(r, g, b);
   }

   public int rgb() {
      int iRed = (int)(this.red * 255.0F);
      int iGreen = (int)(this.green * 255.0F);
      int iBlue = (int)(this.blue * 255.0F);
      return iRed << 16 | iGreen << 8 | iBlue;
   }

   public int rgbWithAlpha(float alpha) {
      int iAlpha = (int)(alpha * 255.0F);
      return this.rgb() | iAlpha << 24;
   }

   public boolean equals(Object anObject) {
      if (anObject != null && anObject instanceof Color) {
         Color color = (Color)anObject;
         return this.red == color.red && this.green == color.green && this.blue == color.blue;
      } else {
         return false;
      }
   }

   @Nonnull
   public Color asImmutable() {
      return new ImmutableColor(this);
   }

   @Nonnull
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("[r:").append((int)(this.red * 255.0F));
      builder.append(",g:").append((int)(this.green * 255.0F));
      builder.append(",b:").append((int)(this.blue * 255.0F));
      builder.append(']');
      return builder.toString();
   }

   public static final class ImmutableColor extends Color {
      ImmutableColor(@Nonnull Color color) {
         super(color);
      }

      ImmutableColor(int red, int green, int blue) {
         super(red, green, blue);
      }

      ImmutableColor(float red, float green, float blue) {
         super(red, green, blue);
      }

      public Color scale(float scaleFactor) {
         throw new UnsupportedOperationException();
      }

      public Color mix(float red, float green, float blue) {
         throw new UnsupportedOperationException();
      }

      public Color adjust(@Nonnull Vec3 adjust, @Nonnull Color target) {
         throw new UnsupportedOperationException();
      }
   }
}
