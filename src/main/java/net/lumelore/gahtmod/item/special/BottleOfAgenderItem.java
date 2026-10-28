package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.GAHTMod;
import net.lumelore.gahtmod.effect.ModMobEffects;
import net.lumelore.gahtmod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Prediction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BottleOfAgenderItem extends Item {

    public BottleOfAgenderItem(Properties properties) {
        super(properties
                .component(DataComponents.LORE,
                        new ItemLore(List.of(Component
                                .translatable("tooltip.gahtmod.bottle_of_agender")
                                .withStyle(ChatFormatting.GRAY))))
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            // Do item stats/event stuff
            CriteriaTriggers.CONSUME_ITEM.trigger(player, itemStack);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            player.gameEvent(GameEvent.DRINK);

            // Give the player a gender bottle when they are done drinking it
            // if they have genders to remove
            if (   player.getEffect(ModMobEffects.GIRL_POWER) != null
                || player.getEffect(ModMobEffects.BOY_POWER)  != null
                || player.getEffect(ModMobEffects.ENBY_POWER) != null
            ) {
                // Putting statuses into bottle of gender item
                ItemStack filledBottle = new ItemStack(ModItems.BOTTLE_OF_GENDER);
                loadDataIntoItem(player, filledBottle);

                // Remove the following statuses from player
                if (!level.isClientSide()) {
                    player.removeEffect(ModMobEffects.GIRL_POWER);
                    player.removeEffect(ModMobEffects.BOY_POWER);
                    player.removeEffect(ModMobEffects.ENBY_POWER);
                }

                // Deplete stack by 1
                itemStack.consume(1, player);

                // Put the new item in the players hand if they used their last bottle of agender
                if (itemStack.isEmpty()) {
                    return filledBottle;
                }
                // Else try to insert it into inventory, if not throw onto ground.
                else if (!player.getInventory().add(filledBottle)) {
                    player.drop(filledBottle, false, Prediction.PREDICTED);
                }
            }
        }

        return itemStack;
    }

    private void loadDataIntoItem(Player player, ItemStack stack) {
        ArrayList<MobEffectInstance> potionList = new ArrayList<>(3);
        try {
            // Put girl power into list if player has it
            if (player.getEffect(ModMobEffects.GIRL_POWER) != null
                    && player.getEffect(ModMobEffects.GIRL_POWER).getDuration() > 20
            ) {
                potionList.add(new MobEffectInstance(ModMobEffects.GIRL_POWER,
                        player.getEffect(ModMobEffects.GIRL_POWER).getDuration(),
                        player.getEffect(ModMobEffects.GIRL_POWER).getAmplifier()));
            }
            // Put boy power into list if player has it
            if (player.getEffect(ModMobEffects.BOY_POWER) != null
                    && player.getEffect(ModMobEffects.BOY_POWER).getDuration() > 20
            ) {
                potionList.add(new MobEffectInstance(ModMobEffects.BOY_POWER,
                        player.getEffect(ModMobEffects.BOY_POWER).getDuration(),
                        player.getEffect(ModMobEffects.BOY_POWER).getAmplifier()));
            }
            // Put enby power into list if player has it
            if (player.getEffect(ModMobEffects.ENBY_POWER) != null
                    && player.getEffect(ModMobEffects.ENBY_POWER).getDuration() > 20
            ) {
                potionList.add(new MobEffectInstance(ModMobEffects.ENBY_POWER,
                        player.getEffect(ModMobEffects.ENBY_POWER).getDuration(),
                        player.getEffect(ModMobEffects.ENBY_POWER).getAmplifier()));
            }
        } catch (NullPointerException e) {
            // getDuration() and getAmplifier() throw if the player doesn't have the effect. Given that I do a null
            // check first I find it unlikely this will even happen unless maybe the player drinks the potion like right
            // when it runs out. Better to be safe than sorry tho.
            GAHTMod.LOGGER.warn("Caught NullPointerException while loading effects into Bottle of Agender.");
        }
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), potionList, Optional.empty()));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        // Don't use if no gender effects present
        if (   player.getEffect(ModMobEffects.GIRL_POWER) == null
            && player.getEffect(ModMobEffects.BOY_POWER)  == null
            && player.getEffect(ModMobEffects.ENBY_POWER) == null
        ) {
            return InteractionResult.PASS;
        }
        return super.use(level, player, hand);
    }

}
