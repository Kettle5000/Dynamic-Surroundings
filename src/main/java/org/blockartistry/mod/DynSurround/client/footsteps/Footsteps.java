package org.blockartistry.mod.DynSurround.client.footsteps;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.IClientEffectHandler;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.ForgeDictionary;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.PFIsolator;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.PFReaderH;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.PFResourcePackDealer;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.PFSolver;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.UserConfigSoundPlayerWrapper;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem.AcousticsManager;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem.BasicPrimitiveMap;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem.LegacyCapableBlockMap;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.implem.NormalVariator;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IBlockMap;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IPrimitiveMap;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IVariator;
import org.blockartistry.mod.DynSurround.client.footsteps.parsers.AcousticsJsonReader;
import org.blockartistry.mod.DynSurround.client.footsteps.parsers.Register;
import org.blockartistry.mod.DynSurround.client.footsteps.util.property.simple.ConfigProperty;

@SideOnly(Side.CLIENT)
public class Footsteps implements IResourceManagerReloadListener, IClientEffectHandler {
   public static Footsteps INSTANCE = null;
   private final PFResourcePackDealer dealer = new PFResourcePackDealer();
   private PFIsolator isolator;
   private boolean isFirstTime = true;

   public Footsteps() {
      INSTANCE = this;
      this.isolator = new PFIsolator();
   }

   public void reloadEverything() {
      this.isolator = new PFIsolator();
      List<IResourcePack> repo = this.dealer.findResourcePacks();
      this.reloadBlockMap(repo);
      this.reloadPrimitiveMap(repo);
      this.reloadAcoustics(repo);
      this.isolator.setSolver(new PFSolver(this.isolator));
      this.reloadVariator(repo);
      this.isolator.setGenerator(new PFReaderH(this.isolator));
   }

   private void reloadVariator(List<IResourcePack> repo) {
      IVariator var = new NormalVariator();

      for(IResourcePack pack : repo) {
         InputStream stream = null;

         try {
            stream = this.dealer.openVariator(pack);
            if (stream != null) {
               var.loadConfig(ConfigProperty.fromStream(stream));
            }
         } catch (Exception var15) {
            ModLog.debug("Unable to load variator data from pack %s", pack.getPackName());
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var14) {
               }
            }

         }
      }

      this.isolator.setVariator(var);
   }

   private void reloadBlockMap(List<IResourcePack> repo) {
      IBlockMap blockMap = new LegacyCapableBlockMap();
      ForgeDictionary.initialize(blockMap);

      for(IResourcePack pack : repo) {
         InputStream stream = null;

         try {
            stream = this.dealer.openBlockMap(pack);
            if (stream != null) {
               Register.setup(ConfigProperty.fromStream(stream), blockMap);
            }
         } catch (IOException var15) {
            ModLog.debug("Unable to load block map data from pack %s", pack.getPackName());
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var14) {
               }
            }

         }
      }

      this.isolator.setBlockMap(blockMap);
   }

   private void reloadPrimitiveMap(List<IResourcePack> repo) {
      IPrimitiveMap primitiveMap = new BasicPrimitiveMap();

      for(IResourcePack pack : repo) {
         InputStream stream = null;

         try {
            stream = this.dealer.openPrimitiveMap(pack);
            if (stream != null) {
               Register.setup(ConfigProperty.fromStream(stream), primitiveMap);
            }
         } catch (IOException var15) {
            ModLog.debug("Unable to load primitive map data from pack %s", pack.getPackName());
         } finally {
            if (stream != null) {
               try {
                  stream.close();
               } catch (IOException var14) {
               }
            }

         }
      }

      this.isolator.setPrimitiveMap(primitiveMap);
   }

   private void reloadAcoustics(List<IResourcePack> repo) {
      AcousticsManager acoustics = new AcousticsManager(this.isolator);
      Scanner scanner = null;
      InputStream stream = null;

      for(IResourcePack pack : repo) {
         try {
            stream = this.dealer.openAcoustics(pack);
            if (stream != null) {
               scanner = new Scanner(stream);
               String jasonString = scanner.useDelimiter("\\Z").next();
               (new AcousticsJsonReader("")).parseJSON(jasonString, acoustics);
            }
         } catch (IOException var16) {
            ModLog.debug("Unable to load acoustic data from pack %s", pack.getPackName());
         } finally {
            try {
               if (scanner != null) {
                  scanner.close();
               }

               if (stream != null) {
                  stream.close();
               }
            } catch (IOException var15) {
            }

         }
      }

      this.isolator.setAcoustics(acoustics);
      this.isolator.setSoundPlayer(new UserConfigSoundPlayerWrapper(acoustics));
      this.isolator.setDefaultStepPlayer(acoustics);
   }

   public void onResourceManagerReload(IResourceManager var1) {
      ModLog.info("Resource Pack reload detected...");
      this.reloadEverything();
   }

   public void process(World world, EntityPlayer player) {
      if (this.isFirstTime) {
         this.isFirstTime = false;
         this.reloadEverything();
      }

      this.isolator.onFrame();
      if (ModOptions.footstepsSoundFactor > 0.0F) {
         player.nextStepDistance = Integer.MAX_VALUE;
      } else if (player.nextStepDistance == Integer.MAX_VALUE) {
         player.nextStepDistance = 0;
      }

   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onWorldUnload(WorldEvent.Unload event) {
      if (event.world.isRemote && event.world.provider.dimensionId == 0) {
         this.isFirstTime = true;
      }

   }

   public boolean hasEvents() {
      return true;
   }

   public IBlockMap getBlockMap() {
      return this.isolator.getBlockMap();
   }
}
