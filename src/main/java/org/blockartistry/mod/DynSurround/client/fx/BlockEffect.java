package org.blockartistry.mod.DynSurround.client.fx;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.compat.BlockPos;

public abstract class BlockEffect {
   private int chance;

   public BlockEffect() {
      this(100);
   }

   public BlockEffect(int chance) {
      this.chance = chance;
   }

   public void setChance(int chance) {
      this.chance = chance;
   }

   public int getChance() {
      return this.chance;
   }

   public boolean trigger(Block block, World world, BlockPos pos, Random random) {
      return random.nextInt(this.getChance()) == 0;
   }

   public abstract void doEffect(Block var1, World var2, BlockPos var3, Random var4);

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("chance:").append(this.chance);
      builder.append(' ').append(this.getClass().getSimpleName());
      return builder.toString();
   }
}
