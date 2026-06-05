package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum ItemSoundClass {
   EMPTY(null, null, null),
   NONE(null, null, Sounds.UTILITY_EQUIP),
   SWORD(Sounds.SWORD_SWING, null, Sounds.SWORD_EQUIP),
   AXE(Sounds.AXE_SWING, null, Sounds.AXE_EQUIP),
   BOW(Sounds.TOOL_SWING, Sounds.BOW_PULL, Sounds.BOW_EQUIP),
   TOOL(Sounds.TOOL_SWING, null, Sounds.TOOL_EQUIP),
   FOOD(null, null, Sounds.FOOD_EQUIP),
   BOOK(Sounds.BOOK_EQUIP, Sounds.BOOK_EQUIP, Sounds.BOOK_EQUIP),
   POTION(Sounds.POTION_EQUIP, Sounds.POTION_EQUIP, Sounds.POTION_EQUIP);

   private final SoundEffect swing;
   private final SoundEffect use;
   private final SoundEffect equip;

   ItemSoundClass(SoundEffect swing, SoundEffect use, SoundEffect equip) {
      this.swing = swing;
      this.use = use;
      this.equip = equip;
   }

   public SoundEffect getSwingSound() {
      return this.swing;
   }

   public SoundEffect getUseSound() {
      return this.use;
   }

   public SoundEffect getEquipSound() {
      return this.equip;
   }
}
