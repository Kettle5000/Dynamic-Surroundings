package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;

@SideOnly(Side.CLIENT)
public class HazeFogRangeCalculator extends VanillaFogRangeCalculator {
   protected static final int BAND_OFFSETS = 15;
   protected static final int BAND_CORE_SIZE = 10;
   protected static final float IMPACT_FAR = 0.6F;
   protected static final float IMPACT_NEAR = 0.95F;
   protected final FogResult cached = new FogResult();

   @Nonnull
   public FogResult calculate(@Nonnull EntityViewRenderEvent.RenderFogEvent event) {
      DimensionRegistry di = EnvironStateHandler.EnvironState.getDimensionInfo();
      if (di == null) {
         this.cached.set(event);
         return this.cached;
      } else {
         if (di.getHasHaze()) {
            float lowY = (float)(di.getCloudHeight() - 15);
            float highY = (float)(di.getCloudHeight() + 15 + 10);
            double eyeHeight = EnvironStateHandler.EnvironState.getPlayer().posY + (double)EnvironStateHandler.EnvironState.getPlayer().eyeHeight;
            if (eyeHeight >= (double)lowY && eyeHeight <= (double)highY) {
               float coreLowY = lowY + 15.0F;
               float coreHighY = coreLowY + 10.0F;
               float scaleFar = 0.6F;
               float scaleNear = 0.95F;
               if (eyeHeight < (double)coreLowY) {
                  float factor = (float)((eyeHeight - (double)lowY) / (double)15.0F);
                  scaleFar *= factor;
                  scaleNear *= factor;
               } else if (eyeHeight > (double)coreHighY) {
                  float factor = (float)(((double)highY - eyeHeight) / (double)15.0F);
                  scaleFar *= factor;
                  scaleNear *= factor;
               }

               float end = event.farPlaneDistance * (1.0F - scaleFar);
               float start = event.farPlaneDistance * (1.0F - scaleNear);
               this.cached.set(start, end);
               return this.cached;
            }
         }

         this.cached.set(event);
         return this.cached;
      }
   }
}
