package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.data.SoundRegistry;
import org.blockartistry.mod.DynSurround.event.SoundConfigEvent;

@SideOnly(Side.CLIENT)
public class SoundBlockHandler implements IClientEffectHandler {
   private final Set<String> soundsToBlock = new HashSet();
   private final TObjectIntHashMap<String> soundCull = new TObjectIntHashMap();

   public void process(World world, EntityPlayer player) {
   }

   public boolean hasEvents() {
      return true;
   }

   @SubscribeEvent
   public void soundConfigReload(SoundConfigEvent.Reload event) {
      this.soundsToBlock.clear();
      this.soundCull.clear();
      SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();

      for(Object resource : handler.sndRegistry.getKeys()) {
         String rs = resource.toString();
         if (SoundRegistry.isSoundBlocked(rs)) {
            ModLog.debug("Blocking sound '%s'", rs);
            this.soundsToBlock.add(rs);
         } else if (SoundRegistry.isSoundCulled(rs)) {
            ModLog.debug("Culling sound '%s'", rs);
            this.soundCull.put(rs, -ModOptions.soundCullingThreshold);
         }
      }

   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void soundEvent(PlaySoundEvent17 event) {
      if (event.sound != null && event.sound.getPositionedSoundLocation() != null) {
         String resource = event.sound.getPositionedSoundLocation().toString();
         if (this.soundsToBlock.contains(resource)) {
            event.result = null;
         } else if (ModOptions.soundCullingThreshold > 0) {
            int lastOccurance = this.soundCull.get(resource);
            if (lastOccurance != 0) {
               int currentTick = EnvironStateHandler.EnvironState.getTickCounter();
               if (currentTick - lastOccurance < ModOptions.soundCullingThreshold) {
                  event.result = null;
               } else {
                  this.soundCull.put(resource, currentTick);
               }

            }
         }
      }
   }
}
