package org.blockartistry.mod.DynSurround.data;

import gnu.trove.map.hash.TObjectFloatHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.event.SoundConfigEvent;

public final class SoundRegistry {
   private static final List<Pattern> cullSoundNamePatterns = new ArrayList();
   private static final List<Pattern> blockSoundNamePatterns = new ArrayList();
   private static final TObjectFloatHashMap<String> volumeControl = new TObjectFloatHashMap(10, 0.5F, 1.0F);

   public static void initialize() {
      cullSoundNamePatterns.clear();
      blockSoundNamePatterns.clear();
      volumeControl.clear();

      for(String sound : ModOptions.culledSounds) {
         try {
            cullSoundNamePatterns.add(Pattern.compile(sound));
         } catch (Throwable var8) {
            ModLog.warn("Unable to compile pattern for culled sound '%s'", sound);
         }
      }

      for(String sound : ModOptions.blockedSounds) {
         try {
            blockSoundNamePatterns.add(Pattern.compile(sound));
         } catch (Throwable var7) {
            ModLog.warn("Unable to compile pattern for blocked sound '%s'", sound);
         }
      }

      for(String volume : ModOptions.soundVolumes) {
         String[] tokens = StringUtils.split(volume, "=");
         if (tokens.length == 2) {
            try {
               float vol = (float)Integer.parseInt(tokens[1]) / 100.0F;
               volumeControl.put(tokens[0], vol);
            } catch (Throwable t) {
               ModLog.error("Unable to process sound volume entry: " + volume, t);
            }
         }
      }

      MinecraftForge.EVENT_BUS.post(new SoundConfigEvent.Reload());
   }

   private SoundRegistry() {
   }

   public static boolean isSoundCulled(String sound) {
      for(Pattern pattern : cullSoundNamePatterns) {
         if (pattern.matcher(sound).matches()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSoundBlocked(String sound) {
      for(Pattern pattern : blockSoundNamePatterns) {
         if (pattern.matcher(sound).matches()) {
            return true;
         }
      }

      return false;
   }

   public static float getVolumeScale(String soundName) {
      return volumeControl.get(soundName);
   }
}
