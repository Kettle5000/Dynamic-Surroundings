package org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.implem.AcousticsLibrary;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.Association;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IDefaultStepPlayer;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IIsolator;
import org.blockartistry.mod.DynSurround.util.MyUtils;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

@SideOnly(Side.CLIENT)
public class AcousticsManager extends AcousticsLibrary implements ISoundPlayer, IDefaultStepPlayer {
   private static final Random RANDOM = new XorShiftRandom();
   private static final boolean USING_LATENESS = true;
   private static final boolean USING_EARLYNESS = true;
   private static final float LATENESS_THRESHOLD_DIVIDER = 1.2F;
   private static final double EARLYNESS_THRESHOLD_POW = (double)0.75F;
   private final List<PendingSound> pending = new ArrayList();
   private final IIsolator isolator;
   private long minimum;

   public AcousticsManager(IIsolator isolator) {
      this.isolator = isolator;
   }

   public void playStep(EntityLivingBase entity, Association assos) {
      try {
         Block block = assos.getBlock();
         if (!block.getMaterial().isLiquid() && block.stepSound != null && block.stepSound.soundName != null) {
            Block.SoundType soundType = block.stepSound;
            if (EnvironStateHandler.EnvironState.getWorld().getBlock(assos.x, assos.y + 1, assos.z) == Blocks.snow_layer) {
               soundType = Blocks.snow_layer.stepSound;
            }

            entity.playSound(soundType.getStepResourcePath(), soundType.getVolume() * 0.15F, soundType.getPitch());
         }
      } catch (Throwable t) {
         ModLog.error("Unable to play step", t);
      }

   }

   public void playSound(Object location, String soundName, float volume, float pitch, IOptions options) {
      if (location instanceof Entity) {
         if (options != null) {
            if (options.hasOption(IOptions.Option.DELAY_MIN) && options.hasOption(IOptions.Option.DELAY_MAX)) {
               long delay = this.randAB(RANDOM, (Long)options.getOption(IOptions.Option.DELAY_MIN), (Long)options.getOption(IOptions.Option.DELAY_MAX));
               if (delay < this.minimum) {
                  this.minimum = delay;
               }

               this.pending.add(new PendingSound(location, soundName, volume, pitch, (IOptions)null, MyUtils.currentTimeMillis() + delay, options.hasOption(IOptions.Option.SKIPPABLE) ? -1L : (Long)options.getOption(IOptions.Option.DELAY_MAX)));
            } else {
               this.actuallyPlaySound((Entity)location, soundName, volume, pitch);
            }
         } else {
            this.actuallyPlaySound((Entity)location, soundName, volume, pitch);
         }

      }
   }

   protected void actuallyPlaySound(Entity location, String soundName, float volume, float pitch) {
      if (ModLog.DEBUGGING) {
         ModLog.debug("    Playing sound " + soundName + " (" + String.format(Locale.ENGLISH, "v%.2f, p%.2f", volume, pitch) + ")");
      }

      try {
         location.playSound(soundName, volume, pitch);
      } catch (Throwable t) {
         ModLog.error("Unable to play sound", t);
      }

   }

   private long randAB(Random rng, long a, long b) {
      return a >= b ? a : a + (long)rng.nextInt((int)b + 1);
   }

   public Random getRNG() {
      return RANDOM;
   }

   public void think() {
      if (!this.pending.isEmpty() && MyUtils.currentTimeMillis() >= this.minimum) {
         long newMinimum = Long.MAX_VALUE;
         long time = MyUtils.currentTimeMillis();
         Iterator<PendingSound> iter = this.pending.iterator();

         while(iter.hasNext()) {
            PendingSound sound = (PendingSound)iter.next();
            if (time < sound.getTimeToPlay() && !((double)time >= (double)sound.getTimeToPlay() - Math.pow((double)sound.getMaximumBase(), (double)0.75F))) {
               newMinimum = sound.getTimeToPlay();
            } else {
               if (ModLog.DEBUGGING && time < sound.getTimeToPlay()) {
                  ModLog.debug("    Playing early sound (early by " + (sound.getTimeToPlay() - time) + "ms, tolerence is " + Math.pow((double)sound.getMaximumBase(), (double)0.75F));
               }

               long lateness = time - sound.getTimeToPlay();
               if (sound.getMaximumBase() >= 0L && !((float)lateness <= (float)sound.getMaximumBase() / 1.2F)) {
                  if (ModLog.DEBUGGING) {
                     ModLog.debug("    Skipped late sound (late by " + lateness + "ms, tolerence is " + (float)sound.getMaximumBase() / 1.2F + "ms)");
                  }
               } else {
                  sound.playSound(this);
               }

               iter.remove();
            }
         }

         this.minimum = newMinimum;
      }
   }

   protected ISoundPlayer mySoundPlayer() {
      return this.isolator.getSoundPlayer();
   }
}
