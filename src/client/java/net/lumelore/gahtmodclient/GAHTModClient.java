package net.lumelore.gahtmodclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.mixin.PlayerEntityAccessor;
import net.minecraft.nbt.CompoundTag;

public class GAHTModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(GAHTPayloads.RequestJumpForBoostPayload.TYPE,
				(payload, context) -> {
					// Collect jumping data, and send it to server
					ClientPlayNetworking.send(
							new GAHTPayloads.SendJumpForBoostPayload(
									((PlayerEntityAccessor) context.player()).getJumping(),
									((PlayerEntityAccessor) context.player()).getNoJumpDelay()
							)
					);
				});

	}


}