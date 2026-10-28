package net.lumelore.gahtmod.item;

import net.lumelore.gahtmod.effect.ModMobEffects;
import net.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.sounds.SoundEvents;
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

    public static final FoodProperties ALWAYS_EDIBLE = new FoodProperties(0, 0, true);

    public static final FoodProperties BIG_HONEY = new FoodProperties(12, 0.6f, true);

    // ESTROGEN ITEMS

    public static final Consumable SMALL_CRUNCHY_ESTROGEN_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 4800, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)))
            .build();

    public static final Consumable LARGE_ESTROGEN_CONSUMABLE = Consumable.builder()
            .sound(SoundEvents.HONEY_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 14400, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 0)))
            .hasConsumeParticles(false)
            .build();

    public static final Consumable TESTOSTERONE_BLOCKER_CONSUMABLE = Consumable.builder()
            .onConsume(new RemoveStatusEffectsConsumeEffect(ModMobEffects.BOY_POWER))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    public static final Consumable PROGESTERONE_CAPSULE_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.GIRL_POWER, 4800, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)))
            .onConsume(new RemoveStatusEffectsConsumeEffect(ModMobEffects.BOY_POWER))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    // TESTOSTERONE ITEMS

    public static final Consumable SMALL_CRUNCHY_TESTOSTERONE_CONSUMABLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.BOY_POWER, 4800, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 200, 0)))
            .build();

    public static final Consumable LARGE_TESTOSTERONE_CONSUMABLE = Consumable.builder()
            .sound(SoundEvents.HONEY_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.BOY_POWER, 14400, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 500, 0)))
            .hasConsumeParticles(false)
            .build();

    public static final Consumable ESTROGEN_BLOCKER_CONSUMABLE = Consumable.builder()
            .onConsume(new RemoveStatusEffectsConsumeEffect(ModMobEffects.GIRL_POWER))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.SLOWNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WEAKNESS))
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.WITHER))
            .build();

    // ENBY ITEMS

    public static final Consumable SMALL_ENBY = Consumable.builder()
            .sound(SoundEvents.HONEY_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.ENBY_POWER, 4800, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0)))
            .hasConsumeParticles(false)
            .build();

    public static final Consumable LARGE_ENBY = Consumable.builder()
            .sound(SoundEvents.HONEY_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModMobEffects.ENBY_POWER, 14400, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE, 600, 0)))
            .hasConsumeParticles(false)
            .build();

    public static final Consumable REVERSE_DRINK = Consumable.builder()
            .sound(ModSounds.REVERSE_DRINKING)
            .soundAfterConsume(ModSounds.REVERSE_DRINKING)
            .animation(ItemUseAnimation.DRINK)
            .hasConsumeParticles(false)
            .build();

    public static final Consumable NORMAL_DRINK = Consumable.builder()
            .sound(SoundEvents.GENERIC_DRINK)
            .soundAfterConsume(SoundEvents.GENERIC_DRINK)
            .animation(ItemUseAnimation.DRINK)
            .hasConsumeParticles(false)
            .build();

}
