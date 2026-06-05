package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.sound.ItemSoundUtil;
import org.blockartistry.mod.DynSurround.client.sound.SoundEffect;
import org.blockartistry.mod.DynSurround.client.sound.SoundManager;

@SideOnly(Side.CLIENT)
public class ItemSoundHandler implements IClientEffectHandler {
   private Item lastHeldItem;
   private int lastSlot = -1;
   private int swingProgress = 0;
   private boolean isSwinging = false;

   public void process(World world, EntityPlayer player) {
      if (player == null || world == null || !world.isRemote) {
         return;
      }

      if (ModOptions.enableEquipSound) {
         updateEquipSound(player);
      }

      if (ModOptions.enableSwingSound) {
         updateSwingSound(player);
      }
   }

   private void updateEquipSound(EntityPlayer player) {
      int currentSlot = player.inventory.currentItem;
      ItemStack currentStack = player.getCurrentEquippedItem();
      Item currentItem = currentStack != null ? currentStack.getItem() : null;
      boolean slotChanged = this.lastSlot != currentSlot;
      boolean itemChanged = this.lastHeldItem != currentItem;

      if (slotChanged || itemChanged) {
         SoundEffect equip = ItemSoundUtil.getEquipSound(currentStack);
         if (equip != null) {
            SoundManager.playSoundAtPlayer(equip);
         }
         this.lastSlot = currentSlot;
         this.lastHeldItem = currentItem;
      }
   }

   private void updateSwingSound(EntityPlayer player) {
      if (player.isSwingInProgress && player.swingProgressInt > this.swingProgress) {
         if (!this.isSwinging) {
            ItemStack currentItem = player.getCurrentEquippedItem();
            SoundEffect swing = ItemSoundUtil.getSwingSound(currentItem);
            if (swing != null && !isHittingBlock()) {
               SoundManager.playSoundAtPlayer(swing);
            }
         }
         this.isSwinging = true;
      } else {
         this.isSwinging = false;
      }

      this.swingProgress = player.swingProgressInt;
   }

   private boolean isHittingBlock() {
      MovingObjectPosition target = Minecraft.getMinecraft().objectMouseOver;
      return target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK;
   }

   public boolean hasEvents() {
      return false;
   }
}
