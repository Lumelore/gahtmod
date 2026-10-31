package net.lumelore.gahtmod.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static net.lumelore.gahtmod.effect.ModMobEffects.BOY_POWER;
import static net.lumelore.gahtmod.effect.ModMobEffects.GIRL_POWER;

public record GenderEffects(List<ConsumeEffect> onConsumeEffects) implements TooltipProvider {

    public static final Codec<GenderEffects> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ConsumeEffect.CODEC.listOf()
                            // Using optional field so nothing provided just becomes an empty list
                            .optionalFieldOf("on_consume_effects", List.of())
                            .forGetter(GenderEffects::onConsumeEffects)
            ).apply(instance, GenderEffects::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GenderEffects> STREAM_CODEC = StreamCodec.composite(
            ConsumeEffect.STREAM_CODEC.apply(ByteBufCodecs.list()),
            GenderEffects::onConsumeEffects,
            GenderEffects::new
    );

    public void applyGenderEffects(Level level, Player player, ItemStack itemStack) {
        if (!level.isClientSide()) {
            onConsumeEffects.forEach(consumeEffect -> {
                consumeEffect.apply(level, itemStack, player);
            });
        }
    }

    public ArrayList<MutableComponent> buildModifierText(MobEffectInstance effect) {
        ArrayList<MutableComponent> modifiers = new ArrayList<>();

        effect.getEffect().value().createModifiers(effect.getAmplifier(),
                (attribute, modifier) ->
                {
                    double value = modifier.operation() == AttributeModifier.Operation.ADD_VALUE
                            ? modifier.amount()
                            : modifier.amount() * 100; // multiply by 100 if percent

                    MutableComponent built;
                    // Change up how fall damage multiplier is communicated cause the default text seems to imply the opposite
                    if (attribute.value().equals(Attributes.FALL_DAMAGE_MULTIPLIER.value())) {
                        built = modifier.amount() < 1
                                ? Component.translatable("attribute.modifier.take." + modifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value),
                                        Component.translatable("attribute.gahtmod.custom_text.fall_damage"))
                                .withStyle(ChatFormatting.BLUE)
                                : Component.translatable("attribute.modifier.plus." + modifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value),
                                        Component.translatable("attribute.gahtmod.custom_text.fall_damage"))
                                .withStyle(ChatFormatting.RED);
                    }
                    // Every other attribute
                    else {
                        built = modifier.amount() > 0
                                ? Component.translatable("attribute.modifier.plus." + modifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value),
                                        Component.translatable(attribute.value().getDescriptionId()))
                                .withStyle(ChatFormatting.BLUE)
                                : Component.translatable("attribute.modifier.take." + modifier.operation().id(),
                                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value),
                                        Component.translatable(attribute.value().getDescriptionId()))
                                .withStyle(ChatFormatting.RED);
                    }

                    modifiers.add(built);
                });
        return modifiers;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        ArrayList<MutableComponent> addedEffects = new ArrayList<>();
        ArrayList<MutableComponent> removedEffects = new ArrayList<>();
        ArrayList<MutableComponent> modifiers = new ArrayList<>();
        AtomicBoolean seenBoyOrGirlPower = new AtomicBoolean(false);

        onConsumeEffects.forEach(consumeEffect -> {
            // FOR APPLY EFFECTS
            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect applyEffect) {
                applyEffect.effects().forEach(effect -> {
                    addedEffects.add(Component.translatable(
                            "potion.withDuration",
                           Component.translatable("potion.withAmplifier", Component.translatable(effect.getDescriptionId()), Component.translatable("potion.potency." + effect.getAmplifier())),
                                 MobEffectUtil.formatDuration(
                                        effect,
                                        components.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1F),
                                        context.tickRate())
                            )
                            .withStyle(effect.getEffect().value().getCategory().getTooltipFormatting()));

                    // Add modifiers (but only once for girl/boy power)
                    if (effect.getEffect() != BOY_POWER && effect.getEffect() != GIRL_POWER) {
                        modifiers.addAll(buildModifierText(effect));
                    } else if (!seenBoyOrGirlPower.get()) {
                        modifiers.addAll(buildModifierText(effect));
                        seenBoyOrGirlPower.set(true);
                    }

                });
            }
            // FOR REMOVING EFFECTS
            else if (consumeEffect instanceof RemoveStatusEffectsConsumeEffect removeEffect) {
                removeEffect.effects().forEach(effect -> {
                    removedEffects.add(Component.translatable(effect.value().getDescriptionId()).withStyle(effect.value().getCategory().getTooltipFormatting()));
                });
            }
        });

        addedEffects.forEach(consumer::accept);

        if (!removedEffects.isEmpty()) {
            if (!addedEffects.isEmpty()) {
                consumer.accept(CommonComponents.EMPTY); // inserting new line
            }
            consumer.accept(Component.translatable("tooltip.gahtmod.removes_effects").withStyle(ChatFormatting.DARK_PURPLE));
            removedEffects.forEach(consumer::accept);
        }

        if (!modifiers.isEmpty()) {
            consumer.accept(CommonComponents.EMPTY); // inserting new line
            consumer.accept(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
            modifiers.forEach(consumer::accept);
        }

    }

    public static GenderEffects.Builder builder() { return new GenderEffects.Builder(); }

    public static class Builder {
        private final List<ConsumeEffect> onConsumeEffects = new ArrayList<>();

        public GenderEffects.Builder addEffect(ConsumeEffect effect) {
            onConsumeEffects.add(effect);
            return this;
        }

        public GenderEffects build() {
            return new GenderEffects(onConsumeEffects);
        }

    }

}
