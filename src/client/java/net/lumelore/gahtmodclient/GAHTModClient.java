package net.lumelore.gahtmodclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.mixin.PlayerEntityAccessor;
import net.minecraft.nbt.NbtCompound;

public class GAHTModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(GAHTPayloads.RequestJumpForBoostPayload.ID,
				(payload, context) -> {
					// Collect jumping data, pack it into an NBT, and send it to server
					NbtCompound jumpData = new NbtCompound();
					jumpData.putBoolean("isJumping", ((PlayerEntityAccessor) context.player()).getJumping());
					jumpData.putInt("jumpCooldown", ((PlayerEntityAccessor) context.player()).getJumpingCooldown());
					ClientPlayNetworking.send(new GAHTPayloads.SendJumpForBoostPayload(jumpData));
				});

	}


}