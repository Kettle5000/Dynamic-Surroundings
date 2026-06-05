package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;

@SideOnly(Side.CLIENT)
public class VanillaFogRangeCalculator implements IFogRangeCalculator {
   @Nonnull
   public FogResult calculate(@Nonnull EntityViewRenderEvent.RenderFogEvent event) {
      return new FogResult(event);
   }

   public void tick() {
   }
}
