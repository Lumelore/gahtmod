package dev.lumelore.gahtmod.effect;

import dev.lumelore.gahtmod.GAHTMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static dev.lumelore.gahtmod.GAHTMod.MOD_ID;

public class ModEffects {

    // Registers for effects
    // Girl + Boy power effect have same uuid for attribute modifier because I don't want them to stack
    public static final RegistryEntry<StatusEffect> GIRL_POWER = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "girl_power"), new GirlPowerEffect().addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, "822AE5A6-8C1E-ADE7-Df79-CB606A1B66A1", 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> BOY_POWER = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "boy_power"), new BoyPowerEffect().addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,    "822AE5A6-8C1E-ADE7-Df79-CB606A1B66A1", 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> ENBY_POWER = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "enby_power"), new EnbyPowerEffect());

    public static void registerModEffects() {
        GAHTMod.LOGGER.info("Registering effects for " + GAHTMod.MOD_ID + "...");
    }

}
