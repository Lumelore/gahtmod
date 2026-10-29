package net.lumelore.gahtmod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class GAHTPayloads {

    // Used to ask the client if a player is jumping. Byte is not used
    public record RequestJumpForBoostPayload(byte discarded) implements CustomPacketPayload {

        public static final Identifier REQUEST_JUMP_FOR_BOOST = GAHTMod.idOf("request_jump_for_boost");

        public static final CustomPacketPayload.Type<RequestJumpForBoostPayload> TYPE = new CustomPacketPayload.Type<>(REQUEST_JUMP_FOR_BOOST);

        public static final StreamCodec<RegistryFriendlyByteBuf, RequestJumpForBoostPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.BYTE,
                RequestJumpForBoostPayload::discarded,
                RequestJumpForBoostPayload::new
                );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    // Sent back by the client to tell the server if a player is jumping
    public record SendJumpForBoostPayload(boolean isJumping, int noJumpDelay) implements CustomPacketPayload {

        public static final Identifier SEND_JUMP_FOR_BOOST = GAHTMod.idOf("send_jump_for_boost");

        public static CustomPacketPayload.Type<SendJumpForBoostPayload> TYPE = new CustomPacketPayload.Type<>(SEND_JUMP_FOR_BOOST);

        // TODO
        public static final StreamCodec<RegistryFriendlyByteBuf, SendJumpForBoostPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                SendJumpForBoostPayload::isJumping,
                ByteBufCodecs.INT,
                SendJumpForBoostPayload::noJumpDelay,
                SendJumpForBoostPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void registerPayloads() {
        GAHTMod.LOGGER.info("Registering payloads...");
        PayloadTypeRegistry.clientboundPlay().register(RequestJumpForBoostPayload.TYPE, RequestJumpForBoostPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(SendJumpForBoostPayload.TYPE, SendJumpForBoostPayload.CODEC);
    }
}
