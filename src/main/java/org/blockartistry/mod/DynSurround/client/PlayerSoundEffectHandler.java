package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.blockartistry.mod.DynSurround.ModOptions;
import org.blockartistry.mod.DynSurround.client.sound.SoundEffect;
import org.blockartistry.mod.DynSurround.client.sound.SoundManager;
import org.blockartistry.mod.DynSurround.client.weather.Weather;
import org.blockartistry.mod.DynSurround.compat.BlockPos;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;
import org.blockartistry.mod.DynSurround.event.DiagnosticEvent;
import org.blockartistry.mod.DynSurround.event.RegistryReloadEvent;

@SideOnly(Side.CLIENT)
public class PlayerSoundEffectHandler implements IClientEffectHandler {
   private static final List<EntityDropParticleFX> drops = new ArrayList();

   private static boolean doBiomeSounds() {
      return EnvironStateHandler.EnvironState.isPlayerUnderground() || !EnvironStateHandler.EnvironState.isPlayerInside();
   }

   private static List<SoundEffect> getBiomeSounds(String conditions) {
      TObjectIntHashMap<SoundEffect> sounds = new TObjectIntHashMap();
      TObjectIntHashMap<BiomeGenBase> weights = BiomeSurveyHandler.getBiomes();

      for(BiomeGenBase biome : weights.keySet()) {
         for(SoundEffect sound : BiomeRegistry.getSounds(biome, conditions)) {
            sounds.put(sound, sounds.get(sound) + weights.get(biome));
         }
      }

      List<SoundEffect> result = new ArrayList();
      int area = BiomeSurveyHandler.getArea();

      for(SoundEffect sound : sounds.keySet()) {
         float scale = 0.3F + 0.7F * ((float)sounds.get(sound) / (float)area);
         result.add(SoundEffect.scaleVolume(sound, scale));
      }

      return result;
   }

   private static void resetSounds() {
      SoundManager.clearSounds();
      drops.clear();
   }

   public void process(World world, EntityPlayer player) {
      if (player.isDead) {
         resetSounds();
      } else {
         BiomeGenBase playerBiome = EnvironStateHandler.EnvironState.getPlayerBiome();
         String conditions = EnvironStateHandler.EnvironState.getConditions();
         List<SoundEffect> sounds = new ArrayList();
         if (doBiomeSounds()) {
            sounds.addAll(getBiomeSounds(conditions));
         }

         sounds.addAll(BiomeRegistry.getSounds(BiomeRegistry.PLAYER, conditions));
         SoundManager.queueAmbientSounds(sounds);
         if (doBiomeSounds()) {
            SoundEffect sound = BiomeRegistry.getSpotSound(playerBiome, conditions, EnvironStateHandler.EnvironState.RANDOM);
            if (sound != null) {
               SoundManager.playSoundAtPlayer(player, sound);
            }
         }

         SoundEffect sound = BiomeRegistry.getSpotSound(BiomeRegistry.PLAYER, conditions, EnvironStateHandler.EnvironState.RANDOM);
         if (sound != null) {
            SoundManager.playSoundAtPlayer(player, sound);
         }

         this.processWaterDrops();
         SoundManager.update();
      }
   }

   public boolean hasEvents() {
      return true;
   }

   @SubscribeEvent
   public void registryReloadEvent(RegistryReloadEvent.Biome event) {
      resetSounds();
   }

   @SubscribeEvent
   public void playerJoinWorldEvent(EntityJoinWorldEvent event) {
      if (event.entity.worldObj.isRemote && EnvironStateHandler.EnvironState.isPlayer(event.entity)) {
         resetSounds();
      }

   }

   @SubscribeEvent
   public void diagnostics(DiagnosticEvent.Gather event) {
      StringBuilder builder = new StringBuilder();
      builder.append("SoundSystem: ").append(SoundManager.currentSoundCount()).append('/').append(SoundManager.maxSoundCount());
      event.output.add(builder.toString());

      for(String sound : SoundManager.getSounds()) {
         event.output.add(sound);
      }

   }

   @SubscribeEvent
   public void entityCreateEvent(EntityEvent.EntityConstructing event) {
      if (event.entity instanceof EntityDropParticleFX) {
         drops.add((EntityDropParticleFX)event.entity);
      }

   }

   private void processWaterDrops() {
      if (!drops.isEmpty()) {
         World world = EnvironStateHandler.EnvironState.getWorld();

         for(EntityDropParticleFX drop : drops) {
            if (drop.isEntityAlive() && !(drop.posY < (double)1.0F)) {
               int x = MathHelper.floor_double(drop.posX);
               int y = MathHelper.floor_double(drop.posY + 0.3);
               int z = MathHelper.floor_double(drop.posZ);
               Block block = world.getBlock(x, y, z);
               if (block != Blocks.air && !block.isLeaves(world, x, y, z)) {
                  int soundY;
                  for(soundY = y - 1; soundY > 0 && (block = world.getBlock(x, soundY, z)) == Blocks.air; --soundY) {
                  }

                  if (soundY > 0 && block.getMaterial().isSolid()) {
                     int distance = y - soundY;
                     SoundManager.playSoundAt(new BlockPos(x, soundY + 1, z), BiomeRegistry.WATER_DRIP, 40 + distance * 2);
                  }
               }
            }
         }

         drops.clear();
      }
   }

   private static boolean replaceRainSound(String name) {
      return "ambient.weather.rain".equals(name);
   }

   @SubscribeEvent
   public void soundEvent(PlaySoundEvent17 event) {
      if (event.sound != null) {
         if ((ModOptions.alwaysOverrideSound || !Weather.doVanilla()) && replaceRainSound(event.name)) {
            ISound sound = event.sound;
            event.result = new PositionedSoundRecord(Weather.getCurrentStormSound(), Weather.getCurrentVolume(), sound.getPitch(), sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
         }

      }
   }
}
