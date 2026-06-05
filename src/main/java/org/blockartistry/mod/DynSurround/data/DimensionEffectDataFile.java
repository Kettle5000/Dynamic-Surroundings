package org.blockartistry.mod.DynSurround.data;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public final class DimensionEffectDataFile extends WorldSavedData {
   private static final String IDENTIFIER = "dsurround";
   private final TIntObjectHashMap<DimensionEffectData> dataList;

   public DimensionEffectDataFile() {
      this("dsurround");
   }

   public DimensionEffectDataFile(String id) {
      super(id);
      this.dataList = new TIntObjectHashMap();
   }

   private static DimensionEffectDataFile getFile(World world) {
      DimensionEffectDataFile data = (DimensionEffectDataFile)world.loadItemData(DimensionEffectDataFile.class, "dsurround");
      if (data == null) {
         data = new DimensionEffectDataFile();
         world.setItemData("dsurround", data);
      }

      data.markDirty();
      return data;
   }

   private DimensionEffectData getData(int dimensionId) {
      DimensionEffectData data = (DimensionEffectData)this.dataList.get(dimensionId);
      if (data != null) {
         return data;
      } else {
         data = new DimensionEffectData(dimensionId);
         this.dataList.put(dimensionId, data);
         return data;
      }
   }

   public static DimensionEffectData get(World world) {
      return getFile(world).getData(world.provider.dimensionId);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagList list = nbt.getTagList("e", 10);

      for(int i = 0; i < list.tagCount(); ++i) {
         NBTTagCompound tag = list.getCompoundTagAt(i);
         DimensionEffectData data = new DimensionEffectData();
         data.readFromNBT(tag);
         this.dataList.put(data.getDimensionId(), data);
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagList list = new NBTTagList();

      for(DimensionEffectData data : this.dataList.valueCollection()) {
         NBTTagCompound tag = new NBTTagCompound();
         data.writeToNBT(tag);
         list.appendTag(tag);
      }

      nbt.setTag("e", list);
   }

   private final class NBT {
      public static final String ENTRIES = "e";
   }
}
