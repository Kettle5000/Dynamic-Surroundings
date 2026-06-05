package org.blockartistry.mod.DynSurround.client.sound.cache;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ResourceURLConnection extends URLConnection {
   protected final ResourceLocation resource;

   protected ResourceURLConnection(@Nonnull URL url, @Nonnull ResourceLocation resource) {
      super(url);
      this.resource = resource;
   }

   public void connect() throws IOException {
   }

   public InputStream getInputStream() throws IOException {
      InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(this.resource).getInputStream();
      return new SoundInputStream(stream);
   }
}
