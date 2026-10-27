package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.GAHTMod;
import net.lumelore.gahtmod.effect.ModEffects;
import net.lumelore.gahtmod.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BottleOfAgenderItem extends Item {

    public BottleOfAgenderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        // Add to item usage statistics
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.DRINK);

            // Give the player a gender bottle when they are done drinking it
            // if they have genders to remove
            if (   user.getStatusEffect(ModEffects.GIRL_POWER) != null
                || user.getStatusEffect(ModEffects.BOY_POWER)  != null
                || user.getStatusEffect(ModEffects.ENBY_POWER) != null
            ) {
                // Putting statuses into bottle of gender item
                ItemStack itemStack = new ItemStack(ModItems.BOTTLE_OF_GENDER);
                loadDataIntoItem((PlayerEntity) user, itemStack);

                // Remove the following statuses from player
                if (!world.isClient) {
                    user.removeStatusEffect(ModEffects.BOY_POWER);
                    user.removeStatusEffect(ModEffects.GIRL_POWER);
                    user.removeStatusEffect(ModEffects.ENBY_POWER);
                }
                // Deplete stack by 1
                stack.decrementUnlessCreative(1, user);
                // Put the new item in the players hand if they used their last bottle of agender
                if (stack.isEmpty()) {
                    return itemStack;
                }
                // Else try to insert it into inventory, if not throw onto ground.
                else if (!((PlayerEntity) user).getInventory().insertStack(itemStack)) {
                    ((PlayerEntity) user).dropItem(itemStack, false);
                }
            }
        }

        return stack;
    }

    private void loadDataIntoItem(PlayerEntity player, ItemStack stack) {
        ArrayList<StatusEffectInstance> potionList = new ArrayList<>(3);
        try {
            // Put girl power into list if player has it
            if (player.getStatusEffect(ModEffects.GIRL_POWER) != null
                    && player.getStatusEffect(ModEffects.GIRL_POWER).getDuration() > 20
            ) {
                potionList.add(new StatusEffectInstance(ModEffects.GIRL_POWER,
                        player.getStatusEffect(ModEffects.GIRL_POWER).getDuration(),
                        player.getStatusEffect(ModEffects.GIRL_POWER).getAmplifier()));
            }
            // Put boy power into list if player has it
            if (player.getStatusEffect(ModEffects.BOY_POWER) != null
                    && player.getStatusEffect(ModEffects.BOY_POWER).getDuration() > 20
            ) {
                potionList.add(new StatusEffectInstance(ModEffects.BOY_POWER,
                        player.getStatusEffect(ModEffects.BOY_POWER).getDuration(),
                        player.getStatusEffect(ModEffects.BOY_POWER).getAmplifier()));
            }
            // Put enby power into list if player has it
            if (player.getStatusEffect(ModEffects.ENBY_POWER) != null
                    && player.getStatusEffect(ModEffects.ENBY_POWER).getDuration() > 20
            ) {
                potionList.add(new StatusEffectInstance(ModEffects.ENBY_POWER,
                        player.getStatusEffect(ModEffects.ENBY_POWER).getDuration(),
                        player.getStatusEffect(ModEffects.ENBY_POWER).getAmplifier()));
            }
        } catch (NullPointerException e) {
            // getDuration() and getAmplifier() throw if the player doesn't have the effect. Given that I do a null
            // check first I find it unlikely this will even happen unless maybe the player drinks the potion like right
            // when it runs out. Better to be safe than sorry tho.
            GAHTMod.LOGGER.warn("Caught NullPointerException while loading effects into Bottle of Agender.");
        }
        stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(), potionList, Optional.empty()));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_agender").formatted(Formatting.GRAY));
        super.appendTooltip(stack, tooltipContext, tooltip, type);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        // Stop item usage if no gender effects present
        if (       user.getStatusEffect(ModEffects.GIRL_POWER) == null
                && user.getStatusEffect(ModEffects.BOY_POWER)  == null
                && user.getStatusEffect(ModEffects.ENBY_POWER) == null
        ) {
            return ActionResult.PASS;
        }
        return super.use(world, user, hand);
    }
}
