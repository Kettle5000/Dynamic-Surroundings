package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.Footsteps;
import org.blockartistry.mod.DynSurround.client.fx.BlockEffectHandler;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;

@SideOnly(Side.CLIENT)
public class ClientEffectHandler {
   private static final List<IClientEffectHandler> effectHandlers = new ArrayList();

   public static void register(IClientEffectHandler handler) {
      effectHandlers.add(handler);
      if (handler.hasEvents()) {
         MinecraftForge.EVENT_BUS.register(handler);
         FMLCommonHandler.instance().bus().register(handler);
      }

   }

   private ClientEffectHandler() {
   }

   public static void initialize() {
      ClientEffectHandler handler = new ClientEffectHandler();
      MinecraftForge.EVENT_BUS.register(handler);
      FMLCommonHandler.instance().bus().register(handler);
      register(new EnvironStateHandler());
      register(new ItemSoundHandler());
      register(new BiomeSurveyHandler());
      register(new FogEffectHandler());
      register(new BlockEffectHandler());
      if (ModOptions.blockedSounds.length > 0 || ModOptions.culledSounds.length > 0) {
         register(new SoundBlockHandler());
      }

      if (ModOptions.enableFootstepSounds) {
         register(new Footsteps());
      }

      if (ModOptions.auroraEnable) {
         register(new AuroraEffectHandler());
      }

      if (ModOptions.enableBiomeSounds) {
         register(new PlayerSoundEffectHandler());
      }

      if (ModOptions.suppressPotionParticles) {
         register(new PotionParticleScrubHandler());
      }

   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void clientTick(TickEvent.ClientTickEvent event) {
      if (!Minecraft.getMinecraft().isGamePaused()) {
         World world = FMLClientHandler.instance().getClient().theWorld;
         if (world != null) {
            if (event.phase == Phase.START) {
               EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;

               for(IClientEffectHandler handler : effectHandlers) {
                  handler.process(world, player);
               }
            }

         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onWorldLoad(WorldEvent.Load e) {
      if (e.world.isRemote) {
         DimensionRegistry.loading(e.world);
      }
   }
}
