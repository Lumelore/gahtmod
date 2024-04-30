package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {

    // 28800 ticks is 24 minutes
    //  9600 ticks is  8 minutes
    //  3600 ticks is  3 minutes
    //  2400 ticks is  2 minutes
    //  1200 ticks is  1  minute

    // Not used, but keeping just in case
    public static final FoodComponent NONE_EFFECT_ITEM = (new FoodComponent.Builder()).alwaysEdible().build();

    // used for prog capsule
    public static final FoodComponent ESTROGEN_ITEM = (new FoodComponent.Builder()).alwaysEdible()
            .statusEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 9600, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 0), 1f).build();

    public static final FoodComponent BIG_HONEY = new FoodComponent.Builder().alwaysEdible().nutrition(12).saturationModifier(0.6f).build();

}
