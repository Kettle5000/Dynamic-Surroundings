package org.blockartistry.mod.DynSurround.client.fx.particle;

import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.util.Color;

public class EntityDamagePopOffFX extends EntityTextPopOffFX {
   public EntityDamagePopOffFX(World world, double x, double y, double z, int amount) {
      super(world, String.valueOf(amount), Color.RED, 1.0F, x, y, z, 0.001, 0.07500000000000001, 0.001);
   }
}
