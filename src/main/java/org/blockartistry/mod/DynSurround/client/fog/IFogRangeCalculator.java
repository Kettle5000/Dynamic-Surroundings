package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;

@SideOnly(Side.CLIENT)
public interface IFogRangeCalculator {
   @Nonnull
   FogResult calculate(@Nonnull EntityViewRenderEvent.RenderFogEvent var1);

   void tick();
}
