package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.blockartistry.mod.DynSurround.util.Color;

@SideOnly(Side.CLIENT)
public interface IFogColorCalculator {
   @Nonnull
   Color calculate(@Nonnull EntityViewRenderEvent.FogColors var1);

   void tick();
}
