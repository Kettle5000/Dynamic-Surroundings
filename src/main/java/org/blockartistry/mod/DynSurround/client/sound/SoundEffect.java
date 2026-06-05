package org.blockartistry.mod.DynSurround.client.sound;

import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.compat.BlockPos;
import org.blockartistry.mod.DynSurround.data.config.SoundConfig;

public final class SoundEffect {
   private static final float[] pitchDelta = new float[]{-0.2F, 0.0F, 0.0F, 0.2F, 0.2F, 0.2F};
   public final String sound;
   public final String conditions;
   private final Pattern pattern;
   public final SoundType type;
   public float volume;
   public final float pitch;
   public final int weight;
   public final boolean variable;
   public final int repeatDelayRandom;
   public final int repeatDelay;

   public SoundEffect(String sound) {
      this(sound, 1.0F, 1.0F, 0, false);
   }

   public SoundEffect(String sound, float volume, float pitch) {
      this(sound, volume, pitch, 0, false);
   }

   public SoundEffect(String sound, float volume, float pitch, boolean variable) {
      this(sound, volume, pitch, 0, variable);
   }

   public SoundEffect(String sound, float volume, float pitch, int repeatDelay, boolean variable) {
      this.sound = sound;
      this.volume = volume;
      this.pitch = pitch;
      this.conditions = ".*";
      this.pattern = null;
      this.weight = 1;
      this.type = SoundEffect.SoundType.SPOT;
      this.variable = variable;
      this.repeatDelayRandom = 0;
      this.repeatDelay = repeatDelay;
   }

   public SoundEffect(SoundEffect effect) {
      this.sound = effect.sound;
      this.volume = effect.volume;
      this.pitch = effect.pitch;
      this.conditions = effect.conditions;
      this.pattern = effect.pattern;
      this.weight = effect.weight;
      this.type = effect.type;
      this.variable = effect.variable;
      this.repeatDelayRandom = effect.repeatDelayRandom;
      this.repeatDelay = effect.repeatDelay;
   }

   public SoundEffect(SoundConfig record) {
      this.sound = StringUtils.isEmpty(record.sound) ? "MISSING SOUND" : record.sound;
      this.conditions = StringUtils.isEmpty(record.conditions) ? ".*" : record.conditions;
      this.volume = record.volume == null ? 1.0F : record.volume;
      this.pitch = record.pitch == null ? 1.0F : record.pitch;
      this.pattern = Pattern.compile(this.conditions);
      this.weight = record.weight == null ? 10 : record.weight;
      this.variable = record.variable != null && record.variable;
      this.repeatDelayRandom = record.repeatDelayRandom == null ? 0 : record.repeatDelayRandom;
      this.repeatDelay = record.repeatDelay == null ? 0 : record.repeatDelay;
      if (record.soundType != null) {
         this.type = SoundEffect.SoundType.getType(record.soundType);
      } else if (record.repeatDelay != null && record.repeatDelay > 0) {
         this.type = SoundEffect.SoundType.PERIODIC;
      } else if (record.step != null && record.step) {
         this.type = SoundEffect.SoundType.STEP;
      } else if (record.spotSound != null && record.spotSound) {
         this.type = SoundEffect.SoundType.SPOT;
      } else {
         this.type = SoundEffect.SoundType.BACKGROUND;
      }

   }

   public boolean matches(String conditions) {
      return this.pattern.matcher(conditions).matches();
   }

   public float getVolume() {
      return this.volume;
   }

   public float getPitch(Random rand) {
      return rand != null && this.variable ? this.pitch + pitchDelta[rand.nextInt(pitchDelta.length)] : this.pitch;
   }

   public int getRepeat(Random rand) {
      return this.repeatDelayRandom <= 0 ? this.repeatDelay : this.repeatDelay + rand.nextInt(this.repeatDelayRandom);
   }

   public void doEffect(Block block, World world, BlockPos pos, Random random) {
      SoundManager.playSoundAt(pos, this, 0);
   }

   public boolean equals(Object anObj) {
      if (this == anObj) {
         return true;
      } else if (!(anObj instanceof SoundEffect)) {
         return false;
      } else {
         SoundEffect s = (SoundEffect)anObj;
         return this.sound.equals(s.sound);
      }
   }

   public int hashCode() {
      return this.sound.hashCode();
   }

   public static SoundEffect scaleVolume(SoundEffect sound, float scale) {
      SoundEffect newEffect = new SoundEffect(sound);
      newEffect.volume *= scale;
      return newEffect;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append('[').append(this.sound);
      if (!StringUtils.isEmpty(this.conditions)) {
         builder.append('(').append(this.conditions).append(')');
      }

      builder.append(", v:").append(this.volume);
      builder.append(", p:").append(this.pitch);
      builder.append(", t:").append(this.type);
      if (this.type == SoundEffect.SoundType.SPOT) {
         builder.append(", w:").append(this.weight);
      }

      if (this.repeatDelay != 0 || this.repeatDelayRandom != 0) {
         builder.append(", d:").append(this.repeatDelay).append('+').append(this.repeatDelayRandom);
      }

      builder.append(']');
      return builder.toString();
   }

   public static enum SoundType {
      BACKGROUND,
      SPOT,
      STEP,
      PERIODIC;

      public static SoundType getType(String soundType) {
         if (soundType == null) {
            return BACKGROUND;
         } else {
            try {
               return valueOf(soundType.toUpperCase());
            } catch (Throwable ex) {
               ex.printStackTrace();
               return BACKGROUND;
            }
         }
      }
   }
}
