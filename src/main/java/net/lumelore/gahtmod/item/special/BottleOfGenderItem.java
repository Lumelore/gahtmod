package net.lumelore.gahtmod.item.special;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class BottleOfGenderItem extends TooltippedConsumableItem {

    public BottleOfGenderItem(Properties properties) {
        super(properties
                .stacksTo(1)
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, itemStack);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            player.gameEvent(GameEvent.DRINK);

            // Applying the effects
            if (!level.isClientSide()) {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                potionContents.forEachEffect(effect -> {
                    // None of the effects being added are instant but might as well leave it just in case
                    if (effect.getEffect().value().isInstantaneous()) {
                        effect.getEffect().value().applyInstantaneousEffect((ServerLevel) level, player, null, player, effect.getAmplifier(), 1.0);
                    } else {
                        player.addEffect(effect);
                    }
                }, 1);
            }

            // Be aware! Decrement can create an empty stack! (it will be air instead of gender bottle if player uses last one)
            itemStack.consume(1, player);
        }

        return itemStack;
    }

}
