package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.util.MyUtils;
import org.blockartistry.mod.DynSurround.util.random.XorShiftRandom;

@SideOnly(Side.CLIENT)
class PlayerSound extends MovingSound {
   private static final float DONE_VOLUME_THRESHOLD = 0.001F;
   private static final float FADE_AMOUNT = 0.015F;
   private static final Random RANDOM = new XorShiftRandom();
   private final SoundEffect sound;
   private boolean isFading;
   private float maxVolume;
   private boolean isDonePlaying;
   private long lastTick;

   public PlayerSound(SoundEffect sound) {
      super(new ResourceLocation(sound.sound));
      this.sound = sound;
      this.volume = sound.volume;
      this.maxVolume = sound.getVolume();
      this.volume = 0.002F;
      this.field_147663_c = sound.getPitch(RANDOM);
      this.repeat = sound.repeatDelay == 0;
      this.field_147665_h = 0;
      this.lastTick = (long)(EnvironStateHandler.EnvironState.getTickCounter() - 1);
      this.field_147666_i = AttenuationType.NONE;
      this.updateLocation();
   }

   public void fadeAway() {
      this.isFading = true;
   }

   public boolean isFading() {
      return this.isFading;
   }

   public boolean isDonePlaying() {
      return this.isDonePlaying;
   }

   public boolean sameSound(SoundEffect snd) {
      return this.sound.equals(snd);
   }

   public void updateLocation() {
      AxisAlignedBB box = EnvironStateHandler.EnvironState.getPlayer().boundingBox;
      Vec3 point = MyUtils.getCenter(box);
      this.xPosF = (float)point.xCoord;
      this.yPosF = (float)box.minY + 32.0F;
      this.zPosF = (float)point.zCoord;
   }

   public void update() {
      if (!this.isDonePlaying()) {
         if (!EnvironStateHandler.EnvironState.getPlayer().isEntityAlive()) {
            this.isDonePlaying = true;
         } else {
            long tickDelta = (long)EnvironStateHandler.EnvironState.getTickCounter() - this.lastTick;
            if (tickDelta != 0L) {
               this.lastTick = (long)EnvironStateHandler.EnvironState.getTickCounter();
               if (this.isFading()) {
                  this.volume -= 0.015F * (float)tickDelta;
               } else if (this.volume < this.maxVolume) {
                  this.volume += 0.015F * (float)tickDelta;
               }

               if (this.volume > this.maxVolume) {
                  this.volume = this.maxVolume;
               }

               if (this.volume <= 0.001F) {
                  this.isDonePlaying = true;
                  this.volume = 0.0F;
               } else {
                  this.updateLocation();
               }

            }
         }
      }
   }

   public float getVolume() {
      return super.getVolume() * ModOptions.masterSoundScaleFactor;
   }

   public void setVolume(float volume) {
      if (volume < this.maxVolume || !this.isFading) {
         this.maxVolume = volume;
      }

   }

   public String toString() {
      return this.sound.toString();
   }

   public boolean equals(Object anObj) {
      if (this == anObj) {
         return true;
      } else if (anObj instanceof PlayerSound) {
         return this.sameSound(((PlayerSound)anObj).sound);
      } else {
         return anObj instanceof SoundEffect ? this.sameSound((SoundEffect)anObj) : false;
      }
   }
}
