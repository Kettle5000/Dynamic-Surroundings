package org.blockartistry.mod.DynSurround.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.ClientEffectHandler;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.ForgeDictionary;
import org.blockartistry.mod.DynSurround.client.footsteps.game.user.GenerateBlockReport;
import org.blockartistry.mod.DynSurround.client.hud.GuiHUDHandler;
import org.blockartistry.mod.DynSurround.client.sound.SoundManager;
import org.blockartistry.mod.DynSurround.data.BlockRegistry;
import org.blockartistry.mod.DynSurround.data.SoundRegistry;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy {
   public void preInit(FMLPreInitializationEvent event) {
      super.preInit(event);
   }

   public void init(FMLInitializationEvent event) {
      super.init(event);
      BlockRegistry.initialize();
      ClientEffectHandler.initialize();
      GuiHUDHandler.initialize();
      SoundRegistry.initialize();
      SoundManager.configureSound();
   }

   public void postInit(FMLPostInitializationEvent event) {
      super.postInit(event);
      if (ModOptions.enableDebugLogging) {
         SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
         List<String> sounds = new ArrayList();

         for(Object resource : handler.sndRegistry.getKeys()) {
            sounds.add(resource.toString());
         }

         Collections.sort(sounds);
         ModLog.info("*** SOUND REGISTRY ***");

         for(String sound : sounds) {
            ModLog.info(sound);
         }

         ModLog.info("*** REGISTERED BLOCK NAMES ***");
         GenerateBlockReport report = new GenerateBlockReport();

         for(String entry : report.getBlockNames()) {
            ModLog.info(entry);
         }

         ForgeDictionary.dumpOreNames();
      }

   }
}
