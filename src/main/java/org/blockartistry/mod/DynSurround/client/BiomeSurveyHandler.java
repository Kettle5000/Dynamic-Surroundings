package org.blockartistry.mod.DynSurround.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.blockartistry.mod.DynSurround.data.FakeBiome;

@SideOnly(Side.CLIENT)
public final class BiomeSurveyHandler implements IClientEffectHandler {
   private static final int BIOME_SURVEY_RANGE = 6;
   private static int area;
   private static final TObjectIntHashMap<BiomeGenBase> weights = new TObjectIntHashMap();
   private static BiomeGenBase lastPlayerBiome = null;
   private static int lastDimension = 0;
   private static int lastPlayerX = 0;
   private static int lastPlayerY = 0;
   private static int lastPlayerZ = 0;

   public static int getArea() {
      return area;
   }

   public static TObjectIntHashMap<BiomeGenBase> getBiomes() {
      return weights;
   }

   private static void doSurvey(EntityPlayer player, int range) {
      area = 0;
      weights.clear();
      if (EnvironStateHandler.EnvironState.getPlayerBiome() instanceof FakeBiome) {
         area = 1;
         weights.put(EnvironStateHandler.EnvironState.getPlayerBiome(), 1);
      } else {
         int x = MathHelper.floor_double(player.posX);
         int z = MathHelper.floor_double(player.posZ);

         for(int dX = -range; dX <= range; ++dX) {
            for(int dZ = -range; dZ <= range; ++dZ) {
               ++area;
               BiomeGenBase biome = player.worldObj.getBiomeGenForCoords(x + dX, z + dZ);
               weights.adjustOrPutValue(biome, 1, 1);
            }
         }
      }

   }

   public void process(World world, EntityPlayer player) {
      int playerX = MathHelper.floor_double(player.posX);
      int playerY = MathHelper.floor_double(player.posY);
      int playerZ = MathHelper.floor_double(player.posZ);
      if (lastDimension != EnvironStateHandler.EnvironState.getDimensionId() || playerX != lastPlayerX || playerY != lastPlayerY || playerZ != lastPlayerZ || lastPlayerBiome != EnvironStateHandler.EnvironState.getPlayerBiome()) {
         lastPlayerBiome = EnvironStateHandler.EnvironState.getPlayerBiome();
         lastDimension = EnvironStateHandler.EnvironState.getDimensionId();
         lastPlayerX = playerX;
         lastPlayerY = playerY;
         lastPlayerZ = playerZ;
         doSurvey(player, 6);
      }

   }

   public boolean hasEvents() {
      return false;
   }
}
