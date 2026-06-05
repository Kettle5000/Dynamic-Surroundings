package org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IAcoustic {
   void playSound(ISoundPlayer var1, Object var2, EventType var3, IOptions var4);
}
