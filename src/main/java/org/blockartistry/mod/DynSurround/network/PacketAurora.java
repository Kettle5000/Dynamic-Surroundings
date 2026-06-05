package org.blockartistry.mod.DynSurround.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import org.blockartistry.mod.DynSurround.client.AuroraEffectHandler;
import org.blockartistry.mod.DynSurround.data.AuroraData;

public final class PacketAurora implements IMessage, IMessageHandler<PacketAurora, IMessage> {
   private int dimension;
   private long seed;
   private int posX;
   private int posZ;
   private int colorSet;
   private int preset;

   public PacketAurora() {
   }

   public PacketAurora(AuroraData data) {
      this(data.dimensionId, data.seed, data.posX, data.posZ, data.colorSet, data.preset);
   }

   public PacketAurora(int dimensionId, long seed, int posX, int posZ, int colorSet, int preset) {
      this.dimension = dimensionId;
      this.seed = seed;
      this.posX = posX;
      this.posZ = posZ;
      this.colorSet = colorSet;
      this.preset = preset;
   }

   public void fromBytes(ByteBuf buf) {
      this.dimension = buf.readInt();
      this.seed = buf.readLong();
      this.posX = buf.readInt();
      this.posZ = buf.readInt();
      this.colorSet = buf.readByte();
      this.preset = buf.readByte();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.dimension);
      buf.writeLong(this.seed);
      buf.writeInt(this.posX);
      buf.writeInt(this.posZ);
      buf.writeByte(this.colorSet);
      buf.writeByte(this.preset);
   }

   public IMessage onMessage(PacketAurora message, MessageContext ctx) {
      AuroraEffectHandler.addAurora(new AuroraData(message.dimension, message.posX, message.posZ, message.seed, message.colorSet, message.preset));
      return null;
   }
}
