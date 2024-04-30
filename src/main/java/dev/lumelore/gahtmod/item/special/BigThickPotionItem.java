package dev.lumelore.gahtmod.item.special;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BigThickPotionItem extends PotionItem {

    public BigThickPotionItem(Settings settings) {
        super(settings);
    }


    // Give the player back 3 glass bottles instead of just 1
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        // Applying the effects
        if (!world.isClient) {
            PotionContentsComponent potionContentsComponent = (PotionContentsComponent)stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            potionContentsComponent.forEachEffect((effect) -> {
                if (((StatusEffect)effect.getEffectType().value()).isInstant()) {
                    ((StatusEffect)effect.getEffectType().value()).applyInstantEffect(playerEntity, playerEntity, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addStatusEffect(effect);
                }

            });
        }
        // If user is a player
        if (playerEntity != null) {
            // Add to usage stats
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            // Eat item or deplete item
            if (stack.contains(DataComponentTypes.FOOD)) {
                user.eatFood(world, stack);
            } else {
                stack.decrementUnlessCreative(1, playerEntity);
            }
        }
        // Give back 3 glass bottles
        if (playerEntity == null || !playerEntity.isInCreativeMode()) {
            ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);
            bottleStack.setCount(3);
            if (stack.isEmpty()) {
                return bottleStack;
            }
            // Drop the bottles on the ground if it can't go into the inventory
            if (playerEntity != null && !playerEntity.getInventory().insertStack(bottleStack)) {
                playerEntity.dropItem(bottleStack, false);
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

}
