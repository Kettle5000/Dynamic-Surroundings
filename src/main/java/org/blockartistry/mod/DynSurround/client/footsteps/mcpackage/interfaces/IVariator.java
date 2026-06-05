package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple.ConfigProperty;

@SideOnly(Side.CLIENT)
public interface IVariator {
   void loadConfig(ConfigProperty var1);
}
