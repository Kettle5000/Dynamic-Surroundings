package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public interface IGenerator {
   void generateFootsteps(EntityPlayer var1);
}
