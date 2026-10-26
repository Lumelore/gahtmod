package net.lumelore.gahtmod.item.special;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LargeBottleEffectItem extends TooltippedConsumableItem {

    public LargeBottleEffectItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.DRINK);

            // Apply consume effects if present
            ConsumableComponent consumableComponent = stack.get(DataComponentTypes.CONSUMABLE);
            if (consumableComponent != null) {
                consumableComponent.finishConsumption(world, user, stack);
            } else {
                stack.decrementUnlessCreative(1, user);
            }

            // Return the three bottles from crafting or chuck onto ground if no inv space
            ItemStack returnBottles = new ItemStack(Items.GLASS_BOTTLE, 3);
            if (stack.isEmpty()) {
                return returnBottles;
            }
            else if (!((PlayerEntity) user).getInventory().insertStack(returnBottles)) {
                ((PlayerEntity) user).dropItem(returnBottles, false);
            }
        }

        return stack;
    }

}
