package org.blockartistry.mod.DynSurround.util.random;

public final class LCGRandom {
   private int v;

   public LCGRandom() {
      this(XorShiftRandom.current().nextInt());
   }

   public LCGRandom(int seed) {
      this.v = seed;
   }

   public int nextInt(int bound) {
      this.v = 214013 * this.v + 2531011;
      return (this.v >> 16 & 32767) % bound;
   }
}
