package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.Association;

@SideOnly(Side.CLIENT)
public interface ISolver {
   void playAssociation(EntityPlayer var1, Association var2, EventType var3);

   Association findAssociationForPlayer(EntityPlayer var1, double var2, boolean var4);

   Association findAssociationForPlayer(EntityPlayer var1, double var2);

   Association findAssociationForLocation(EntityPlayer var1, int var2, int var3, int var4);

   Association findAssociationForBlock(int var1, int var2, int var3);

   Association findAssociationForBlock(int var1, int var2, int var3, String var4);

   boolean playSpecialStoppingConditions(EntityPlayer var1);

   boolean hasSpecialStoppingConditions(EntityPlayer var1);
}
