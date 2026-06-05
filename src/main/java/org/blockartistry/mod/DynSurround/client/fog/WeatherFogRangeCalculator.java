package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.blockartistry.mod.DynSurround.client.weather.Weather;

@SideOnly(Side.CLIENT)
public class WeatherFogRangeCalculator extends VanillaFogRangeCalculator {
   protected static final float START_IMPACT = 0.9F;
   protected static final float END_IMPACT = 0.4F;
   protected final FogResult cache = new FogResult();

   @Nonnull
   public FogResult calculate(@Nonnull EntityViewRenderEvent.RenderFogEvent event) {
      this.cache.set(event);
      float rainStr = Weather.getRainStrength();
      if (rainStr > 0.0F) {
         float startScale = 1.0F - 0.9F * rainStr;
         float endScale = 1.0F - 0.4F * rainStr;
         this.cache.set(this.cache.getStart() * startScale, this.cache.getEnd() * endScale);
      }

      return this.cache;
   }
}
