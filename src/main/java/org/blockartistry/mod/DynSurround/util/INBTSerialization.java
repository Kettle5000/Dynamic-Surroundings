package org.blockartistry.mod.DynSurround.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerialization {
   void readFromNBT(NBTTagCompound var1);

   void writeToNBT(NBTTagCompound var1);
}
