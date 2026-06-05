package org.blockartistry.mod.DynSurround.client.sound.fix;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.Field;
import paulscode.sound.Source;

public class SoundFixMethods {
   public static final Field removed = ReflectionHelper.findField(Source.class, new String[]{"removed"});

   private SoundFixMethods() {
   }

   public static Source removeSource(Source source) {
      try {
         if (removed.getBoolean(source)) {
            source.cleanup();
            return null;
         }
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }

      return source;
   }

   public static void cleanupSource(Source source) {
      if (source.toStream) {
         try {
            removed.setBoolean(source, true);
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
      } else {
         source.cleanup();
      }

   }
}
