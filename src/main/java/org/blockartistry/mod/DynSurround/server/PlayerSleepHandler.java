package org.blockartistry.mod.DynSurround.server;

import net.minecraft.world.WorldServer;
import org.blockartistry.mod.DynSurround.ModOptions;

public class PlayerSleepHandler {
   public static void resetRainAndThunder(WorldServer world) {
      if (ModOptions.resetRainOnSleep) {
         world.provider.resetRainAndThunder();
      }

   }
}
