package org.blockartistry.mod.DynSurround.util;

public class MathStuff {
   private static final int SIN_BITS = 12;
   private static final int SIN_MASK = 4095;
   private static final int SIN_COUNT = 4096;
   private static final float RAD_FULL = ((float)Math.PI * 2F);
   private static final float RAD_TO_INDEX = 651.8986F;
   private static final float DEG_FULL = 360.0F;
   private static final float DEG_TO_INDEX = 11.377778F;
   private static final float COS_TO_SIN = ((float)Math.PI / 2F);
   private static final float[] SIN_TABLE = new float[4096];
   private static final int ATAN2_BITS = 7;
   private static final int ATAN2_BITS2 = 14;
   private static final int ATAN2_MASK = 16383;
   private static final int ATAN2_COUNT = 16384;
   private static final int ATAN2_DIM = (int)Math.sqrt((double)16384.0F);
   private static final float ATAN2_DIM_MINUS_1;
   private static final float[] ATAN2_TABLE;
   private static final float RAD_TO_DEG = 57.29578F;
   private static final float DEG_TO_RAD = ((float)Math.PI / 180F);
   public static final float PI_F = (float)Math.PI;
   public static final float E_F = (float)Math.E;

   public static final float sin(float rad) {
      return SIN_TABLE[(int)(rad * 651.8986F) & 4095];
   }

   public static final float cos(float rad) {
      return SIN_TABLE[(int)((rad + ((float)Math.PI / 2F)) * 651.8986F) & 4095];
   }

   public static final float tan(float rad) {
      return sin(rad) / cos(rad);
   }

   public static final double sin(double rad) {
      float tmp = (float)rad;
      return (double)SIN_TABLE[(int)(tmp * 651.8986F) & 4095];
   }

   public static final double cos(double rad) {
      float tmp = (float)rad;
      return (double)SIN_TABLE[(int)((tmp + ((float)Math.PI / 2F)) * 651.8986F) & 4095];
   }

   public static final double tan(double rad) {
      return (double)tan((float)rad);
   }

   public static final float atan2(float y, float x) {
      float add;
      float mul;
      if (x < 0.0F) {
         if (y < 0.0F) {
            x = -x;
            y = -y;
            mul = 1.0F;
         } else {
            x = -x;
            mul = -1.0F;
         }

         add = -(float)Math.PI;
      } else {
         if (y < 0.0F) {
            y = -y;
            mul = -1.0F;
         } else {
            mul = 1.0F;
         }

         add = 0.0F;
      }

      float invDiv = ATAN2_DIM_MINUS_1 / (x < y ? y : x);
      int xi = (int)(x * invDiv);
      int yi = (int)(y * invDiv);
      return (ATAN2_TABLE[yi * ATAN2_DIM + xi] + add) * mul;
   }

   public static final double atan2(double y, double x) {
      float tmp1 = (float)y;
      float tmp2 = (float)x;
      return (double)atan2(tmp1, tmp2);
   }

   public static final float toRadians(float degrees) {
      return degrees * ((float)Math.PI / 180F);
   }

   public static final float toDegrees(float radians) {
      return radians * 57.29578F;
   }

   public static float wrapDegrees(float value) {
      value %= 360.0F;
      if (value >= 180.0F) {
         value -= 360.0F;
      }

      if (value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }

   public static double wrapDegrees(double value) {
      value %= (double)360.0F;
      if (value >= (double)180.0F) {
         value -= (double)360.0F;
      }

      if (value < (double)-180.0F) {
         value += (double)360.0F;
      }

      return value;
   }

   public static final float abs(float val) {
      return val < 0.0F ? -val : val;
   }

   public static final double abs(double val) {
      return val < (double)0.0F ? -val : val;
   }

   public static final long abs(long val) {
      return val < 1L ? -val : val;
   }

   public static final int abs(int val) {
      return val < 1 ? -val : val;
   }

   public static float sqrt(float value) {
      return (float)Math.sqrt((double)value);
   }

   public static double sqrt(double value) {
      return (double)((float)Math.sqrt(value));
   }

   public static int floor(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static int floor(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static double log(double value) {
      return value < 0.03 ? Math.log(value) : (double)6.0F * (value - (double)1.0F) / (value + (double)1.0F + (double)4.0F * Math.sqrt(value));
   }

   public static float clamp(float num, float min, float max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static double clamp(double num, double min, double max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static int clamp(int num, int min, int max) {
      return num < min ? min : (num > max ? max : num);
   }

   static {
      ATAN2_DIM_MINUS_1 = (float)(ATAN2_DIM - 1);
      ATAN2_TABLE = new float[16384];

      for(int i = 0; i < 4096; ++i) {
         SIN_TABLE[i] = (float)Math.sin((double)(((float)i + 0.5F) / 4096.0F * ((float)Math.PI * 2F)));
      }

      for(int i = 0; i < 360; i += 90) {
         SIN_TABLE[(int)((float)i * 11.377778F) & 4095] = (float)Math.sin((double)i * Math.PI / (double)180.0F);
      }

      for(int i = 0; i < ATAN2_DIM; ++i) {
         for(int j = 0; j < ATAN2_DIM; ++j) {
            float x0 = (float)i / (float)ATAN2_DIM;
            float y0 = (float)j / (float)ATAN2_DIM;
            ATAN2_TABLE[j * ATAN2_DIM + i] = (float)Math.atan2((double)y0, (double)x0);
         }
      }

   }
}
