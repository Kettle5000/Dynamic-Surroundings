package org.blockartistry.mod.DynSurround;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.blockartistry.mod.DynSurround.proxy.Proxy;

@Mod(
   modid = "dsurround",
   useMetadata = true,
   dependencies = "required-after:Forge@[10.13.4.1614,)",
   version = "1.7.10-1.0.0",
   guiFactory = "org.blockartistry.mod.DynSurround.client.gui.ConfigGuiFactory"
)
public class Module {
   public static final String MOD_ID = "dsurround";
   public static final String MOD_NAME = "Dynamic Surroundings";
   public static final String VERSION = "1.7.10-1.0.0";
   public static final String DEPENDENCIES = "required-after:Forge@[10.13.4.1614,)";
   public static final String GUI_FACTORY = "org.blockartistry.mod.DynSurround.client.gui.ConfigGuiFactory";
   @Instance("dsurround")
   protected static Module instance;
   @SidedProxy(
      clientSide = "org.blockartistry.mod.DynSurround.proxy.ProxyClient",
      serverSide = "org.blockartistry.mod.DynSurround.proxy.Proxy"
   )
   protected static Proxy proxy;
   protected static Configuration config;
   protected static File dataDirectory;

   public static Module instance() {
      return instance;
   }

   public static Proxy proxy() {
      return proxy;
   }

   public static Configuration config() {
      return config;
   }

   public static File dataDirectory() {
      return dataDirectory;
   }

   public Module() {
      ModLog.setLogger(LogManager.getLogger("dsurround"));
   }

   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      FMLCommonHandler.instance().bus().register(this);
      dataDirectory = new File(event.getModConfigurationDirectory(), "dsurround");
      dataDirectory.mkdirs();
      config = new Configuration(new File(dataDirectory, "dsurround.cfg"));
      config.load();
      ModOptions.load(config);
      config.save();
      ModLog.DEBUGGING = ModOptions.enableDebugLogging;
      proxy.preInit(event);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      proxy.init(event);
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      proxy.postInit(event);
      config.save();
   }

   @EventHandler
   public void serverStarting(FMLServerStartingEvent event) {
      proxy.serverStarting(event);
   }
}
