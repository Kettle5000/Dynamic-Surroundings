package org.orecruncher.dsurround.mixins.core;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.orecruncher.dsurround.eventing.ClientState;
import org.orecruncher.dsurround.mixinutils.MixinHelpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinTagCollector {

    @Inject(method = "handleCustomPayload(Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;)V", at = @At("HEAD"))
    private void dsurround_captureServerBrand(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        if (new ResourceLocation("minecraft", "brand").equals(packet.getIdentifier())) {
            var data = new FriendlyByteBuf(packet.getData().duplicate());
            MixinHelpers.SERVER_BRAND = data.readUtf(32767);
        }
    }

    @Inject(method = "handleUpdateTags(Lnet/minecraft/network/protocol/game/ClientboundUpdateTagsPacket;)V", at = @At("TAIL"))
    private void dsurround_tagsUpdated(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
        var listener = (ClientPacketListener) (Object) this;
        if (listener.getConnection().isMemoryConnection())
            ClientState.TAG_SYNC.raise().onTagSync(listener.registryAccess());
    }
}
