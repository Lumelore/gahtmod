package net.lumelore.gahtmod.item.special;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BottleOfGenderItem extends TooltippedConsumableItem {

    public BottleOfGenderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            user.emitGameEvent(GameEvent.DRINK);
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

            // Applying the effects
            if (!world.isClient) {
                PotionContentsComponent potionContentsComponent = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
                potionContentsComponent.forEachEffect(effect -> {
                    // None of the effects being added are instant but might as well leave it just in case
                    if (effect.getEffectType().value().isInstant()) {
                        effect.getEffectType().value().applyInstantEffect((ServerWorld) world, user, null, user, effect.getAmplifier(), 1.0);
                    } else {
                        user.addStatusEffect(effect);
                    }
                });
            }

            // Be aware! Decrement can create an empty stack! (it will be air instead of gender bottle if player uses last one)
            stack.decrementUnlessCreative(1, user);

        }

        return stack;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }



}
