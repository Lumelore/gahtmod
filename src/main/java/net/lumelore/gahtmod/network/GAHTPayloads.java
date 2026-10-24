package net.lumelore.gahtmod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class GAHTPayloads {

    public static final Identifier REQUEST_JUMP_FOR_BOOST = Identifier.of(GAHTMod.MOD_ID, "request_jump_for_boost");
    public static final Identifier SEND_JUMP_FOR_BOOST = Identifier.of(GAHTMod.MOD_ID, "send_jump_for_boost");
    public static final Identifier SPAWN_PARTICLE = Identifier.of(GAHTMod.MOD_ID, "spawn_particle");

    // Used to ask the client if a player is jumping. Byte is not used
    public record RequestJumpForBoostPayload(byte discarded) implements CustomPayload {

        public static CustomPayload.Id<RequestJumpForBoostPayload> ID = new CustomPayload.Id<>(REQUEST_JUMP_FOR_BOOST);

        public static final PacketCodec<RegistryByteBuf, RequestJumpForBoostPayload> CODEC = PacketCodec
                .tuple(PacketCodecs.BYTE, RequestJumpForBoostPayload::discarded, RequestJumpForBoostPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    // Sent back by the client to tell the server if a player is jumping
    public record SendJumpForBoostPayload(NbtCompound jumpingData) implements CustomPayload {

        public static CustomPayload.Id<SendJumpForBoostPayload> ID = new CustomPayload.Id<>(SEND_JUMP_FOR_BOOST);

        public static final PacketCodec<RegistryByteBuf, SendJumpForBoostPayload> CODEC = PacketCodec
                .tuple(PacketCodecs.NBT_COMPOUND, SendJumpForBoostPayload::jumpingData, SendJumpForBoostPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public static void registerPayloads() {
        GAHTMod.LOGGER.info("Registering payloads...");
        PayloadTypeRegistry.playS2C().register(GAHTPayloads.RequestJumpForBoostPayload.ID, GAHTPayloads.RequestJumpForBoostPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SendJumpForBoostPayload.ID, SendJumpForBoostPayload.CODEC);
    }
}
