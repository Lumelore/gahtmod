package net.lumelore.gahtmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lumelore.gahtmod.effect.ModEffects;
import net.lumelore.gahtmod.item.ModItemGroups;
import net.lumelore.gahtmod.item.ModItems;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.gahtmod.recipe.ModRecipes;
import net.lumelore.gahtmod.sound.ModSounds;
import net.lumelore.gahtmod.util.EffectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GAHTMod implements ModInitializer {

	public static final String MOD_ID = "gahtmod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Registers
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEffects.registerModEffects();
		ModSounds.registerSounds();
        ModRecipes.registerRecipes();
		GAHTPayloads.registerPayloads();

		// Receiver Registers
		ServerPlayNetworking.registerGlobalReceiver(GAHTPayloads.SendJumpForBoostPayload.ID, (sendJumpForBoost, context) -> {
            EffectUtil.dash(context.player(), sendJumpForBoost);
		});

	}

}