package org.blockartistry.mod.DynSurround.client.footsteps.game.system;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.blockartistry.mod.DynSurround.ModLog;
import org.blockartistry.mod.DynSurround.client.footsteps.mcpackage.interfaces.IBlockMap;
import org.blockartistry.mod.DynSurround.compat.MCHelper;

@SideOnly(Side.CLIENT)
public final class ForgeDictionary {
   private static final String[] oreBlocks = new String[]{"oreIron", "oreGold", "oreCopper", "oreTin", "oreSilver", "oreLead", "oreNickle", "orePlatinum", "oreManaInfused", "oreElectrum", "oreInvar", "oreBronze", "oreSignalum", "oreEnderium", "oreLapis", "oreDiamond", "oreEmerald", "oreRedstone", "oreQuartz", "oreCoal", "oreNickel", "oreChimerite", "oreBlueTopaz", "oreMoonstone", "oreVinteum", "oreTitanium", "oreOsmium", "denseoreLapis", "oreUranium", "denseoreIron", "denseoreGold", "denseoreRedstone", "denseoreDiamond", "denseoreEmerald", "denseoreCoal", "denseoreCopper", "denseoreTin", "denseoreSilver", "denseoreLead", "denseoreNickel", "denseorePlatinum", "denseoreMithril", "oreRuby", "oreSapphire", "oreEmery", "oreAluminum", "oreJade", "oreApatite", "oreSalt", "oreZinc", "orePeridot", "oreSilicon", "oreMoldavite", "oreBloodstone", "oreCinnabar", "oreYellorite", "oreTemporal", "oreAmethyst", "oreAmber", "oreMalachite", "oreTanzanite", "oreTritanium", "oreTungsten", "oreTelsalite", "oreCheese", "denseorePeridot", "denseoreZinc", "denseoreRuby", "denseoreSapphire", "denseoreAmethyst", "denseoreTungsten", "oreHeeEndium", "oreStarSteel", "oreColdIron", "oreAdamantine", "oreMercury", "oreFossil"};
   private static final String[] metalBlocks = new String[]{"blockIron", "blockGold", "blockCopper", "blockTin", "blockSilver", "blockLead", "blockNickle", "blockPlatinum", "blockMithril", "blockElectrum", "blockInvar", "blockBronze", "blockSignalum", "blockLumium", "blockEnderium", "blockSteel", "blockNickel", "blockTitanium", "blockOsmium", "blockUranium", "blockBrass", "blockZinc", "blockConstantan", "slabConstantan", "slabCopper", "slabSilver", "slabElectrum", "slabNickel", "slabAluminum", "blockAluminum", "blockYellorium", "blockCyanite", "blockBlutonium", "blockLudicrite", "blockTemporal", "blockTritanium", "blockTungsten", "blockHeeEndium", "blockPsiMetal", "blockStarsteel", "blockAdamantine", "blockColdiron", "blockAquarium", "plateAdamantine", "plateBrass", "plateAquarium", "plateBronze", "plateStarsteel", "plateCopper", "plateGold", "plateElectrum", "plateGold", "plateInvar", "plateIron", "plateLead", "plateMithril", "plateNickel", "plateTin", "plateSteel", "plateSilver", "plateZinc", "bars", "trapdoorAdamantine", "trapdoorMithril", "trapdoorSilver", "trapdoorAquarium", "trapdoorInvar", "trapdoorBrass", "trapdoorBronze", "trapdoorColdiron", "trapdoorElectrum", "trapdoorLead", "trapdoorNickel", "trapdoorStarsteel", "trapdoorTin", "trapdoorSteel", "doorAdamantine", "doorAquarium", "doorBrass", "doorBronze", "doorColdiron", "doorCopper", "doorElectrum", "doorInvar", "doorLead", "doorMithril", "doorNickel", "doorSilver", "doorStarsteel", "doorSteel", "doorTin", "blockWroughtIron"};
   private static final String[] woodBlocks = new String[]{"logWood", "planksWood", "slabWood", "stairWood", "plankBamboo", "slabBamboo", "stairBamboo", "craftingTableWood", "plankWood"};
   private static final String[] saplings = new String[]{"treeSaplings", "saplingTree"};
   private static final String[] glassBlocks = new String[]{"blockGlass"};
   private static final String[] leafBlocks = new String[]{"treeLeaves", "leavesTree", "treeBambooLeaves"};
   private static final String[] stoneBlocks = new String[]{"stone", "cobblestone", "blockFuelCoke", "concrete", "blockCoal", "andesite", "blockAndesite", "stoneAndesite", "stoneDiorite", "diorite", "blockDiorite", "blockGranite", "stoneGranite", "blockCharcoal"};
   private static final String[] sandstoneBlocks = new String[]{"sandstone", "blockPrismarine", "limestone", "stoneLimestone", "blockLimestone"};
   private static final String[] sandBlocks = new String[]{"sand", "blockSalt", "blockPsiDust"};
   private static final String[] woodChests = new String[]{"chestWood", "chestTrapped"};
   private static final String[] rugBlocks = new String[]{"wool", "blockClothRock", "materialBedding"};
   private static final String[] fenceBlocks = new String[]{"fenceWood", "fenceGateWood"};
   private static final String[] mudBlocks = new String[]{"blockSlime", "blockCheese"};
   private static final String[] obsidianBlocks = new String[]{"oreSunstone", "blockGraphite", "basalt", "stoneBasalt", "blockBasalt", "blockBloodstone"};
   private static final String[] compositeBlocks = new String[]{"blockDiamond", "blockEmerald", "blockPeridot", "blockRuby", "blockSapphire", "blockVinteum", "blockChimerite", "blockBlueTopaz", "blockMoonstone", "blockSunstone", "blockAmethyst", "blockMoldavite", "blockAmber", "blockTanzanite", "blockMalachite", "blockChalcedony", "blockTeslaite", "blockJade", "blockPsiGem"};
   private static final String[] marbleBlocks = new String[]{"blockQuartz", "marble", "stoneMarble", "blockMarble"};
   private static final Map<String, String[]> dictionaryMaps = new HashMap();

   private ForgeDictionary() {
   }

   public static void dumpOreNames() {
      ModLog.debug("**** FORGE ORE DICTIONARY NAMES ****");

      for(String oreName : OreDictionary.getOreNames()) {
         ModLog.debug(oreName);
      }

      ModLog.debug("************************************");
   }

   public static void initialize(IBlockMap blockMap) {
      for(Map.Entry<String, String[]> entry : dictionaryMaps.entrySet()) {
         String value = (String)entry.getKey();

         for(String oreName : (String[])entry.getValue()) {
            for(ItemStack stack : OreDictionary.getOres(oreName, false)) {
               Block block = Block.getBlockFromItem(stack.getItem());
               if (block != null) {
                  String blockName = MCHelper.nameOf(block);
                  if (stack.getHasSubtypes() && stack.getItemDamage() != 32767) {
                     blockName = blockName + "^" + stack.getItemDamage();
                  }

                  blockMap.register(blockName, value);
               }
            }
         }
      }

   }

   static {
      dictionaryMaps.put("ore", oreBlocks);
      dictionaryMaps.put("hardmetal", metalBlocks);
      dictionaryMaps.put("wood", woodBlocks);
      dictionaryMaps.put("glass", glassBlocks);
      dictionaryMaps.put("#sapling", saplings);
      dictionaryMaps.put("leaves", leafBlocks);
      dictionaryMaps.put("stone", stoneBlocks);
      dictionaryMaps.put("sandstone", sandstoneBlocks);
      dictionaryMaps.put("sand", sandBlocks);
      dictionaryMaps.put("squeakywood", woodChests);
      dictionaryMaps.put("rug", rugBlocks);
      dictionaryMaps.put("#fence", fenceBlocks);
      dictionaryMaps.put("mud", mudBlocks);
      dictionaryMaps.put("obsidian", obsidianBlocks);
      dictionaryMaps.put("composite", compositeBlocks);
      dictionaryMaps.put("marble", marbleBlocks);
   }
}
