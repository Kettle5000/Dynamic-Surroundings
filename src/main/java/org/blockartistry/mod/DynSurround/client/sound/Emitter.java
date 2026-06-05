package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;
import paulscode.sound.SoundSystemConfig;

@SideOnly(Side.CLIENT)
class Emitter {
   protected static final Random RANDOM = new XorShiftRandom();
   protected final SoundEffect effect;
   protected PlayerSound activeSound;
   protected int repeatDelay = 0;

   public Emitter(SoundEffect sound) {
      this.effect = sound;
   }

   public void update() {
      if (!(SoundSystemConfig.getMasterGain() <= 0.0F)) {
         if (this.activeSound == null) {
            this.activeSound = new PlayerSound(this.effect);
         } else if (SoundManager.isSoundPlaying(this.activeSound)) {
            return;
         }

         try {
            SoundManager.playSound(this.activeSound);
         } catch (Throwable t) {
            ModLog.error("Unable to play sound", t);
         }

      }
   }

   public void setVolume(float volume) {
      if (this.activeSound != null) {
         this.activeSound.setVolume(volume);
      }

   }

   public float getVolume() {
      return this.activeSound != null ? this.activeSound.getVolume() : 0.0F;
   }

   public void fade() {
      if (this.activeSound != null) {
         this.activeSound.fadeAway();
      }

   }
}
