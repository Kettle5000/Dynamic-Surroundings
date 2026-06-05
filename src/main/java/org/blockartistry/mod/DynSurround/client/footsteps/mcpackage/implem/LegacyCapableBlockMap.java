package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.compat.MCHelper;

@SideOnly(Side.CLIENT)
public class LegacyCapableBlockMap extends BasicBlockMap {
   public void register(String key, String value) {
      try {
         int endOfNumber = key.indexOf(94);
         if (endOfNumber == -1) {
            endOfNumber = key.indexOf(46);
         }

         if (endOfNumber == -1) {
            endOfNumber = key.length();
         }

         String number = key.substring(0, endOfNumber);
         int id = Integer.parseInt(number);
         Object o = Block.blockRegistry.getObjectById(id);
         if (o != null && o instanceof Block) {
            String fullKeyRebuild = MCHelper.nameOf((Block)o) + (endOfNumber == key.length() ? "" : key.substring(endOfNumber));
            super.register(fullKeyRebuild, value);
            ModLog.debug("Adding legacy key: " + fullKeyRebuild + " for " + key);
         } else {
            super.register(key, value);
         }
      } catch (NumberFormatException var8) {
         super.register(key, value);
      }

   }
}
