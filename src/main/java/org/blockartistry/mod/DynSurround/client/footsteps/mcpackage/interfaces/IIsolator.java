package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ILibrary;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;

@SideOnly(Side.CLIENT)
public interface IIsolator {
   void onFrame();

   ILibrary getAcoustics();

   ISolver getSolver();

   IBlockMap getBlockMap();

   IPrimitiveMap getPrimitiveMap();

   ISoundPlayer getSoundPlayer();

   IDefaultStepPlayer getDefaultStepPlayer();

   void setAcoustics(ILibrary var1);

   void setSolver(ISolver var1);

   void setBlockMap(IBlockMap var1);

   void setPrimitiveMap(IPrimitiveMap var1);

   void setSoundPlayer(ISoundPlayer var1);

   void setDefaultStepPlayer(IDefaultStepPlayer var1);
}
