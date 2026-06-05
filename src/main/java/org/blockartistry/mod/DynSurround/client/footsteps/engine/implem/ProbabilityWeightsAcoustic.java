package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;

@SideOnly(Side.CLIENT)
public class ProbabilityWeightsAcoustic implements IAcoustic {
   protected final List<IAcoustic> acoustics;
   protected final int[] weights;
   protected final int totalWeight;

   public ProbabilityWeightsAcoustic(List<IAcoustic> acoustics, List<Integer> weights) {
      this.acoustics = new ArrayList(acoustics);
      this.weights = new int[weights.size()];
      int tWeight = 0;

      for(int i = 0; i < weights.size(); ++i) {
         this.weights[i] = (Integer)weights.get(i);
         tWeight += this.weights[i];
      }

      this.totalWeight = tWeight;
   }

   public void playSound(ISoundPlayer player, Object location, EventType event, IOptions inputOptions) {
      if (this.totalWeight > 0) {
         int targetWeight = player.getRNG().nextInt(this.totalWeight);
         int i = 0;

         for(i = this.weights.length; (targetWeight -= this.weights[i - 1]) >= 0; --i) {
         }

         ((IAcoustic)this.acoustics.get(i - 1)).playSound(player, location, event, inputOptions);
      }
   }
}
