package dev.lumelore.gahtmod.item.special;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CrunchyPotionItem extends PotionItem {

    public CrunchyPotionItem(Settings settings) {
        super(settings);
    }

    // Mostly same as the method in PotionItem class, crunchy potion items don't
    // give bottles back so had to remove that part
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

        // The part here where a glass bottle is added to inventory was removed.
        // This item does not give the player anything back.

        user.emitGameEvent(GameEvent.EAT);
        return stack;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

}
