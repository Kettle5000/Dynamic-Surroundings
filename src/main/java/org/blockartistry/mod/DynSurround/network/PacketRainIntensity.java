package org.blockartistry.mod.DynSurround.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import org.blockartistry.mod.DynSurround.client.weather.Weather;
import org.blockartistry.mod.DynSurround.util.PlayerUtils;

public final class PacketRainIntensity implements IMessage, IMessageHandler<PacketRainIntensity, IMessage> {
   private float intensity;
   private int dimension;

   public PacketRainIntensity() {
   }

   public PacketRainIntensity(float intensity, int dimension) {
      this.intensity = intensity;
      this.dimension = dimension;
   }

   public void fromBytes(ByteBuf buf) {
      this.intensity = buf.readFloat();
      this.dimension = buf.readInt();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeFloat(this.intensity);
      buf.writeInt(this.dimension);
   }

   public IMessage onMessage(PacketRainIntensity message, MessageContext ctx) {
      if (message.dimension == PlayerUtils.getClientPlayerDimension()) {
         Weather.setIntensity(message.intensity);
      }

      return null;
   }
}
