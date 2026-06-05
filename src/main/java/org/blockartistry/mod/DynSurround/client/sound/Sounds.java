package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class Sounds {
   public static final SoundEffect SWORD_EQUIP = new SoundEffect("dsurround:sword.equip", 0.5F, 1.0F);
   public static final SoundEffect SWORD_SWING = new SoundEffect("dsurround:sword.swing", 1.0F, 1.0F);
   public static final SoundEffect AXE_EQUIP = new SoundEffect("dsurround:blunt.equip", 0.5F, 1.0F);
   public static final SoundEffect AXE_SWING = new SoundEffect("dsurround:blunt.swing", 1.0F, 1.0F);
   public static final SoundEffect TOOL_EQUIP = new SoundEffect("dsurround:tool.equip", 0.3F, 1.0F);
   public static final SoundEffect TOOL_SWING = new SoundEffect("dsurround:tool.swing", 1.0F, 1.0F);
   public static final SoundEffect BOW_EQUIP = new SoundEffect("dsurround:bow.equip", 0.5F, 1.0F);
   public static final SoundEffect BOW_PULL = new SoundEffect("dsurround:bow.pull", 1.0F, 1.0F);
   public static final SoundEffect UTILITY_EQUIP = new SoundEffect("dsurround:utility.equip", 0.3F, 1.0F);
   public static final SoundEffect FOOD_EQUIP = new SoundEffect("dsurround:utility.equip", 0.3F, 1.0F);
   public static final SoundEffect BOOK_EQUIP = new SoundEffect("dsurround:pageflip", 0.3F, 1.0F);
   public static final SoundEffect POTION_EQUIP = new SoundEffect("dsurround:potion.equip", 0.3F, 1.0F);

   private Sounds() {
   }
}
