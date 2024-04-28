package dev.lumelore.gahtmod;

import dev.lumelore.gahtmod.effect.BoyPowerEffect;
import dev.lumelore.gahtmod.effect.GirlPowerEffect;
import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.ModItemGroups;
import dev.lumelore.gahtmod.item.ModItems;
import dev.lumelore.gahtmod.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GAHTMod implements ModInitializer {

	public static final String MOD_ID = "gahtmod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {



		// Registers for items and groups
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		// Registers for effects
		ModEffects.registerModEffects();
		// Registers for sounds
		ModSounds.registerSounds();



	}
}