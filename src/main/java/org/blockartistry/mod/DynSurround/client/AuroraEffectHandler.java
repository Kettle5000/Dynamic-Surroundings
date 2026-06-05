package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.aurora.Aurora;
import org.blockartistry.mod.DynSurround.data.AuroraData;
import org.blockartistry.mod.DynSurround.util.DiurnalUtils;

@SideOnly(Side.CLIENT)
public final class AuroraEffectHandler implements IClientEffectHandler {
   private static int auroraDimension = 0;
   private static final Set<AuroraData> auroras = new HashSet();
   public static Aurora currentAurora;

   public static void addAurora(AuroraData data) {
      if (ModOptions.auroraEnable) {
         synchronized(auroras) {
            if (auroraDimension != data.dimensionId || EnvironStateHandler.EnvironState.getDimensionId() != data.dimensionId) {
               auroras.clear();
               currentAurora = null;
               auroraDimension = data.dimensionId;
            }

            auroras.add(data);
         }
      }
   }

   private Aurora getClosestAurora(World world) {
      AuroraData ad = null;
      synchronized(auroras) {
         if (auroraDimension != EnvironStateHandler.EnvironState.getDimensionId() || DiurnalUtils.isDaytime(world)) {
            auroras.clear();
         }

         if (auroras.size() == 0) {
            currentAurora = null;
            return null;
         }

         EntityPlayer player = EnvironStateHandler.EnvironState.getPlayer();
         int playerX = (int)player.posX;
         int playerZ = (int)player.posZ;
         boolean started = false;
         int distanceSq = 0;

         for(AuroraData data : auroras) {
            int deltaX = data.posX - playerX;
            int deltaZ = data.posZ - playerZ;
            int d = deltaX * deltaX + deltaZ * deltaZ;
            if (!started || distanceSq > d) {
               started = true;
               distanceSq = d;
               ad = data;
            }
         }
      }

      if (ad == null) {
         currentAurora = null;
      } else if (currentAurora == null || currentAurora.posX != (float)ad.posX && currentAurora.posZ != (float)ad.posZ) {
         ModLog.debug("New aurora: " + ad.toString());
         currentAurora = new Aurora(ad);
      }

      return currentAurora;
   }

   public boolean hasEvents() {
      return false;
   }

   public void process(World world, EntityPlayer player) {
      Aurora aurora = this.getClosestAurora(world);
      if (aurora != null) {
         aurora.update();
         if (aurora.isAlive() && DiurnalUtils.isSunrise(world)) {
            ModLog.debug("Aurora fade...");
            aurora.die();
         }
      }

   }
}
