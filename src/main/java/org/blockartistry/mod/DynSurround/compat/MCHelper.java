package org.blockartistry.mod.DynSurround.compat;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class MCHelper {
   public static String nameOf(Block block) {
      return Block.blockRegistry.getNameForObject(block);
   }

   public static Block getBlockNameRaw(String blockName) {
      return (Block)GameData.getBlockRegistry().getRaw(blockName);
   }

   public static Block getBlock(World world, BlockPos pos) {
      return world.getBlock(pos.getX(), pos.getY(), pos.getZ());
   }

   public static int getBlockMetadata(World world, BlockPos pos) {
      return world.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ());
   }

   public static boolean isAirBlock(World world, BlockPos pos) {
      return world.isAirBlock(pos.getX(), pos.getY(), pos.getZ());
   }

   public static boolean isLeafBlock(World world, BlockPos pos) {
      return getBlock(world, pos).isLeaves(world, pos.getX(), pos.getY(), pos.getZ());
   }
}
