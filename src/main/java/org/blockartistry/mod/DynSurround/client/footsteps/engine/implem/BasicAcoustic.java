package org.blockartistry.mod.DynSurround.client.footsteps.engine.implem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.EventType;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IAcoustic;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.IOptions;
import org.blockartistry.mod.DynSurround.client.footsteps.engine.interfaces.ISoundPlayer;

@SideOnly(Side.CLIENT)
public class BasicAcoustic implements IAcoustic {
   protected String soundName;
   protected float volMin = 1.0F;
   protected float volMax = 1.0F;
   protected float pitchMin = 1.0F;
   protected float pitchMax = 1.0F;
   protected IOptions outputOptions;

   public void playSound(ISoundPlayer player, Object location, EventType event, IOptions inputOptions) {
      if (!StringUtils.isEmpty(this.soundName)) {
         float volume = this.generateVolume(player.getRNG());
         float pitch = this.generatePitch(player.getRNG());
         if (inputOptions != null) {
            if (inputOptions.hasOption(IOptions.Option.GLIDING_VOLUME)) {
               volume = this.volMin + (this.volMax - this.volMin) * (Float)inputOptions.getOption(IOptions.Option.GLIDING_VOLUME);
            }

            if (inputOptions.hasOption(IOptions.Option.GLIDING_PITCH)) {
               pitch = this.pitchMin + (this.pitchMax - this.pitchMin) * (Float)inputOptions.getOption(IOptions.Option.GLIDING_PITCH);
            }
         }

         player.playSound(location, this.soundName, volume, pitch, this.outputOptions);
      }
   }

   private float generateVolume(Random rng) {
      return this.randAB(rng, this.volMin, this.volMax);
   }

   private float generatePitch(Random rng) {
      return this.randAB(rng, this.pitchMin, this.pitchMax);
   }

   private float randAB(Random rng, float a, float b) {
      return a >= b ? a : a + rng.nextFloat() * (b - a);
   }

   public void setSoundName(String soundName) {
      this.soundName = soundName;
   }

   public void setVolMin(float volMin) {
      this.volMin = volMin;
   }

   public void setVolMax(float volMax) {
      this.volMax = volMax;
   }

   public void setPitchMin(float pitchMin) {
      this.pitchMin = pitchMin;
   }

   public void setPitchMax(float pitchMax) {
      this.pitchMax = pitchMax;
   }
}
