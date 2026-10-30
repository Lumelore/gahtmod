package net.lumelore.gahtmod.item;

import net.lumelore.gahtmod.effect.ModMobEffects;
import net.lumelore.gahtmod.item.component.GenderEffects;
import net.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;

public class GAHTComponents {
    // 28800 ticks is 24 minutes
    // 16800 ticks is 14 minutes
    // 14400 ticks is 12 minutes
    //  9600 ticks is  8 minutes
    //  3600 ticks is  3 minutes
    //  2400 ticks is  2 minutes
    //  1200 ticks is  1  minute

    public static final FoodProperties GENERIC_ALWAYS_EDIBLE = new FoodProperties(0, 0, true);

    public static final FoodProperties LARGE_HONEY = new FoodProperties(12, 0.6f, true);

    public static final Consumable GENERIC_DRINK = Consumable.builder()
            .sound(SoundEvents.GENERIC_DRINK)
            .soundAfterConsume(SoundEvents.GENERIC_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .hasConsumeParticles(false)
            .build();

    public static final Consumable THICK_DRINK = Consumable.builder()
            .sound(SoundEvents.HONEY_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .hasConsumeParticles(false)
            .build();

    public static final Consumable REVERSE_DRINK = Consumable.builder()
            .sound(ModSounds.REVERSE_DRINKING)
            .soundAfterConsume(ModSounds.REVERSE_DRINKING)
            .animation(ItemUseAnimation.DRINK)
            .hasConsumeParticles(false)
            .build();

    // ESTROGEN ITEMS

    public static final GenderEffects ESTROGEN_PILL = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 4800, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)))
            .build();

    public static final GenderEffects BOTTLE_OF_ESTROGEN = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 14400, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 0)))
            .build();

    public static final GenderEffects TESTOSTERONE_BLOCKER = GenderEffects.builder()
            .addEffect(new RemoveStatusEffectsConsumeEffect(ModMobEffects.BOY_POWER))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    public static final GenderEffects PROGESTERONE_CAPSULE = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 4800, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)))
            .addEffect(new RemoveStatusEffectsConsumeEffect(ModMobEffects.BOY_POWER))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    // TESTOSTERONE ITEMS

    public static final GenderEffects TESTOSTERONE_GEL_CAPSULE = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.BOY_POWER, 4800, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 200, 0)))
            .build();

    public static final GenderEffects BOTTLE_OF_TESTOSTERONE = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.BOY_POWER, 14400, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 500, 0)))
            .build();

    public static final GenderEffects ESTROGEN_BLOCKER = GenderEffects.builder()
            .addEffect(new RemoveStatusEffectsConsumeEffect(ModMobEffects.GIRL_POWER))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .addEffect(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    // ENBY ITEMS

    public static final GenderEffects ENBY_JUICE = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.ENBY_POWER, 4800, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0)))
            .build();

    public static final GenderEffects LARGE_ENBY_JUICE = GenderEffects.builder()
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.ENBY_POWER, 14400, 0)))
            .addEffect(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 600, 0)))
            .build();


}
