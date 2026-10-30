package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.GAHTMod;
import net.lumelore.gahtmod.effect.ModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class BottleOfGenderfluidItem extends GenderConsumableItem {

    public BottleOfGenderfluidItem(Properties properties) {
        super(properties.component(DataComponents.LORE,
                new ItemLore(List.of(
                        Component.translatable("tooltip.gahtmod.bottle_of_genderfluid.line1")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("tooltip.gahtmod.bottle_of_genderfluid.line2")
                                .withStyle(ChatFormatting.GRAY)
                        )))

        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            // stuff (good comment me!)
            CriteriaTriggers.CONSUME_ITEM.trigger(player, itemStack);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            player.gameEvent(GameEvent.DRINK);

            // Give the following statuses randomly
            if (!level.isClientSide()) {
                ArrayList<Holder<MobEffect>> effectPool = new ArrayList<>(List.of(ModMobEffects.GIRL_POWER, ModMobEffects.BOY_POWER, ModMobEffects.ENBY_POWER));

                // Before giving statuses, if player has all 3 then remove one randomly (because fuck you, that's why /s)
                if (   player.getEffect(ModMobEffects.GIRL_POWER) != null
                    || player.getEffect(ModMobEffects.BOY_POWER)  != null
                    || player.getEffect(ModMobEffects.ENBY_POWER) != null
                ) {
                    player.removeEffect(effectPool.get((int)(Math.random() * effectPool.size())));
                }

                Holder<MobEffect> toApply;
                boolean goAgain;
                do {
                    // TODO: Make this less likely over time: 100% -> 50% -> 33% or smthign
                    // Decide if to pick another effect
                    goAgain = (int) (Math.random() * 2) == 0;
                    // Take random effect from pool
                    toApply = effectPool.remove((int) (Math.random() * effectPool.size()));

                    // If player has worse or no gender effect, apply the effect
                    if (userHasWorseGenderEffect(player, toApply)) {
                        giveGenderEffect(player, toApply);
                    }
                    // If player has the effect, and it is the first one chosen, change it to a different effect
                    // Make sure they do not have the effect first, if they do then remove the current effect
                    else if (effectPool.size() == 2) {
                        Holder<MobEffect> toRemove = toApply;
                        toApply = effectPool.remove((int) (Math.random() * effectPool.size()));
                        if (userHasWorseGenderEffect(player, toApply)) {
                            try {
                                player.addEffect(new MobEffectInstance(toApply, player.getEffect(toRemove).getDuration()));
                            } catch (NullPointerException e) {
                                GAHTMod.LOGGER.warn("Caught NullPointerException while applying Bottle of Genderfluid item. (method: finishUsingItem)");
                            }
                        }
                        player.removeEffect(toRemove);
                    }

                } while (!effectPool.isEmpty() && goAgain);
            }

            itemStack.consume(1, player);
        }

        return itemStack;
    }

    private void giveGenderEffect(Player user, Holder<MobEffect> genderEffect) {
        user.addEffect(new MobEffectInstance(genderEffect, 4800, 0));
        if (genderEffect == ModMobEffects.GIRL_POWER) {
            user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
        }
        else if (genderEffect == ModMobEffects.BOY_POWER) {
            user.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 200, 0));
        }
        else if (genderEffect == ModMobEffects.ENBY_POWER) {
            user.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 200, 0));
        }
    }

    private boolean userHasWorseGenderEffect(Player player, Holder<MobEffect> genderEffect) {
        // if player has status effect
        try {
            if (player.getEffect(genderEffect) != null) {
                // if status they already have has greater duration, then what they have is better
                if (player.getEffect(genderEffect).getDuration() > 4800) {
                    return false;
                }
                // this effect is better otherwise
                return true;
            }
        } catch (NullPointerException e) {
            GAHTMod.LOGGER.warn("Caught NullPointerException while applying Bottle of Genderfluid item. (method: userHasWorseGenderEffect)");
        }
        // player does not have effect, and thus they have a worse one
        return true;
    }

}
