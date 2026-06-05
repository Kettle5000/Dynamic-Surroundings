package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.EntityRenderer;

@SideOnly(Side.CLIENT)
public interface IAtmosRenderer {
   void render(EntityRenderer var1, float var2);
}
