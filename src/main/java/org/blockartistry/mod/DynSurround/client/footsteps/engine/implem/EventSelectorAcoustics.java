package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.EnumMap;
import java.util.Map;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.INamedAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;

@SideOnly(Side.CLIENT)
public class EventSelectorAcoustics implements INamedAcoustic {
   private final String name;
   private final Map<EventType, IAcoustic> pairs = new EnumMap(EventType.class);

   public EventSelectorAcoustics(String acousticName) {
      this.name = acousticName;
   }

   public String getName() {
      return this.name;
   }

   public void playSound(ISoundPlayer player, Object location, EventType event, IOptions inputOptions) {
      IAcoustic acoustic = (IAcoustic)this.pairs.get(event);
      if (acoustic != null) {
         acoustic.playSound(player, location, event, inputOptions);
      } else if (event.canTransition()) {
         this.playSound(player, location, event.getTransitionDestination(), inputOptions);
      }

   }

   public void setAcousticPair(EventType type, IAcoustic acoustic) {
      this.pairs.put(type, acoustic);
   }
}
