package net.lumelore.gahtmod.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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



    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        onConsumeEffects.forEach(consumeEffect -> {
            // FOR APPLY EFFECTS
            if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect applyEffect) {
                applyEffect.effects().forEach(effect -> {
                    consumer.accept(Component.translatable(
                            "potion.withDuration",
                           Component.translatable("potion.withAmplifier", Component.translatable(effect.getDescriptionId()), Component.translatable("potion.potency." + effect.getAmplifier())),
                                 MobEffectUtil.formatDuration(
                                        effect,
                                        components.getOrDefault(DataComponents.POTION_DURATION_SCALE, 1F),
                                        context.tickRate())
                            )
                            .withStyle(effect.getEffect().value().getCategory().getTooltipFormatting()));
                });
            }
            // FOR REMOVING EFFECTS
            else if (consumeEffect instanceof RemoveStatusEffectsConsumeEffect removeEffect) {
                removeEffect.effects().forEach(effect -> {
                    consumer.accept(Component.translatable(effect.value().getDescriptionId()).withStyle(ChatFormatting.RED));
                });
            }

        });
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
