package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    // 28800 ticks is 24 minutes
    //  9600 ticks is  8 minutes
    //  3600 ticks is  3 minutes
    //  2400 ticks is  2 minutes
    //  1200 ticks is  1  minute

    // Used for items that have special effects, such as the blockers
    public static final FoodComponent NONE_EFFECT_ITEM = new FoodComponent.Builder().alwaysEdible().build();

    /* ============== *
     * Estrogen Items *
     * ============== */

    // Girl Power I for 8 minutes and Regeneration I for 1 minute
    public static final FoodComponent ESTROGEN_ITEM = new FoodComponent.Builder().alwaysEdible()
            .statusEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 9600, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 0), 1f).build();
    // Girl Power I for 24 minutes and Regeneration II for 2 minutes
    public static final FoodComponent STRONG_ESTROGEN_ITEM = new FoodComponent.Builder().alwaysEdible()
            .statusEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, 28800, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2400, 1), 1f).build();

    /* ================== *
     * Testosterone Items *
     * ================== */

    // Boy Power I for 8 minutes and Strength I for 1 minute
    public static final FoodComponent TESTOSTERONE_ITEM = new FoodComponent.Builder().alwaysEdible()
            .statusEffect(new StatusEffectInstance(ModEffects.BOY_POWER, 9600, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1200, 0), 1f).build();
    // Boy Power I for 24 minutes and Strength II for 2 minutes
    public static final FoodComponent STRONG_TESTOSTERONE_ITEM = new FoodComponent.Builder().alwaysEdible()
            .statusEffect(new StatusEffectInstance(ModEffects.BOY_POWER, 28800, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 1), 1f).build();

    /* ========== *
     * Enby Items *
     * ========== */

    // Enby Power I for 8 minutes and Resistance I for 1 minute -- Also fills players hunger same as honey bottle item
    public static final FoodComponent ENBY_ITEM = new FoodComponent.Builder().alwaysEdible().hunger(6).saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(ModEffects.ENBY_POWER, 9600, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0), 1f).build();
    // Enby Power I for 24 minutes and Resistance II for 2 minutes -- Also fills players hunger quite a bite
    public static final FoodComponent STRONG_ENBY_ITEM = new FoodComponent.Builder().alwaysEdible().hunger(12).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(ModEffects.ENBY_POWER, 28800, 0), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2400, 1), 1f).build();

}
