package org.blockartistry.mod.DynSurround.client.hud;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.blockartistry.mod.DynSurround.ModOptions;

@SideOnly(Side.CLIENT)
public final class GuiHUDHandler {
   private static final List<IGuiOverlay> overlays = new ArrayList();

   private GuiHUDHandler() {
   }

   public static void register(IGuiOverlay overlay) {
      overlays.add(overlay);
   }

   public static void initialize() {
      if (ModOptions.potionHudEnabled) {
         register(new PotionHUD());
      }

      MinecraftForge.EVENT_BUS.register(new GuiHUDHandler());
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void onRenderExperienceBar(RenderGameOverlayEvent event) {
      for(IGuiOverlay overlay : overlays) {
         overlay.doRender(event);
      }

   }

   public interface IGuiOverlay {
      void doRender(RenderGameOverlayEvent var1);
   }
}
