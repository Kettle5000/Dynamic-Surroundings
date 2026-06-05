package org.blockartistry.mod.DynSurround.client.fx.particle;

import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.util.Color;

public class EntityHealPopOffFX extends EntityTextPopOffFX {
   public EntityHealPopOffFX(World world, double x, double y, double z, int amount) {
      super(world, String.valueOf(amount), Color.GREEN, 1.0F, x, y, z, 0.001, 0.07500000000000001, 0.001);
   }
}
