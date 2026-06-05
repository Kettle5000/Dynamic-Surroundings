package org.blockartistry.mod.DynSurround.data;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.MathHelper;
import org.blockartistry.mod.DynSurround.util.Color;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

public final class ColorPair {
   public final Color baseColor;
   public final Color fadeColor;
   private static final List<ColorPair> PAIRS = new ArrayList();

   private ColorPair(Color base, Color fade) {
      this.baseColor = base;
      this.fadeColor = fade;
   }

   public static ColorPair get(int id) {
      return (ColorPair)PAIRS.get(MathHelper.clamp_int(id, 0, PAIRS.size() - 1));
   }

   public static int randomId() {
      return XorShiftRandom.current().nextInt(PAIRS.size());
   }

   public static int testId() {
      return PAIRS.size() - 1;
   }

   static {
      PAIRS.add(new ColorPair(new Color(0, 255, 153), new Color(51, 255, 0)));
      PAIRS.add(new ColorPair(Color.BLUE, Color.GREEN));
      PAIRS.add(new ColorPair(Color.TURQOISE, Color.LGREEN));
      PAIRS.add(new ColorPair(Color.YELLOW, Color.RED));
      PAIRS.add(new ColorPair(Color.NAVY, Color.INDIGO));
      PAIRS.add(new ColorPair(Color.GREEN, Color.YELLOW));
      PAIRS.add(new ColorPair(Color.MAGENTA, Color.GREEN));
      PAIRS.add(new ColorPair(Color.INDIGO, Color.GREEN));
      PAIRS.add(new ColorPair(Color.CYAN, Color.MAGENTA));
   }
}
