package org.blockartistry.mod.DynSurround;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.IChatComponent.Serializer;
import org.apache.commons.lang3.StringUtils;

public final class VersionCheck implements Runnable {
   private static final String REMOTE_VERSION_FILE = "https://raw.githubusercontent.com/OreCruncher/BetterRain/master/versions.txt";
   private static final int VERSION_CHECK_RETRIES = 3;
   private static final int VERSION_CHECK_INTERVAL = 10000;
   public static final SoftwareVersion modVersion = new SoftwareVersion(Module.VERSION);
   public static SoftwareVersion currentVersion = new SoftwareVersion();
   public static UpdateStatus status;
   private static final String mcVersion;
   private static final String CURSE_PROJECT_NAME = "238891";
   private static final String MOD_NAME_TEMPLATE = "DynamicSurroundings-1.7.10-[].jar";

   private VersionCheck() {
   }

   public static void register() {
      if (Loader.isModLoaded("VersionChecker")) {
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.setString("curseProjectName", "238891");
         nbt.setString("curseFilenameParser", "DynamicSurroundings-1.7.10-[].jar");
         FMLInterModComms.sendRuntimeMessage("dsurround", "VersionChecker", "addVersionCheck", nbt);
      }

      if (ModOptions.enableVersionChecking) {
         VersionCheck test = new VersionCheck();
         FMLCommonHandler.instance().bus().register(test);
         (new Thread(test)).start();
      }

   }

   @SubscribeEvent
   public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
      if (event.player instanceof EntityPlayer && status == VersionCheck.UpdateStatus.OUTDATED) {
         String msg = StatCollector.translateToLocalFormatted("msg.NewVersionAvailable.dsurround", new Object[]{"Dynamic Surroundings", currentVersion, "238891"});
         IChatComponent component = Serializer.func_150699_a(msg);
         event.player.addChatMessage(component);
      }

   }

   private static void versionCheck() {
      try {
         String location = "https://raw.githubusercontent.com/OreCruncher/BetterRain/master/versions.txt";

         HttpURLConnection conn;
         for(conn = null; location != null && !location.isEmpty(); location = conn.getHeaderField("Coordinates")) {
            URL url = new URL(location);
            if (conn != null) {
               conn.disconnect();
            }

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; ru; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
            conn.connect();
         }

         if (conn == null) {
            throw new NullPointerException();
         }

         BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

         String line;
         while((line = reader.readLine()) != null) {
            String[] tokens = line.split(":");
            if (mcVersion.matches(tokens[0])) {
               currentVersion = new SoftwareVersion(tokens[1]);
               break;
            }
         }

         status = VersionCheck.UpdateStatus.CURRENT;
         if (modVersion.compareTo(currentVersion) < 0) {
            status = VersionCheck.UpdateStatus.OUTDATED;
         }

         conn.disconnect();
         reader.close();
      } catch (Exception e) {
         ModLog.warn("Unable to read remote version data", e);
         status = VersionCheck.UpdateStatus.COMM_ERROR;
      }

   }

   public void run() {
      int count = 0;
      ModLog.info("Checking for newer mod version");

      try {
         do {
            if (count > 0) {
               ModLog.info("Awaiting attempt %d", count);
               Thread.sleep(10000L);
            }

            versionCheck();
            ++count;
         } while(count < 3 && status == VersionCheck.UpdateStatus.COMM_ERROR);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      switch (status) {
         case COMM_ERROR:
            ModLog.warn("Version check failed");
            break;
         case CURRENT:
            ModLog.info("Dynamic Surroundings version [%s] is the same or newer than the current version [%s]", modVersion, currentVersion);
            break;
         case OUTDATED:
            ModLog.warn("Using outdated version [" + modVersion + "] for Minecraft " + mcVersion + ". Consider updating to " + currentVersion + ".");
            break;
         case UNKNOWN:
            ModLog.warn("Unknown version check status!");
      }

   }

   static {
      status = VersionCheck.UpdateStatus.UNKNOWN;
      mcVersion = Loader.instance().getMinecraftModContainer().getVersion();
   }

   public static enum UpdateStatus {
      UNKNOWN,
      CURRENT,
      OUTDATED,
      COMM_ERROR;
   }

   public static class SoftwareVersion implements Comparable<SoftwareVersion> {
      public final int major;
      public final int minor;
      public final int revision;
      public final int patch;
      public final boolean isAlpha;
      public final boolean isBeta;

      public SoftwareVersion() {
         this.major = 0;
         this.minor = 0;
         this.revision = 0;
         this.patch = 0;
         this.isAlpha = false;
         this.isBeta = false;
      }

      public SoftwareVersion(String versionString) {
         if (versionString.charAt(0) == '@') {
            this.major = 0;
            this.minor = 0;
            this.revision = 0;
            this.patch = 0;
            this.isAlpha = false;
            this.isBeta = false;
         } else {
            assert versionString != null;

            assert versionString.length() > 0;

            this.isAlpha = StringUtils.containsIgnoreCase(versionString, "ALPHA");
            if (this.isAlpha) {
               versionString = StringUtils.remove(versionString, "ALPHA");
            }

            this.isBeta = StringUtils.containsIgnoreCase(versionString, "BETA");
            if (this.isBeta) {
               versionString = StringUtils.remove(versionString, "BETA");
            }

            int dashIdx = versionString.indexOf('-');
            if (dashIdx >= 0) {
               versionString = versionString.substring(dashIdx + 1);
            }

            String[] parts = StringUtils.split(versionString, ".");
            int numComponents = parts.length;

            assert numComponents >= 3;

            this.major = Integer.parseInt(parts[0]);
            this.minor = Integer.parseInt(parts[1]);
            this.revision = Integer.parseInt(parts[2]);
            if (numComponents == 4) {
               this.patch = Integer.parseInt(parts[3]);
            } else {
               this.patch = 0;
            }

         }
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(this.major).append('.').append(this.minor).append('.').append(this.revision);
         if (this.patch != 0) {
            builder.append('.').append(this.patch);
         }

         if (this.isAlpha) {
            builder.append("ALPHA");
         }

         if (this.isBeta) {
            builder.append("BETA");
         }

         return builder.toString();
      }

      public int compareTo(SoftwareVersion obj) {
         if (this.major != obj.major) {
            return this.major - obj.major;
         } else if (this.minor != obj.minor) {
            return this.minor - obj.minor;
         } else {
            return this.revision != obj.revision ? this.revision - obj.revision : this.patch - obj.patch;
         }
      }
   }
}
