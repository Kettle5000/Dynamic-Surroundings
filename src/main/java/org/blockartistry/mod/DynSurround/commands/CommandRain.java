package org.blockartistry.mod.DynSurround.commands;

import com.google.common.collect.ImmutableList;
import java.text.DecimalFormat;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.blockartistry.mod.DynSurround.data.BiomeRegistry;
import org.blockartistry.mod.DynSurround.data.BlockRegistry;
import org.blockartistry.mod.DynSurround.data.DimensionEffectData;

public final class CommandRain extends CommandBase {
   private static final List<String> ALIAS = ImmutableList.of("r", "br");
   private static final DecimalFormat FORMATTER = new DecimalFormat("0.0");

   public static String statusOutput(World world, DimensionEffectData data) {
      StringBuilder builder = new StringBuilder();
      float minutes = (float)world.getWorldInfo().getRainTime() / 20.0F / 60.0F;
      builder.append(data.toString());
      builder.append("; isRaining: ").append(Boolean.toString(world.isRaining()));
      builder.append("; isSurface: ").append(Boolean.toString(world.provider.isSurfaceWorld()));
      builder.append("; strength: ").append(FORMATTER.format((double)(world.getRainStrength(1.0F) * 100.0F)));
      builder.append("; timer: ").append(FORMATTER.format((double)minutes)).append(" minutes");
      return builder.toString();
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandName() {
      return "rain";
   }

   public List<String> getCommandAliases() {
      return ALIAS;
   }

   public String getCommandUsage(ICommandSender p_71518_1_) {
      return "/rain <status | reset | reload | 1-100 | <<setmax|setmin> 0-100>";
   }

   public void processCommand(ICommandSender sender, String[] parms) {
      EntityPlayerMP player = getCommandSenderAsPlayer(sender);
      World world = player.worldObj;
      DimensionEffectData data = DimensionEffectData.get(world);
      if (parms.length == 1) {
         if ("status".compareToIgnoreCase(parms[0]) == 0) {
            player.addChatMessage(new ChatComponentText(statusOutput(world, data)));
         } else if ("reset".compareToIgnoreCase(parms[0]) == 0) {
            world.provider.resetRainAndThunder();
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("msg.RainReset")));
         } else if ("reload".compareToIgnoreCase(parms[0]) == 0) {
            BiomeRegistry.initialize();
            BlockRegistry.initialize();
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("msg.BiomeReload")));
         } else {
            double d = parseDoubleBounded(sender, parms[0], (double)0.0F, (double)100.0F) / (double)100.0F;
            data.setRainIntensity((float)d);
            if (d > (double)0.0F && !world.getWorldInfo().isRaining()) {
               world.getWorldInfo().setRaining(true);
               world.getWorldInfo().setRainTime(6000);
            }

            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("msg.RainIntensitySet", new Object[]{FORMATTER.format((double)(data.getRainIntensity() * 100.0F))})));
         }
      } else if (parms.length == 2) {
         if ("setmin".compareToIgnoreCase(parms[0]) == 0) {
            double d = parseDoubleBounded(sender, parms[1], (double)0.0F, (double)100.0F) / (double)100.0F;
            data.setMinRainIntensity((float)d);
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("msg.MinRainIntensitySet", new Object[]{FORMATTER.format((double)(data.getMinRainIntensity() * 100.0F))})));
         } else {
            if ("setmax".compareToIgnoreCase(parms[0]) != 0) {
               throw new CommandException(this.getCommandUsage(sender), new Object[0]);
            }

            double d = parseDoubleBounded(sender, parms[1], (double)0.0F, (double)100.0F) / (double)100.0F;
            data.setMaxRainIntensity((float)d);
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("msg.MaxRainIntensitySet", new Object[]{FORMATTER.format((double)(data.getMaxRainIntensity() * 100.0F))})));
         }
      } else {
         player.addChatMessage(new ChatComponentText(this.getCommandUsage(sender)));
      }

   }
}
