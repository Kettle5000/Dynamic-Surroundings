package org.blockartistry.mod.DynSurround.data;

import net.minecraft.world.biome.BiomeGenBase;

public class FakeBiome extends BiomeGenBase {
   public FakeBiome(int biomeId, String biomeName) {
      super(biomeId, false);
      this.setBiomeName(biomeName);
      this.theBiomeDecorator = null;
      this.flowers = null;
      this.spawnableCaveCreatureList = null;
      this.spawnableCreatureList = null;
      this.spawnableMonsterList = null;
      this.spawnableWaterCreatureList = null;
      this.worldGeneratorBigTree = null;
      this.worldGeneratorSwamp = null;
      this.worldGeneratorTrees = null;
   }

   public boolean canSpawnLightningBolt() {
      return false;
   }

   public boolean getEnableSnow() {
      return false;
   }
}
