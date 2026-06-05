package org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

@SideOnly(Side.CLIENT)
public interface ISoundPlayer {
   void playSound(Object var1, String var2, float var3, float var4, IOptions var5);

   Random getRNG();
}
