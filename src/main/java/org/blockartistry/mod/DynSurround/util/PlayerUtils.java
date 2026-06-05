package org.blockartistry.mod.DynSurround.util;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.regex.Pattern;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;
import org.blockartistry.mod.DynSurround.data.DimensionRegistry;

public final class PlayerUtils {
   private static final int INSIDE_Y_ADJUST = 3;
   private static final Pattern REGEX_DEEP_OCEAN = Pattern.compile("(?i).*deep.*ocean.*|.*abyss.*");
   private static final Pattern REGEX_OCEAN = Pattern.compile("(?i)(?!.*deep.*)(.*ocean.*|.*kelp.*|.*coral.*)");
   private static final Pattern REGEX_RIVER = Pattern.compile("(?i).*river.*");
   private static final int RANGE = 3;
   private static final int AREA = 49;

   private PlayerUtils() {
   }

   public static BiomeGenBase getPlayerBiome(EntityPlayer player, boolean getTrue) {
      int theX = MathHelper.floor_double(player.posX);
      int theZ = MathHelper.floor_double(player.posZ);
      BiomeGenBase biome = player.worldObj.getBiomeGenForCoords(theX, theZ);
      if (!getTrue) {
         if (player.isInsideOfMaterial(Material.water)) {
            if (REGEX_RIVER.matcher(biome.biomeName).matches()) {
               biome = BiomeRegistry.UNDERRIVER;
            } else if (REGEX_OCEAN.matcher(biome.biomeName).matches()) {
               biome = BiomeRegistry.UNDEROCEAN;
            } else if (REGEX_DEEP_OCEAN.matcher(biome.biomeName).matches()) {
               biome = BiomeRegistry.UNDERDEEPOCEAN;
            } else {
               biome = BiomeRegistry.UNDERWATER;
            }
         } else {
            DimensionRegistry info = DimensionRegistry.getData(player.getEntityWorld());
            int theY = MathHelper.floor_double(player.posY);
            if (theY + 3 < info.getSeaLevel()) {
               biome = BiomeRegistry.UNDERGROUND;
            } else if (theY >= info.getSpaceHeight()) {
               biome = BiomeRegistry.OUTERSPACE;
            } else if (theY >= info.getCloudHeight()) {
               biome = BiomeRegistry.CLOUDS;
            }
         }
      }

      return biome;
   }

   private static int getPlayerDimension(EntityPlayer player) {
      return player != null && player.worldObj != null ? player.getEntityWorld().provider.dimensionId : -256;
   }

   private static float ceilingCoverageRatio(EntityPlayer entity) {
      World world = entity.getEntityWorld();
      int targetY = MathHelper.floor_double(entity.posY);
      int baseX = MathHelper.floor_double(entity.posX);
      int baseZ = MathHelper.floor_double(entity.posZ);
      int seeSky = 0;

      for(int x = -3; x <= 3; ++x) {
         for(int z = -3; z <= 3; ++z) {
            int y = world.getTopSolidOrLiquidBlock(baseX + x, baseZ + z);
            if (y - targetY < 2) {
               ++seeSky;
            }
         }
      }

      return 1.0F - (float)seeSky / 49.0F;
   }

   public static boolean isReallyInside(EntityPlayer entity) {
      return ceilingCoverageRatio(entity) > 0.42F;
   }

   @SideOnly(Side.CLIENT)
   public static int getClientPlayerDimension() {
      return getPlayerDimension(FMLClientHandler.instance().getClient().thePlayer);
   }
}
