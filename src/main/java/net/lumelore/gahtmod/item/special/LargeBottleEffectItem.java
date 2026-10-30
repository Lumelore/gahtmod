package net.lumelore.gahtmod.item.special;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class LargeBottleEffectItem extends GenderConsumableItem {

    public LargeBottleEffectItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, itemStack);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            player.gameEvent(GameEvent.DRINK);

            Consumable consumable = itemStack.get(DataComponents.CONSUMABLE);
            if (consumable != null) {
                // onConsume decrements item counter so no need to do that here
                consumable.onConsume(level, entity, itemStack);
            }
            else {
                itemStack.consume(1, entity);
            }

            // Return the three bottles from crafting or chuck onto ground if no inv space
            ItemStack returnBottles = new ItemStack(Items.GLASS_BOTTLE, 3);
            if (itemStack.isEmpty()) {
                return returnBottles;
            }
            else if (!player.getInventory().add(returnBottles)) {
                player.drop(returnBottles, false, Prediction.PREDICTED);
            }
        }

        return itemStack;
    }

}
