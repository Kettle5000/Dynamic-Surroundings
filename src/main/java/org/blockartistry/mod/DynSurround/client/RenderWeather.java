package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.EntityRenderer;
import org.blockartistry.mod.DynSurround.client.aurora.AuroraRenderer;
import org.blockartistry.mod.DynSurround.client.weather.StormRenderer;
import org.blockartistry.mod.DynSurround.client.weather.StormSplashRenderer;

@SideOnly(Side.CLIENT)
public final class RenderWeather {
   private static final List<IAtmosRenderer> renderList = new ArrayList();

   public static void register(IAtmosRenderer renderer) {
      renderList.add(renderer);
   }

   public static void addRainParticles(EntityRenderer theThis) {
      StormSplashRenderer.renderStormSplashes(EnvironStateHandler.EnvironState.getDimensionId(), theThis);
   }

   public static void renderRainSnow(EntityRenderer theThis, float partialTicks) {
      for(IAtmosRenderer renderer : renderList) {
         renderer.render(theThis, partialTicks);
      }

   }

   static {
      register(new StormRenderer());
      register(new AuroraRenderer());
   }
}
