package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemWritableBook;

@SideOnly(Side.CLIENT)
public final class ItemSoundUtil {
   private ItemSoundUtil() {
   }

   public static ItemSoundClass resolveClass(Item item) {
      if (item == null) {
         return ItemSoundClass.EMPTY;
      }

      if (item instanceof ItemSword) {
         return ItemSoundClass.SWORD;
      }
      if (item instanceof ItemAxe || item instanceof ItemPickaxe) {
         return ItemSoundClass.AXE;
      }
      if (item instanceof ItemBow) {
         return ItemSoundClass.BOW;
      }
      if (item instanceof ItemSpade || item instanceof ItemHoe) {
         return ItemSoundClass.TOOL;
      }
      if (item instanceof ItemFood) {
         return ItemSoundClass.FOOD;
      }
      if (item instanceof ItemPotion) {
         return ItemSoundClass.POTION;
      }
      if (item instanceof ItemEditableBook || item instanceof ItemWritableBook) {
         return ItemSoundClass.BOOK;
      }
      if (item instanceof ItemBlock) {
         return ItemSoundClass.NONE;
      }

      return ItemSoundClass.NONE;
   }

   public static SoundEffect getEquipSound(ItemStack stack) {
      if (stack == null) {
         return null;
      }
      return resolveClass(stack.getItem()).getEquipSound();
   }

   public static SoundEffect getSwingSound(ItemStack stack) {
      if (stack == null) {
         return null;
      }
      return resolveClass(stack.getItem()).getSwingSound();
   }
}
