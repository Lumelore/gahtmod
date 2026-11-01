package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.item.component.GenderEffects;
import net.lumelore.gahtmod.item.component.ModComponents;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class BottleOfGenderItem extends GenderConsumableItem {

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

            GenderEffects effects = itemStack.get(ModComponents.GENDER_EFFECTS);
            if (effects != null) {
                effects.applyGenderEffects(level, (Player) entity, itemStack);
            }

            // Be aware! Decrement can create an empty stack! (it will be air instead of gender bottle if player uses last one)
            itemStack.consume(1, player);
        }

        return itemStack;
    }

}
