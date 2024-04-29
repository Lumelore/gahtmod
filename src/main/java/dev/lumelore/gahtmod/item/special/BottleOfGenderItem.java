package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleOfGenderItem extends Item {

    public BottleOfGenderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity) {
            applyEffects((PlayerEntity) user, stack);
        }
        super.finishUsing(stack, world, user);
        // Add to item usage statistics
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            // Give the player an empty gender bottle when they are done drinking it
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.BOTTLE_OF_AGENDER);
            }
            if (!playerEntity.getAbilities().creativeMode) {
                ItemStack itemStack = new ItemStack(ModItems.BOTTLE_OF_AGENDER);
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
        }
        return stack;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
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

    /*
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            int[] effects = stack.getNbt().getIntArray("gahtmod.stored_gender");
            // Girl power tooltip
            if (effects[0] > 0) {
                tooltip.add(Text.translatable(ModEffects.GIRL_POWER.getTranslationKey()).formatted(ModEffects.GIRL_POWER.getCategory().getFormatting())
                        .append(" (")
                        .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.GIRL_POWER, effects[0]), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                        .append(")"));
            }
            // boy power tooltip
            if (effects[2] > 0) {
                tooltip.add(Text.translatable(ModEffects.BOY_POWER.getTranslationKey()).formatted(ModEffects.BOY_POWER.getCategory().getFormatting())
                        .append(" (")
                        .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.BOY_POWER, effects[2]), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                        .append(")"));
            }
            // enby power tooltip
            if (effects[4] > 0) {
                tooltip.add(Text.translatable(ModEffects.ENBY_POWER.getTranslationKey()).formatted(ModEffects.ENBY_POWER.getCategory().getFormatting())
                        .append(" (")
                        .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.ENBY_POWER, effects[4]), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                        .append(")"));
            }
        }
    }*/

    private void applyEffects(PlayerEntity player, ItemStack itemStack) {
        /*
        System.out.println("Here1");
        if (itemStack.hasNbt()) {
            System.out.println("Here2");
            int[] effects = itemStack.getNbt().getIntArray("gahtmod.stored_gender");
            //Apply girl power
            if (effects[0] > 0) {
                player.addStatusEffect(new StatusEffectInstance(ModEffects.GIRL_POWER, effects[0], effects[1]));
            }
            //Apply boy power
            if (effects[2] > 0) {
                player.addStatusEffect(new StatusEffectInstance(ModEffects.BOY_POWER, effects[2], effects[3]));
            }
            //Apply enby power
            if (effects[4] > 0) {
                player.addStatusEffect(new StatusEffectInstance(ModEffects.ENBY_POWER, effects[4], effects[5]));
            }
        }*/
    }

}
