package org.blockartistry.mod.DynSurround.client.footsteps.game.user;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import org.blockartistry.mod.DynSurround.compat.MCHelper;

@SideOnly(Side.CLIENT)
public class GenerateBlockReport {
   private final List<String> justNames = new ArrayList();
   private final List<String> results = new ArrayList();

   public GenerateBlockReport() {
      for(Object o : Block.blockRegistry) {
         Block block = (Block)o;
         String name = MCHelper.nameOf(block);
         String soundName;
         if (block.stepSound == null) {
            soundName = "NO_STEP";
         } else if (block.stepSound.soundName == null) {
            soundName = "NO_SOUND";
         } else {
            soundName = block.stepSound.soundName;
         }

         if (block instanceof BlockLiquid) {
            soundName = soundName + ",EXTENDS_LIQUID";
         }

         if (block instanceof BlockBush) {
            soundName = soundName + ",EXTENDS_BUSH";
         }

         if (block instanceof BlockDoublePlant) {
            soundName = soundName + ",EXTENDS_DOUBLE_PLANT";
         }

         if (block instanceof BlockCrops) {
            soundName = soundName + ",EXTENDS_CROPS";
         }

         if (block instanceof BlockContainer) {
            soundName = soundName + ",EXTENDS_CONTAINER";
         }

         if (block instanceof BlockLeavesBase) {
            soundName = soundName + ",EXTENDS_LEAVES";
         }

         if (block instanceof BlockRailBase) {
            soundName = soundName + ",EXTENDS_RAIL";
         }

         if (block instanceof BlockSlab) {
            soundName = soundName + ",EXTENDS_SLAB";
         }

         if (block instanceof BlockStairs) {
            soundName = soundName + ",EXTENDS_STAIRS";
         }

         if (block instanceof BlockBreakable) {
            soundName = soundName + ",EXTENDS_BREAKABLE";
         }

         if (block instanceof BlockFalling) {
            soundName = soundName + ",EXTENDS_PHYSICALLY_FALLING";
         }

         if (block instanceof BlockPane) {
            soundName = soundName + ",EXTENDS_PANE";
         }

         if (block instanceof BlockRotatedPillar) {
            soundName = soundName + ",EXTENDS_PILLAR";
         }

         if (block instanceof BlockTorch) {
            soundName = soundName + ",EXTENDS_TORCH";
         }

         if (!block.isOpaqueCube()) {
            soundName = soundName + ",HITBOX";
         }

         this.justNames.add(name);
         this.results.add(name + " = " + soundName);
      }

      Collections.sort(this.justNames);
      Collections.sort(this.results);
   }

   public List<String> getResults() {
      return this.results;
   }

   public List<String> getBlockNames() {
      return this.justNames;
   }
}
