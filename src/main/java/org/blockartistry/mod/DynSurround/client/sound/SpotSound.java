package org.blockartistry.mod.DynSurround.client.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.EnvironStateHandler;
import org.blockartistry.mod.DynSurround.compat.BlockPos;

@SideOnly(Side.CLIENT)
public class SpotSound extends PositionedSound {
   private static final int SPOT_SOUND_RANGE = 6;
   private final SoundEffect sound;
   private final int timeMark;

   public SpotSound(SoundEffect sound) {
      super(new ResourceLocation(sound.sound));
      this.sound = sound;
      this.volume = sound.volume;
      this.field_147663_c = sound.getPitch(EnvironStateHandler.EnvironState.RANDOM);
      this.repeat = false;
      this.field_147665_h = 0;
      this.xPosF = (float)EnvironStateHandler.EnvironState.getPlayer().posX + 0.5F;
      this.yPosF = (float)EnvironStateHandler.EnvironState.getPlayer().posY + 32.0F;
      this.zPosF = (float)EnvironStateHandler.EnvironState.getPlayer().posZ + 0.5F;
      this.field_147666_i = AttenuationType.NONE;
      this.timeMark = EnvironStateHandler.EnvironState.getTickCounter();
   }

   public SpotSound(BlockPos pos, SoundEffect sound, int delay) {
      super(new ResourceLocation(sound.sound));
      this.sound = sound;
      this.volume = sound.volume;
      this.field_147663_c = sound.getPitch(EnvironStateHandler.EnvironState.RANDOM);
      this.repeat = false;
      this.field_147665_h = 0;
      this.xPosF = (float)pos.getX() + 0.5F;
      this.yPosF = (float)pos.getY() + 0.5F;
      this.zPosF = (float)pos.getZ() + 0.5F;
      this.timeMark = EnvironStateHandler.EnvironState.getTickCounter() + delay;
   }

   public SpotSound(EntityPlayer player, SoundEffect sound) {
      super(new ResourceLocation(sound.sound));
      this.sound = sound;
      this.volume = sound.volume;
      this.field_147663_c = sound.getPitch(EnvironStateHandler.EnvironState.RANDOM);
      this.repeat = false;
      this.field_147665_h = 0;
      this.xPosF = (float)MathHelper.floor_double(player.posX + (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6) - (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6));
      this.yPosF = (float)MathHelper.floor_double(player.posY + (double)1.0F + (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6) - (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6));
      this.zPosF = (float)MathHelper.floor_double(player.posZ + (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6) - (double)EnvironStateHandler.EnvironState.RANDOM.nextInt(6));
      this.timeMark = EnvironStateHandler.EnvironState.getTickCounter();
   }

   public float getVolume() {
      return super.getVolume() * ModOptions.masterSoundScaleFactor;
   }

   public int getTickAge() {
      return EnvironStateHandler.EnvironState.getTickCounter() - this.timeMark;
   }

   public SoundEffect getSoundEffect() {
      return this.sound;
   }

   public String toString() {
      return this.sound.toString();
   }
}
