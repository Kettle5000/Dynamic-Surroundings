package org.blockartistry.mod.DynSurround.client.fx.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

@SideOnly(Side.CLIENT)
public abstract class EntityJetFX extends EntityFX {
   protected static final Random RANDOM = XorShiftRandom.current();
   protected final int jetStrength;
   protected final int updateFrequency;

   public EntityJetFX(int strength, World world, double x, double y, double z) {
      this(strength, world, x, y, z, 3);
   }

   public EntityJetFX(int strength, World world, double x, double y, double z, int freq) {
      super(world, x, y, z);
      this.setAlphaF(0.0F);
      this.jetStrength = strength;
      this.updateFrequency = freq;
      this.particleMaxAge = (RANDOM.nextInt(strength) + 2) * 20;
   }

   public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
   }

   protected abstract EntityFX getJetParticle();

   public void playSound() {
   }

   public void onUpdate() {
      if (this.particleAge % this.updateFrequency == 0) {
         Minecraft.getMinecraft().effectRenderer.addEffect(this.getJetParticle());
      }

      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

   }
}
