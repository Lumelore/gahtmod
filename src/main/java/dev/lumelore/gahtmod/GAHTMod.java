package dev.lumelore.gahtmod;

import dev.lumelore.gahtmod.item.ModItemGroups;
import dev.lumelore.gahtmod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GAHTMod implements ModInitializer {

	public static final String MOD_ID = "gahtmod";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();


	}
}