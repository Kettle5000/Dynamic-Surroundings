package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;

@SideOnly(Side.CLIENT)
public class SimultaneousAcoustic implements IAcoustic {
   protected final List<IAcoustic> acoustics;

   public SimultaneousAcoustic(Collection<IAcoustic> acoustics) {
      this.acoustics = new ArrayList(acoustics);
   }

   public void playSound(ISoundPlayer player, Object location, EventType event, IOptions inputOptions) {
      for(IAcoustic acoustic : this.acoustics) {
         acoustic.playSound(player, location, event, inputOptions);
      }

   }
}
