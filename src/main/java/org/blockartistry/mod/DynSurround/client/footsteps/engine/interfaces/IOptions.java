package org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IOptions {
   boolean hasOption(Option var1);

   Object getOption(Option var1);

   public static enum Option {
      DELAY_MIN,
      DELAY_MAX,
      SKIPPABLE,
      GLIDING_VOLUME,
      GLIDING_PITCH;
   }
}
