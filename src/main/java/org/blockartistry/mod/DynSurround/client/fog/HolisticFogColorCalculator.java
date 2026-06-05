package org.blockartistry.mod.DynSurround.client.fog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import org.blockartistry.mod.DynSurround.util.Color;

@SideOnly(Side.CLIENT)
public class HolisticFogColorCalculator implements IFogColorCalculator {
   protected List<IFogColorCalculator> calculators = new ArrayList();
   protected Color cached;

   public void add(@Nonnull IFogColorCalculator calc) {
      this.calculators.add(calc);
   }

   public Color calculate(@Nonnull EntityViewRenderEvent.FogColors event) {
      Color result = null;

      for(IFogColorCalculator calc : this.calculators) {
         Color color = calc.calculate(event);
         if (result == null) {
            result = color;
         } else if (color != null) {
            result = result.mix(color);
         }
      }

      return this.cached = result;
   }

   public void tick() {
      for(IFogColorCalculator calc : this.calculators) {
         calc.tick();
      }

   }

   public String toString() {
      return this.cached != null ? this.cached.toString() : "<NOT SET>";
   }
}
