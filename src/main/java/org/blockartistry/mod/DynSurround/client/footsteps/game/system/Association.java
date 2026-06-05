package org.blockartistry.mod.DynSurround.client.footsteps.game.system;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

@SideOnly(Side.CLIENT)
public class Association {
   private Block block;
   private int meta;
   public int x;
   public int y;
   public int z;
   private String data = null;
   private boolean noAssociation = false;
   private boolean isPrimative = false;

   public Association() {
   }

   public Association(Block block, int meta, int xx, int yy, int zz) {
      this.block = block;
      this.meta = meta;
      this.x = xx;
      this.y = yy;
      this.z = zz;
   }

   public String getData() {
      return this.data;
   }

   public Association setAssociation(String association) {
      this.data = association;
      this.noAssociation = false;
      return this;
   }

   public Association setNoAssociation() {
      this.noAssociation = true;
      return this;
   }

   public boolean getNoAssociation() {
      return this.noAssociation;
   }

   public Association setPrimitive(String primative) {
      this.data = primative;
      this.isPrimative = true;
      return this;
   }

   public boolean isPrimative() {
      return this.isPrimative;
   }

   public Block getBlock() {
      return this.block;
   }

   public int getMeta() {
      return this.meta;
   }

   public boolean isNotEmitter() {
      return this.data != null && this.data.equals("NOT_EMITTER");
   }
}
