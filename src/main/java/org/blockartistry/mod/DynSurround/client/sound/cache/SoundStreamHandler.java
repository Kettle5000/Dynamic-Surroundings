package org.blockartistry.mod.DynSurround.client.sound.cache;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import javax.annotation.Nonnull;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SoundStreamHandler extends URLStreamHandler {
   protected final ResourceLocation resource;
   protected URLConnection connection;

   public SoundStreamHandler(@Nonnull ResourceLocation resource) {
      this.resource = resource;
   }

   protected URLConnection createConnection(@Nonnull URL url) {
      return new ResourceURLConnection(url, this.resource);
   }

   protected URLConnection openConnection(@Nonnull URL url) throws IOException {
      if (this.connection == null) {
         this.connection = this.createConnection(url);
      }

      return this.connection;
   }

   public String getSpec() {
      return String.format("%s:%s:%s", "mcsounddomain", this.resource.getResourceDomain(), this.resource.getResourcePath());
   }
}
