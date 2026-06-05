package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.compat.BlockPos;
import org.blockartistry.mod.DynSurround.data.SoundRegistry;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC10;
import paulscode.sound.CommandObject;
import paulscode.sound.SoundSystemConfig;

@SideOnly(Side.CLIENT)
public class SoundManager {
   private static final int AGE_THRESHOLD_TICKS = 5;
   private static final int SOUND_QUEUE_SLACK = 6;
   private static final Map<SoundEffect, Emitter> emitters = new HashMap();
   private static final List<SpotSound> pending = new ArrayList();
   private static int normalChannelCount = 0;
   private static int streamChannelCount = 0;

   public static void clearSounds() {
      for(Emitter emit : emitters.values()) {
         emit.fade();
      }

      emitters.clear();
      pending.clear();
   }

   public static void queueAmbientSounds(List<SoundEffect> sounds) {
      for(SoundEffect effect : new ArrayList<SoundEffect>(emitters.keySet())) {
         if (!sounds.contains(effect)) {
            ((Emitter)emitters.remove(effect)).fade();
         } else {
            Emitter emitter = (Emitter)emitters.get(effect);
            SoundEffect incoming = null;

            for(SoundEffect sound : sounds) {
               if (sound.equals(effect)) {
                  incoming = sound;
                  break;
               }
            }

            emitter.setVolume(incoming.getVolume());
            sounds.remove(effect);
         }
      }

      for(SoundEffect sound : sounds) {
         emitters.put(sound, new Emitter(sound));
      }

   }

   public static void update() {
      for(Emitter emitter : emitters.values()) {
         emitter.update();
      }

      Iterator<SpotSound> pitr = pending.iterator();

      while(pitr.hasNext()) {
         SpotSound sound = (SpotSound)pitr.next();
         if (sound.getTickAge() >= 5) {
            ModLog.debug("AGING: " + sound.toString());
            pitr.remove();
         } else if (sound.getTickAge() >= 0 && canFitSound()) {
            playSound(sound);
            pitr.remove();
         }
      }

   }

   public static int currentSoundCount() {
      return Minecraft.getMinecraft().getSoundHandler().sndManager.playingSounds.size();
   }

   public static int maxSoundCount() {
      return SoundSystemConfig.getNumberNormalChannels() + SoundSystemConfig.getNumberStreamingChannels();
   }

   private static boolean canFitSound() {
      return currentSoundCount() < SoundSystemConfig.getNumberNormalChannels() - 6;
   }

   public static void flushSound() {
   }

   static void playSound(ISound sound) {
      if (sound != null) {
         if (ModOptions.enableDebugLogging) {
            ModLog.debug("PLAYING: " + sound.toString());
         }

         SoundHandler h = Minecraft.getMinecraft().getSoundHandler();
         h.playSound(sound);
      }

   }

   public static void playSoundAtPlayer(SoundEffect sound) {
      SpotSound s = new SpotSound(sound);
      playSound(s);
   }

   public static void playSoundAtPlayer(EntityPlayer player, SoundEffect sound) {
      if (player == null) {
         player = EnvironStateHandler.EnvironState.getPlayer();
      }

      SpotSound s = new SpotSound(player, sound);
      if (!canFitSound()) {
         pending.add(s);
      } else {
         playSound(s);
      }

   }

   public static void playSoundAt(BlockPos pos, SoundEffect sound, int tickDelay) {
      if (tickDelay <= 0 || canFitSound()) {
         SpotSound s = new SpotSound(pos, sound, tickDelay);
         if (tickDelay <= 0 && canFitSound()) {
            playSound(s);
         } else {
            pending.add(s);
         }

      }
   }

   public static boolean isSoundPlaying(@Nonnull ISound sound) {
      net.minecraft.client.audio.SoundManager manager = Minecraft.getMinecraft().getSoundHandler().sndManager;
      return manager.isSoundPlaying(sound) || manager.playingSounds.containsValue(sound) || manager.delayedSounds.containsKey(sound);
   }

   public static List<String> getSounds() {
      List<String> result = new ArrayList();

      for(SoundEffect effect : emitters.keySet()) {
         result.add("EMITTER: " + effect.toString() + "[vol:" + ((Emitter)emitters.get(effect)).getVolume() + "]");
      }

      for(SpotSound effect : pending) {
         result.add((effect.getTickAge() < 0 ? "DELAYED: " : "PENDING: ") + effect.getSoundEffect().toString());
      }

      return result;
   }

   private static float getVolume(@Nonnull SoundCategory category) {
      GameSettings settings = Minecraft.getMinecraft().gameSettings;
      return settings != null && category != null && category != SoundCategory.MASTER ? settings.getSoundLevel(category) : 1.0F;
   }

   public static float getNormalizedVolume(ISound sound, SoundPoolEntry poolEntry, SoundCategory category) {
      float result = 0.0F;
      if (sound == null) {
         ModLog.warn("getNormalizedVolume(): Null sound parameter");
         return result;
      } else {
         String soundName = sound.getPositionedSoundLocation().toString();
         if (poolEntry == null) {
            ModLog.warn("getNormalizedVolume(%s): Null poolEntry parameter", soundName);
         } else if (category == null) {
            ModLog.warn("getNormalizedVolume(%s): Null category parameter", soundName);
         } else {
            try {
               float volumeScale = SoundRegistry.getVolumeScale(soundName);
               result = (float)MathHelper.clamp_double((double)sound.getVolume() * poolEntry.getVolume() * (double)getVolume(category) * (double)volumeScale, (double)0.0F, (double)1.0F);
            } catch (Throwable t) {
               ModLog.error("getNormalizedVolume(): Unable to calculate " + soundName, t);
            }
         }

         return result;
      }
   }

   public static float getNormalizedPitch(ISound sound, SoundPoolEntry poolEntry) {
      float result = 0.0F;
      if (sound == null) {
         ModLog.warn("getNormalizedPitch(): Null sound parameter");
         return result;
      } else {
         String soundName = sound.getPositionedSoundLocation().toString();
         if (poolEntry == null) {
            ModLog.warn("getNormalizedPitch(%s): Null poolEntry parameter", soundName);
         } else {
            try {
               result = (float)MathHelper.clamp_double((double)sound.getPitch() * poolEntry.getPitch(), (double)0.5F, (double)2.0F);
            } catch (Throwable t) {
               ModLog.error("getNormalizedPitch(): Unable to calculate " + soundName, t);
            }
         }

         return result;
      }
   }

   public static void configureSound() {
      int totalChannels = -1;

      try {
         boolean create = !AL.isCreated();
         if (create) {
            AL.create();
         }

         IntBuffer ib = BufferUtils.createIntBuffer(1);
         ALC10.alcGetInteger(AL.getDevice(), 4112, ib);
         totalChannels = ib.get(0);
         if (create) {
            AL.destroy();
         }
      } catch (Throwable e) {
         e.printStackTrace();
      }

      normalChannelCount = ModOptions.normalSoundChannelCount;
      streamChannelCount = ModOptions.streamingSoundChannelCount;
      if (ModOptions.autoConfigureChannels && totalChannels > 64) {
         totalChannels = (totalChannels + 1) * 3 / 4;
         streamChannelCount = totalChannels / 5;
         normalChannelCount = totalChannels - streamChannelCount;
      }

      ModLog.info("Sound channels: %d normal, %d streaming (total avail: %s)", normalChannelCount, streamChannelCount, totalChannels == -1 ? "UNKNOWN" : Integer.toString(totalChannels));
      SoundSystemConfig.setNumberNormalChannels(normalChannelCount);
      SoundSystemConfig.setNumberStreamingChannels(streamChannelCount);
   }
}
