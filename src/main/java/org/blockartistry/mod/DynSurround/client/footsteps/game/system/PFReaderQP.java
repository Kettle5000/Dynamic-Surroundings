package org.blockartistry.mod.DynSurround.client.footsteps.game.system;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IIsolator;

@SideOnly(Side.CLIENT)
public class PFReaderQP extends PFReaderH {
   private int hoof = 0;
   private final int USE_FUNCTION = 2;
   private float nextWalkDistanceMultiplier = 0.05F;
   private final Random rand = new Random();

   public PFReaderQP(IIsolator isolator) {
      super(isolator);
   }

   protected void stepped(EntityPlayer ply, EventType event) {
      if (this.hoof == 0 || this.hoof == 2) {
         this.nextWalkDistanceMultiplier = this.rand.nextFloat();
      }

      if (this.hoof >= 3) {
         this.hoof = 0;
      } else {
         ++this.hoof;
      }

      if (this.hoof == 3 && event == EventType.RUN) {
         this.produceStep(ply, event);
         this.hoof = 0;
      }

      if (event == EventType.WALK) {
         this.produceStep(ply, event);
      }

   }

   protected float reevaluateDistance(EventType event, float distance) {
      if (event == EventType.WALK) {
         this.getClass();
         if (2 == 2) {
            float overallMultiplier = 0.925F;
            float ndm = 0.2F;
            float pond = this.nextWalkDistanceMultiplier;
            pond *= pond;
            pond *= 0.2F;
            if (this.hoof != 1 && this.hoof != 3) {
               return distance * (1.0F - pond) * 0.925F;
            }

            return distance * pond * 0.925F;
         }

         this.getClass();
         if (2 == 1) {
            float overallMultiplier = 1.4F;
            float ndm = 0.5F;
            if (this.hoof != 1 && this.hoof != 3) {
               return distance * 0.5F * 1.4F;
            }

            return distance * (0.5F + this.nextWalkDistanceMultiplier * 0.5F * 0.5F) * 1.4F;
         }

         this.getClass();
         if (2 == 0) {
            float overallMultiplier = 1.5F;
            float ndm = 0.425F + this.nextWalkDistanceMultiplier * 0.15F;
            if (this.hoof != 1 && this.hoof != 3) {
               return distance * (1.0F - ndm) * 1.5F;
            }

            return distance * ndm * 1.5F;
         }
      }

      if (event == EventType.RUN && this.hoof == 0) {
         return distance * 0.8F;
      } else {
         return event == EventType.RUN ? distance * 0.3F : distance;
      }
   }
}
