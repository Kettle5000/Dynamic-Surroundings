package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ILibrary;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.INamedAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;
import org.blockartistry.mod.DynSurround.client.footsteps.game.system.Association;

@SideOnly(Side.CLIENT)
public abstract class AcousticsLibrary implements ILibrary {
   private final Map<String, IAcoustic> acoustics = new LinkedHashMap();

   public void addAcoustic(INamedAcoustic acoustic) {
      this.acoustics.put(acoustic.getName(), acoustic);
   }

   public void playAcoustic(Object location, Association acousticName, EventType event) {
      this.playAcoustic(location, acousticName.getData(), event, (IOptions)null);
   }

   public void playAcoustic(Object location, String acousticName, EventType event, IOptions inputOptions) {
      if (StringUtils.isEmpty(acousticName)) {
         ModLog.debug("Attempt to play acoustic with no name");
      } else {
         String[] fragments = acousticName.split(",");

         for(String fragment : fragments) {
            IAcoustic acoustic = (IAcoustic)this.acoustics.get(fragment);
            if (acoustic == null) {
               this.onAcousticNotFound(location, fragment, event, inputOptions);
            } else {
               if (ModLog.DEBUGGING) {
                  ModLog.debug("  Playing acoustic " + acousticName + " for event " + event.toString().toUpperCase());
               }

               acoustic.playSound(this.mySoundPlayer(), location, event, inputOptions);
            }
         }

      }
   }

   protected void onAcousticNotFound(Object location, String acousticName, EventType event, IOptions inputOptions) {
      ModLog.debug("Tried to play a missing acoustic: " + acousticName);
   }

   protected abstract ISoundPlayer mySoundPlayer();
}
