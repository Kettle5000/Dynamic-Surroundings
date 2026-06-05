package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.util.Color;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

@SideOnly(Side.CLIENT)
public class EntityCriticalPopOffFX extends EntityTextPopOffFX {
   private static final String[] POWER_WORDS = new String[]{"BAM", "BANG", "BONK", "CRRACK", "CRASH", "KRUNCH", "OOOOFF", "POWIE", "SPLATT", "THUNK", "TWAPE", "WHAMMM", "ZAP"};

   private static String getPowerWord() {
      return POWER_WORDS[XorShiftRandom.current().nextInt(POWER_WORDS.length)];
   }

   public EntityCriticalPopOffFX(World world, double x, double y, double z) {
      super(world, getPowerWord(), Color.ORANGE, 1.0F, x, y, z, 0.001, 0.07500000000000001, 0.001);
      this.shouldOnTop = true;
      this.particleGravity = -0.04F;
      this.scale = 0.5F;
   }
}
