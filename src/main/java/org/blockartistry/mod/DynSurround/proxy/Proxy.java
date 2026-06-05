package org.blockartistry.mod.DynSurround.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import org.blockartistry.mod.DynSurround.VersionCheck;
import org.blockartistry.mod.DynSurround.client.DamageEffectHandler;
import org.blockartistry.mod.DynSurround.commands.CommandRain;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;
import org.blockartistry.mod.DynSurround.network.Network;
import org.blockartistry.mod.DynSurround.server.ServerEffectHandler;

public class Proxy {
   public void preInit(FMLPreInitializationEvent event) {
      VersionCheck.register();
   }

   public void init(FMLInitializationEvent event) {
      Network.initialize();
      ServerEffectHandler.initialize();
      DamageEffectHandler.initialize();
   }

   public void postInit(FMLPostInitializationEvent event) {
      BiomeRegistry.initialize();
      DimensionRegistry.initialize();
   }

   public void serverStarting(FMLServerStartingEvent event) {
      MinecraftServer server = MinecraftServer.getServer();
      ICommandManager command = server.getCommandManager();
      ServerCommandManager serverCommand = (ServerCommandManager)command;
      serverCommand.registerCommand(new CommandRain());
   }
}
