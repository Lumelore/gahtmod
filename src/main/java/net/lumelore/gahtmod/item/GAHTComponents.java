package net.lumelore.gahtmod.item;

import net.lumelore.gahtmod.effect.ModEffects;
import net.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;

public class GAHTComponents {
    // 28800 ticks is 24 minutes
    // 16800 ticks is 14 minutes
    // 14400 ticks is 12 minutes
    //  9600 ticks is  8 minutes
    //  3600 ticks is  3 minutes
    //  2400 ticks is  2 minutes
    //  1200 ticks is  1  minute

    public static final FoodComponent ALWAYS_EDIBLE = (new FoodComponent.Builder()).alwaysEdible().build();

    public static final FoodComponent BIG_HONEY = new FoodComponent.Builder().alwaysEdible().nutrition(12).saturationModifier(0.6f).build();

    // ESTROGEN ITEMS
    
    public static final ConsumableComponent SMALL_CRUNCHY_ESTROGEN_CONSUMABLE = ConsumableComponent.builder()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 4800, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0)))
            .build();

    public static final ConsumableComponent LARGE_ESTROGEN_CONSUMABLE = ConsumableComponent.builder()
            .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .finishSound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .useAction(UseAction.DRINK)
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 14400, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 160, 1)))
            .consumeParticles(false)
            .build();

    public static final ConsumableComponent TESTOSTERONE_BLOCKER_CONSUMABLE = ConsumableComponent.builder()
            .consumeEffect(new RemoveEffectsConsumeEffect(ModEffects.BOY_POWER))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.SLOWNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WEAKNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WITHER))
            .build();

    public static final ConsumableComponent PROGESTERONE_CAPSULE_CONSUMABLE = ConsumableComponent.builder()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 4800, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0)))
            .consumeEffect(new RemoveEffectsConsumeEffect(ModEffects.BOY_POWER))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.SLOWNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WEAKNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WITHER))
            .build();

    // TESTOSTERONE ITEMS

    public static final ConsumableComponent SMALL_CRUNCHY_TESTOSTERONE_CONSUMABLE = ConsumableComponent.builder()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.BOY_POWER, 4800, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 0)))
            .build();

    public static final ConsumableComponent LARGE_TESTOSTERONE_CONSUMABLE = ConsumableComponent.builder()
            .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .finishSound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .useAction(UseAction.DRINK)
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.BOY_POWER, 14400, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 500, 0)))
            .consumeParticles(false)
            .build();

    public static final ConsumableComponent ESTROGEN_BLOCKER_CONSUMABLE = ConsumableComponent.builder()
            .consumeEffect(new RemoveEffectsConsumeEffect(ModEffects.GIRL_POWER))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.SLOWNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WEAKNESS))
            .consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.WITHER))
            .build();

    // ENBY ITEMS

    public static final ConsumableComponent SMALL_ENBY = ConsumableComponent.builder()
            .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .finishSound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .useAction(UseAction.DRINK)
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.ENBY_POWER, 4800, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0)))
            .consumeParticles(false)
            .build();

    public static final ConsumableComponent LARGE_ENBY = ConsumableComponent.builder()
            .sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .finishSound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK)
            .useAction(UseAction.DRINK)
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(ModEffects.ENBY_POWER, 14400, 0)))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 0)))
            .consumeParticles(false)
            .build();

    public static final ConsumableComponent REVERSE_DRINK = ConsumableComponent.builder()
            .sound(ModSounds.REVERSE_DRINKING)
            .finishSound(ModSounds.REVERSE_DRINKING)
            .useAction(UseAction.DRINK)
            .consumeParticles(false)
            .build();

    public static final ConsumableComponent NORMAL_DRINK = ConsumableComponent.builder()
            .sound(SoundEvents.ENTITY_GENERIC_DRINK)
            .finishSound(SoundEvents.ENTITY_GENERIC_DRINK)
            .useAction(UseAction.DRINK)
            .consumeParticles(false)
            .build();

}
