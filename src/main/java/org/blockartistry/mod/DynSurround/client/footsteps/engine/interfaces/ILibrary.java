package org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.Association;

@SideOnly(Side.CLIENT)
public interface ILibrary {
   void addAcoustic(INamedAcoustic var1);

   void playAcoustic(Object var1, Association var2, EventType var3);

   void playAcoustic(Object var1, String var2, EventType var3, IOptions var4);

   void think();
}
