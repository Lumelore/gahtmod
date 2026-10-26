package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.GAHTMod;
import net.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class BottleOfGenderfluidItem extends TooltippedConsumableItem {

    public BottleOfGenderfluidItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            // stuff (good comment me!)
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.DRINK);

            // Give the following statuses randomly
            if (!world.isClient) {
                ArrayList<RegistryEntry<StatusEffect>> pool = new ArrayList<>(List.of(ModEffects.GIRL_POWER, ModEffects.BOY_POWER, ModEffects.ENBY_POWER));

                // Before giving statuses, if user has all 3 then remove one randomly
                if (user.getStatusEffect(ModEffects.GIRL_POWER) != null
                        && user.getStatusEffect(ModEffects.BOY_POWER) != null
                        && user.getStatusEffect(ModEffects.ENBY_POWER) != null) {
                    user.removeStatusEffect(pool.get((int) (Math.random() * pool.size())));
                }

                RegistryEntry<StatusEffect> toApply;
                boolean goAgain;
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
                            try {
                                user.addStatusEffect(new StatusEffectInstance(toApply, user.getStatusEffect(toRemove).getDuration(), 0));
                            }
                            catch (NullPointerException e) {
                                GAHTMod.LOGGER.warn("Caught NullPointerException while applying Bottle of Genderfluid item. (method: finishUsing)");
                            }
                        }
                        // remove the old effect
                        user.removeStatusEffect(toRemove);
                    }
                } while (!pool.isEmpty() && goAgain);
            }

            stack.decrementUnlessCreative(1, user);
        }

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_genderfluid.line1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_genderfluid.line2").formatted(Formatting.GRAY));
    }


    private void giveGenderEffect(PlayerEntity user, RegistryEntry<StatusEffect> genderEffect) {
        user.addStatusEffect(new StatusEffectInstance(genderEffect, 4800, 0));
        if (genderEffect == ModEffects.GIRL_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0));
        }
        else if (genderEffect == ModEffects.BOY_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 0));
        }
        else if (genderEffect == ModEffects.ENBY_POWER) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0));
        }
    }

    private boolean userHasWorseGenderEffect(PlayerEntity user, RegistryEntry<StatusEffect> genderEffect) {
        // if user has status effect
        try {
            if (user.getStatusEffect(genderEffect) != null) {
                // if status they already have has greater duration, then what they have is better
                if (user.getStatusEffect(genderEffect).getDuration() > 4800) {
                    return false;
                }
                // this effect is better otherwise
                return true;
            }
        } catch (NullPointerException e) {
            GAHTMod.LOGGER.warn("Caught NullPointerException while applying Bottle of Genderfluid item. (method: userHasWorseGenderEffect)");
        }
        // user does not have effect, and thus they have a worse one
        return true;
    }

}
