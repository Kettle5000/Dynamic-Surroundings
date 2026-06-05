package org.blockartistry.mod.DynSurround.data;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.MathHelper;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

public final class AuroraPreset {
   public final int length;
   public final float nodeLength;
   public final float nodeWidth;
   public final int bandOffset;
   private static final List<AuroraPreset> PRESET = new ArrayList();

   private AuroraPreset(int length, float nodeLength, float nodeWidth, int bandOffset) {
      this.length = length;
      this.nodeLength = nodeLength;
      this.nodeWidth = nodeWidth;
      this.bandOffset = bandOffset;
   }

   public static AuroraPreset get(int id) {
      return (AuroraPreset)PRESET.get(MathHelper.clamp_int(id, 0, PRESET.size() - 1));
   }

   public static int randomId() {
      return XorShiftRandom.current().nextInt(PRESET.size());
   }

   public static int testId() {
      return PRESET.size() - 1;
   }

   static {
      PRESET.add(new AuroraPreset(128, 30.0F, 2.0F, 45));
      PRESET.add(new AuroraPreset(128, 15.0F, 2.0F, 27));
      PRESET.add(new AuroraPreset(64, 30.0F, 2.0F, 45));
      PRESET.add(new AuroraPreset(64, 15.0F, 2.0F, 27));
   }
}
