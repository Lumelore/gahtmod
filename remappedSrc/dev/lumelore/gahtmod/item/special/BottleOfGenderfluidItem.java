package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BottleOfGenderfluidItem extends Item {

    public BottleOfGenderfluidItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        // Add to item usage statistics
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        // If user is a player
        if (playerEntity != null) {
            // Add to usage stats
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            // Eat item or deplete item
            if (stack.contains(DataComponentTypes.FOOD)) {
                user.eatFood(world, stack, stack.get(DataComponentTypes.FOOD));
            } else {
                stack.decrementUnlessCreative(1, playerEntity);
            }

            // Give the following statuses randomly
            if (!world.isClient) {
                ArrayList<RegistryEntry<StatusEffect>> pool = new ArrayList<>(List.of(ModEffects.GIRL_POWER, ModEffects.BOY_POWER, ModEffects.ENBY_POWER));

                // Before giving statuses, if user has all 3 then remove one randomly
                if (       user.getStatusEffect(ModEffects.GIRL_POWER) != null
                        && user.getStatusEffect(ModEffects.BOY_POWER)  != null
                        && user.getStatusEffect(ModEffects.ENBY_POWER) != null) {
                    user.removeStatusEffect(pool.get((int) (Math.random() * pool.size())));
                }

                RegistryEntry<StatusEffect> toApply;
                boolean goAgain = true;
                do {
                    // Decide if to pick another effect
                    goAgain = (int) (Math.random() * 2) == 0;
                    // Pick random effect
                    toApply = pool.remove((int) (Math.random() * pool.size()));
                    // If user has worse or no gender effect, apply the effect
                    if (userHasWorseGenderEffect((PlayerEntity) user, toApply)) {
                        giveGenderEffect((PlayerEntity) user, toApply);
                    }
                    // If user has the effect, and it is the first one chosen, change it to a different effect
                    // Make sure they do not have the effect first, if they do then remove the current effect
                    else if (pool.size() == 2) {
                        RegistryEntry<StatusEffect> toRemove = toApply;
                        toApply = pool.remove((int) (Math.random() * pool.size()));
                        // give them the new effect
                        if (userHasWorseGenderEffect((PlayerEntity) user, toApply)) {
                            user.addStatusEffect(new StatusEffectInstance(toApply, user.getStatusEffect(toRemove).getDuration(), 0));
                        }
                        // remove the old effect
                        user.removeStatusEffect(toRemove);
                    }
                } while (!pool.isEmpty() && goAgain);
            }
        }

        // Give back 1 glass bottle
        if (playerEntity == null || !playerEntity.isInCreativeMode()) {
            ItemStack bottleStack = new ItemStack(Items.GLASS_BOTTLE);
            if (stack.isEmpty()) {
                return bottleStack;
            }
            // Drop the bottle on the ground if it can't go into the inventory
            if (playerEntity != null && !playerEntity.getInventory().insertStack(bottleStack)) {
                playerEntity.dropItem(bottleStack, false);
            }
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_genderfluid.line1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_genderfluid.line2").formatted(Formatting.GRAY));
    }


    private void giveGenderEffect(PlayerEntity user, RegistryEntry<StatusEffect> genderEffect) {
        user.addStatusEffect(new StatusEffectInstance(genderEffect, 4800, 0));
        if (genderEffect == ModEffects.GIRL_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0));
        }
        else if (genderEffect == ModEffects.BOY_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
        }
        else if (genderEffect == ModEffects.ENBY_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 0));
        }
    }

    private boolean userHasWorseGenderEffect(PlayerEntity user, RegistryEntry<StatusEffect> genderEffect) {
        // if user has status effect
        if (user.getStatusEffect(genderEffect) != null) {
            // if status they already have has greater duration, then what they have is better
            if (user.getStatusEffect(genderEffect).getDuration() > 4800) {
                return false;
            }
            // this effect is better otherwise
            return true;
        }
        // user does not have effect, and thus they have a worse one
        return true;
    }

}
