package org.blockartistry.mod.DynSurround.client.footsteps.game.system;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.Module;

@SideOnly(Side.CLIENT)
public class PFResourcePackDealer {
   private final ResourceLocation pf_pack = new ResourceLocation("footsteps", "pf_pack.json");
   private final ResourceLocation acoustics = new ResourceLocation("footsteps", "acoustics.json");
   private final ResourceLocation blockmap = new ResourceLocation("footsteps", "blockmap.json");
   private final ResourceLocation primitivemap = new ResourceLocation("footsteps", "primitivemap.json");
   private final ResourceLocation variator = new ResourceLocation("footsteps", "variator.json");

   public List<IResourcePack> findResourcePacks() {
      List<ResourcePackRepository.Entry> repo = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
      List<IResourcePack> foundEntries = new ArrayList();
      foundEntries.add(new DefaultPack());

      for(ModContainer mod : Loader.instance().getActiveModList()) {
         foundEntries.add(new DefaultPack(mod.getModId()));
      }

      for(ResourcePackRepository.Entry pack : repo) {
         ModLog.debug("Resource Pack: %s", pack.getResourcePackName());
         if (this.checkCompatible(pack)) {
            ModLog.debug("Found Footsteps resource pack: %s", pack.getResourcePackName());
            foundEntries.add(pack.getResourcePack());
         }
      }

      return foundEntries;
   }

   private boolean checkCompatible(ResourcePackRepository.Entry pack) {
      return pack.getResourcePack().resourceExists(this.pf_pack);
   }

   public InputStream openPackDescriptor(IResourcePack pack) throws IOException {
      return pack.getInputStream(this.pf_pack);
   }

   public InputStream openAcoustics(IResourcePack pack) throws IOException {
      return pack.getInputStream(this.acoustics);
   }

   public InputStream openBlockMap(IResourcePack pack) throws IOException {
      return pack.getInputStream(this.blockmap);
   }

   public InputStream openPrimitiveMap(IResourcePack pack) throws IOException {
      return pack.getInputStream(this.primitivemap);
   }

   public InputStream openVariator(IResourcePack pack) throws IOException {
      return pack.getInputStream(this.variator);
   }

   private static class DefaultPack implements IResourcePack {
      private final String mod;

      public DefaultPack() {
         this.mod = null;
      }

      public DefaultPack(String mod) {
         this.mod = mod;
      }

      public InputStream getInputStream(ResourceLocation loc) throws IOException {
         StringBuilder builder = new StringBuilder();
         builder.append("/assets/dsurround/data/");
         builder.append(loc.getResourceDomain());
         builder.append('/');
         if (this.mod != null) {
            builder.append(this.mod).append('_');
         }

         builder.append(loc.getResourcePath());
         return Module.class.getResourceAsStream(builder.toString());
      }

      public boolean resourceExists(ResourceLocation loc) {
         return true;
      }

      public Set getResourceDomains() {
         return null;
      }

      public IMetadataSection getPackMetadata(IMetadataSerializer loc, String data) throws IOException {
         return null;
      }

      public BufferedImage getPackImage() throws IOException {
         return null;
      }

      public String getPackName() {
         return this.mod == null ? "DEFAULT" : "DEFAULT: " + this.mod;
      }
   }
}
