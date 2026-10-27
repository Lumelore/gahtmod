package net.lumelore.gahtmod.effect;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static net.lumelore.gahtmod.GAHTMod.MOD_ID;

public class ModEffects {
    // Registers for effects
    // Girl + Boy power effect have same id for attribute modifier because I don't want them to stack
    public static final RegistryEntry<StatusEffect> GIRL_POWER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "girl_power"), new GirlPowerEffect().addAttributeModifier(EntityAttributes.SAFE_FALL_DISTANCE, Identifier.of("gahtmod.effects.safe_fall_boost"), 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> BOY_POWER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "boy_power"), new BoyPowerEffect().addAttributeModifier(EntityAttributes.SAFE_FALL_DISTANCE,    Identifier.of("gahtmod.effects.safe_fall_boost"), 2.0, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> ENBY_POWER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "enby_power"), new EnbyPowerEffect().addAttributeModifier(EntityAttributes.FALL_DAMAGE_MULTIPLIER, Identifier.of("gahtmod.effects.half_fall_damage"), 0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void registerModEffects() {
        GAHTMod.LOGGER.info("Registering effects for " + GAHTMod.MOD_ID + "...");
    }

}
