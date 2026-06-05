package org.blockartistry.mod.DynSurround.client.sound.cache;

import com.google.common.io.ByteStreams;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.blockartistry.mod.DynSurround.ModLog;

@SideOnly(Side.CLIENT)
public final class SoundCache {
   private static final int BUFFER_SIZE = 65536;
   private static final byte[] BUFFER = new byte[65536];
   private static final IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
   private static final Map<ResourceLocation, URL> cache = new HashMap(256);
   private static final byte[] SILENCE = getBuffer(new ResourceLocation("dsurround:sounds/ambient/silence.ogg"));

   private static byte[] getBuffer(@Nonnull ResourceLocation resource) {
      InputStream stream = null;

      byte[] var2;
      try {
         stream = manager.getResource(resource).getInputStream();
         if (stream != null) {
            if (stream.available() >= 65536) {
               return null;
            }

            int bytesRead = ByteStreams.read(stream, BUFFER, 0, 65536);
            if (bytesRead != 0 && bytesRead != 65536) {
               byte[] var20 = Arrays.copyOf(BUFFER, bytesRead);
               return var20;
            }

            byte[] var19 = null;
            return var19;
         }

         ModLog.warn("No stream returned for [%s]", resource.toString());
         var2 = SILENCE;
      } catch (Throwable var16) {
         ModLog.warn("Error reading stream [%s]", resource.toString());
         byte[] var3 = SILENCE;
         return var3;
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (Throwable var15) {
            }
         }

      }

      return var2;
   }

   private static URL load(@Nonnull ResourceLocation key) throws Exception {
      byte[] buffer = getBuffer(key);
      SoundStreamHandler handler;
      if (buffer == null) {
         handler = new SoundStreamHandler(key);
      } else {
         handler = new MemoryStreamHandler(key, buffer);
      }

      return new URL((URL)null, handler.getSpec(), handler);
   }

   private SoundCache() {
   }

   public static URL getURLForSoundResource(@Nonnull ResourceLocation soundResource) {
      URL result = (URL)cache.get(soundResource);
      if (result == null) {
         try {
            cache.put(soundResource, result = load(soundResource));
         } catch (Throwable var3) {
            throw new Error("Unable to load sound from cache!");
         }
      }

      return result;
   }
}
