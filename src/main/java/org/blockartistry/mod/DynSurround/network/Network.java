package org.blockartistry.mod.DynSurround.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import org.blockartistry.mod.DynSurround.client.DamageEffectHandler;
import org.blockartistry.mod.DynSurround.data.AuroraData;

public final class Network {
   private static int discriminator = 0;
   public static SimpleNetworkWrapper network;

   private Network() {
   }

   public static void initialize() {
      network = NetworkRegistry.INSTANCE.newSimpleChannel("dsurround");
      network.registerMessage(PacketRainIntensity.class, PacketRainIntensity.class, ++discriminator, Side.CLIENT);
      network.registerMessage(PacketAurora.class, PacketAurora.class, ++discriminator, Side.CLIENT);
      network.registerMessage(PacketHealthChange.class, PacketHealthChange.class, ++discriminator, Side.CLIENT);
   }

   public static void sendRainIntensity(float intensity, int dimension) {
      network.sendToDimension(new PacketRainIntensity(intensity, dimension), dimension);
   }

   public static void sendAurora(AuroraData data, int dimension) {
      network.sendToDimension(new PacketAurora(data), dimension);
   }

   public static void sendHealthUpdate(DamageEffectHandler.HealthData data, int dimension) {
      network.sendToDimension(new PacketHealthChange(data), dimension);
   }
}
