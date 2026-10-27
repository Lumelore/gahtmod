package net.lumelore.gahtmod.item.special;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static net.lumelore.gahtmod.effect.ModEffects.BOY_POWER;
import static net.lumelore.gahtmod.effect.ModEffects.GIRL_POWER;

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
        ArrayList<Text> modifiers = new ArrayList<>();
        // Put consumableComponent effects into the tooltip if present
        if (consumableComponent != null) {
            consumableComponent.onConsumeEffects().forEach(consumeEffect -> {
                // If effect is applied
                if (consumeEffect instanceof ApplyEffectsConsumeEffect) {
                    // Get the effects
                    List<StatusEffectInstance> effects = ((ApplyEffectsConsumeEffect) consumeEffect).effects();
                    AtomicBoolean containsBoyOrGirlEffects = new AtomicBoolean(false);
                    // Iterate through effects in the component
                    effects.forEach(effect -> {
                        // Add each effect in component to list of effects to be applied
                        addEffects.add(
                                Text.translatable(effect.getTranslationKey()) // Effect Name
                                        .append(getAmplifier(effect)) // Amplifier (includes space(s))
                                        .append(getDuration(effect, context.getUpdateTickRate())) // Duration
                                        .formatted(effect.getEffectType().value().getCategory().getFormatting())
                        );

                        // Boy power and girl power attributes don't stack, so don't double add them
                        if ((effect.getEffectType() == BOY_POWER && !containsBoyOrGirlEffects.get()) || (effect.getEffectType() == GIRL_POWER && !containsBoyOrGirlEffects.get()) ) {
                            modifiers.addAll(modifierText(effect));
                            containsBoyOrGirlEffects.set(true);
                        } else if (effect.getEffectType() != BOY_POWER && effect.getEffectType() != GIRL_POWER) {
                            modifiers.addAll(modifierText(effect));
                        }
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
            AtomicBoolean containsBoyOrGirlEffects = new AtomicBoolean(false);

            potionComponent.forEachEffect(effect -> {
                addEffects.add(
                        Text.translatable(effect.getTranslationKey()) // Effect Name
                            .append(getAmplifier(effect)) // Amplifier (includes space(s))
                            .append(getDuration(effect, context.getUpdateTickRate())) // Duration
                            .formatted(effect.getEffectType().value().getCategory().getFormatting())
                );

                // Boy power and girl power attributes don't stack, so don't double add them
                if ((effect.getEffectType() == BOY_POWER && !containsBoyOrGirlEffects.get()) || (effect.getEffectType() == GIRL_POWER && !containsBoyOrGirlEffects.get()) ) {
                    modifiers.addAll(modifierText(effect));
                    containsBoyOrGirlEffects.set(true);
                } else if (effect.getEffectType() != BOY_POWER && effect.getEffectType() != GIRL_POWER) {
                    modifiers.addAll(modifierText(effect));
                }
            });
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

        // Add modifiers at the very bottom
        if (!modifiers.isEmpty()) {
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
            tooltip.addAll(modifiers);
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

    private static ArrayList<Text> modifierText(StatusEffectInstance effect) {
        ArrayList<Text> modifiers = new ArrayList<>();

        effect.getEffectType().value().forEachAttributeModifier(effect.getAmplifier(),
                (attribute, modifier) ->
                {
                    double value = modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE
                            ? modifier.value()
                            : modifier.value() * 100; // multiply by 100 if percent

                    Text built;
                    // Change up how fall damage multiplier is communicated cause the default text seems to imply the opposite
                    if (attribute.getIdAsString().equals(EntityAttributes.FALL_DAMAGE_MULTIPLIER.getIdAsString())) {
                        built = modifier.value() < 1
                                ? Text.translatable("attribute.modifier.take." + modifier.operation().getId(),
                                        AttributeModifiersComponent.DECIMAL_FORMAT.format(value),
                                        Text.translatable("attribute.gahtmod.custom_text.fall_damage"))
                                            .formatted(Formatting.BLUE)
                                : Text.translatable("attribute.modifier.plus." + modifier.operation().getId(),
                                        AttributeModifiersComponent.DECIMAL_FORMAT.format(value),
                                        Text.translatable("attribute.gahtmod.custom_text.fall_damage"))
                                            .formatted(Formatting.RED);
                    }
                    // Every other attribute
                    else {
                        built = modifier.value() > 0
                                ? Text.translatable("attribute.modifier.plus." + modifier.operation().getId(),
                                        AttributeModifiersComponent.DECIMAL_FORMAT.format(value),
                                        Text.translatable(attribute.value().getTranslationKey()))
                                            .formatted(Formatting.BLUE)
                                : Text.translatable("attribute.modifier.take." + modifier.operation().getId(),
                                        AttributeModifiersComponent.DECIMAL_FORMAT.format(value),
                                        Text.translatable(attribute.value().getTranslationKey()))
                                            .formatted(Formatting.RED);
                    }

                    modifiers.add(built);
                });
        return modifiers;
    }

}
