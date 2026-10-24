package net.lumelore.gahtmod.item.special;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class TooltippedConsumableItem extends Item {

    public TooltippedConsumableItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        // Items may have potion effects in a ConsumableComponent or a PotionContentsComponent
        // A ConsumableComponent holds a list of ConsumeEffects. Each ConsumeEffect holds a list of effects.
        ConsumableComponent consumableComponent = stack.get(DataComponentTypes.CONSUMABLE);
        PotionContentsComponent potionComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
        // To hold effect text until they are ready to be added
        ArrayList<Text> addEffects = new ArrayList<>();
        ArrayList<Text> removeEffects = new ArrayList<>();

        // Put consumableComponent effects into the tooltip if present
        if (consumableComponent != null) {
            consumableComponent.onConsumeEffects().forEach(consumeEffect -> {
                // If effect is applied
                if (consumeEffect instanceof ApplyEffectsConsumeEffect) {
                    // Get the effects
                    List<StatusEffectInstance> effects = ((ApplyEffectsConsumeEffect) consumeEffect).effects();

                    // Iterate through effects in the component
                    effects.forEach(effect -> {
                        // Add each effect in component to list of effects to be applied
                        addEffects.add(
                                Text.translatable(effect.getTranslationKey()) // Effect Name
                                        .append(getAmplifier(effect)) // Amplifier (includes space(s))
                                        .append(getDuration(effect, context.getUpdateTickRate())) // Duration
                                        .formatted(effect.getEffectType().value().getCategory().getFormatting())
                        );
                    });
                }

                // If effect is removed
                if (consumeEffect instanceof RemoveEffectsConsumeEffect) {
                    // Get the effects
                    RegistryEntryList<StatusEffect> effects = ((RemoveEffectsConsumeEffect) consumeEffect).effects();

                    // Iterate through effects in component
                    effects.forEach(effect -> {
                        // Add each effect in component to list of effects to be removed
                        removeEffects.add(Text.translatable(effect.value().getTranslationKey())
                                .formatted(effect.value().getCategory().getFormatting()));
                    });
                }
            });
        }

        // Repeat above for potion shit
        if (potionComponent != null) {
            potionComponent.forEachEffect(effect -> addEffects.add(
                    Text.translatable(effect.getTranslationKey()) // Effect Name
                            .append(getAmplifier(effect)) // Amplifier (includes space(s))
                            .append(getDuration(effect, context.getUpdateTickRate())) // Duration
                            .formatted(effect.getEffectType().value().getCategory().getFormatting())
            ));
        }

        // Add given effects (if present)
        if (!addEffects.isEmpty()) {
            // List add effects
            tooltip.addAll(addEffects);
        }
        // Add removed effects tooltip (if present)
        if (!removeEffects.isEmpty()) {
            // Add empty Space to separate added effects and removed effects if needed
            if (!addEffects.isEmpty()) {
                tooltip.add(Text.of(""));
            }
            // Add "removes effects:" text
            tooltip.add(Text.translatable("tooltip.gahtmod.removes_effects").formatted(Formatting.DARK_PURPLE));
            // List remove effects
            tooltip.addAll(removeEffects);
        }

    }

    // Used to get amplifier for effect in tooltip
    private static Text getAmplifier(StatusEffectInstance effect) {
        // A level 1 effect is internally level 0, and that amplifier is not displayed next to effect name
        if (effect.getAmplifier() != 0) {
            return MutableText
                    .of(Text.of(" ").getContent())
                    .append( Text.translatable( "potion.potency." + effect.getAmplifier()) )
                    .append(" ");
        }
        else return Text.of(" ");
    }

    // Used to get duration for effect in tooltip
    private static Text getDuration(StatusEffectInstance effect, Float tickRate) {
        if (effect.isInfinite() || effect.getDuration() >= 20) {
            // Multiplier should always be 1
            return Text.of("(" + StatusEffectUtil.getDurationText(effect, 1f, tickRate).getLiteralString() + ")");
        }
        return Text.empty();
    }

}
