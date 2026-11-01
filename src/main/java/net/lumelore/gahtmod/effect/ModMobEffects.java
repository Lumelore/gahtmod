package net.lumelore.gahtmod.effect;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModMobEffects {
    // Registers for effects
    // Girl + Boy power effect have same id for attribute modifier because I don't want them to stack
    public static final Holder<MobEffect> GIRL_POWER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, GAHTMod.idOf("girl_power"), new GirlPowerEffect().addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, GAHTMod.idOf("gahtmod.effects.safe_fall_distance"), 2.0, AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> BOY_POWER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, GAHTMod.idOf("boy_power"), new BoyPowerEffect().addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, GAHTMod.idOf("gahtmod.effects.safe_fall_distance"), 2.0, AttributeModifier.Operation.ADD_VALUE));
    public static final Holder<MobEffect> ENBY_POWER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, GAHTMod.idOf("enby_power"), new EnbyPowerEffect().addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, GAHTMod.idOf("gahtmod.effects.half_fall_damage"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void registerModEffects() {
        GAHTMod.LOGGER.info("Registering effects for " + GAHTMod.MOD_ID + "...");
    }

}
