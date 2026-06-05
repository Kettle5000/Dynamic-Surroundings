package org.blockartistry.mod.DynSurround.client.sound.cache;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class MemoryURLConnection extends URLConnection {
   protected final byte[] buffer;

   protected MemoryURLConnection(@Nonnull URL url, @Nonnull byte[] buffer) {
      super(url);
      this.buffer = buffer;
   }

   public void connect() throws IOException {
   }

   public InputStream getInputStream() throws IOException {
      return new ByteArrayInputStream(this.buffer);
   }
}
