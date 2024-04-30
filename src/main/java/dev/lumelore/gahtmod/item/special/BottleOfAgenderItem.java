package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.ModItems;
import dev.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BottleOfAgenderItem extends Item {

    public BottleOfAgenderItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        // Add to item usage statistics
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity playerEntity) {
            // Give the player a gender bottle when they are done drinking it
            // if they have genders to remove
            if (   user.getStatusEffect(ModEffects.GIRL_POWER) != null
                || user.getStatusEffect(ModEffects.BOY_POWER)  != null
                || user.getStatusEffect(ModEffects.ENBY_POWER) != null) {

                // Putting statuses into bottle of gender item
                ItemStack itemStack = new ItemStack(ModItems.BOTTLE_OF_GENDER);
                loadDataIntoItem(playerEntity, itemStack);

                // Remove the following statuses from player
                if (!world.isClient) {
                    user.removeStatusEffect(ModEffects.BOY_POWER);
                    user.removeStatusEffect(ModEffects.GIRL_POWER);
                    user.removeStatusEffect(ModEffects.ENBY_POWER);
                }
                // Deplete stack by 1
                stack.decrement(1);
                // Put the new item in the players hand if they used their last bottle of agender (they most likely did)
                if (stack.isEmpty()) {
                    return itemStack;
                }
                // Else try to insert it into inventory, if not throw onto ground.
                else if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
        }
        // If they don't have a gender status, don't consume item
        return new ItemStack(ModItems.BOTTLE_OF_AGENDER);
    }

    private void loadDataIntoItem(PlayerEntity player, ItemStack stack) {
        ArrayList<StatusEffectInstance> potionList = new ArrayList<>(3);
        // Put girl power into list if player has it
        if (player.getStatusEffect(ModEffects.GIRL_POWER) != null
                && player.getStatusEffect(ModEffects.GIRL_POWER).getDuration() > 20) {
            potionList.add(new StatusEffectInstance(ModEffects.GIRL_POWER,
                    player.getStatusEffect(ModEffects.GIRL_POWER).getDuration(),
                    player.getStatusEffect(ModEffects.GIRL_POWER).getAmplifier()));
        }
        // Put boy power into list if player has it
        if (player.getStatusEffect(ModEffects.BOY_POWER) != null
                && player.getStatusEffect(ModEffects.BOY_POWER).getDuration() > 20) {
            potionList.add(new StatusEffectInstance(ModEffects.BOY_POWER,
                    player.getStatusEffect(ModEffects.BOY_POWER).getDuration(),
                    player.getStatusEffect(ModEffects.BOY_POWER).getAmplifier()));
        }
        // Put enby power into list if player has it
        if (player.getStatusEffect(ModEffects.ENBY_POWER) != null
                && player.getStatusEffect(ModEffects.ENBY_POWER).getDuration() > 20) {
            potionList.add(new StatusEffectInstance(ModEffects.ENBY_POWER,
                    player.getStatusEffect(ModEffects.ENBY_POWER).getDuration(),
                    player.getStatusEffect(ModEffects.ENBY_POWER).getAmplifier()));
        }
        stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(), potionList));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
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
        return ModSounds.REVERSE_DRINKING;
    }

    @Override
    public SoundEvent getEatSound() {
        return ModSounds.REVERSE_DRINKING;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_agender").formatted(Formatting.GRAY));
        super.appendTooltip(stack, tooltipContext, tooltip, type);
    }
}
