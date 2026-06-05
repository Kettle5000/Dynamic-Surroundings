package org.blockartistry.mod.DynSurround.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PotionParticleScrubHandler implements IClientEffectHandler {
   public void process(World world, EntityPlayer player) {
      player.getDataWatcher().updateObject(7, 0);
   }

   public boolean hasEvents() {
      return false;
   }
}
