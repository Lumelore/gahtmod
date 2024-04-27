package dev.lumelore.gahtmod.effect;

import dev.lumelore.gahtmod.GAHTMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.lumelore.gahtmod.GAHTMod.MOD_ID;

public class ModEffects {

    // Registers for effects
    public static final StatusEffect GIRL_POWER = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "girl_power"), new GirlPowerEffect());
    public static final StatusEffect BOY_POWER = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "boy_power"), new BoyPowerEffect());

    public static void registerModEffects() {
        GAHTMod.LOGGER.info("Registering effects for " + GAHTMod.MOD_ID + "...");
    }

}