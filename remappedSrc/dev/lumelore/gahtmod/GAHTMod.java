package net.lumelore.gahtmod;

import net.lumelore.gahtmod.effect.ModEffects;
import net.lumelore.gahtmod.item.ModItemGroups;
import net.lumelore.gahtmod.item.ModItems;
import net.lumelore.gahtmod.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

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