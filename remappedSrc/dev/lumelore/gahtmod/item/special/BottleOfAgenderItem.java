package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.ModItems;
import dev.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import java.util.List;

public class BottleOfAgenderItem extends Item {

    public BottleOfAgenderItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        // Add to item usage statistics
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity) {
            // Give the player a gender bottle when they are done drinking it
            // if they have genders to remove
            if (   user.getStatusEffect(ModEffects.GIRL_POWER) != null
                || user.getStatusEffect(ModEffects.BOY_POWER)  != null
                || user.getStatusEffect(ModEffects.ENBY_POWER) != null) {

                // Putting statuses into bottle of gender item
                PlayerEntity playerEntity = (PlayerEntity) user;
                ItemStack itemStack = new ItemStack(ModItems.BOTTLE_OF_GENDER);
                loadDataIntoItem(playerEntity, itemStack);

                // Remove the following statuses from player
                if (!world.isClient) {
                    user.removeStatusEffect(ModEffects.BOY_POWER);
                    user.removeStatusEffect(ModEffects.GIRL_POWER);
                    user.removeStatusEffect(ModEffects.ENBY_POWER);
                }
                // Put the new item in the players hand if they used their last bottle of agender
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
        // Each pair of zeros is for duration and amplifier
        // Pairs in order are for, girl power, boy power, and enby power
        int[] tempArr = {0,0, 0,0, 0,0};
        // If the player has girl power store in array
        if (player.getStatusEffect(ModEffects.GIRL_POWER) != null) {
            tempArr[0] = player.getStatusEffect(ModEffects.GIRL_POWER).getDuration();
            tempArr[1] = player.getStatusEffect(ModEffects.GIRL_POWER).getAmplifier();
        }
        // If the player has boy power store in array
        if (player.getStatusEffect(ModEffects.BOY_POWER) != null) {
            tempArr[2] = player.getStatusEffect(ModEffects.BOY_POWER).getDuration();
            tempArr[3] = player.getStatusEffect(ModEffects.BOY_POWER).getAmplifier();
        }
        // If the player has enby power store in array
        if (player.getStatusEffect(ModEffects.ENBY_POWER) != null) {
            tempArr[4] = player.getStatusEffect(ModEffects.ENBY_POWER).getDuration();
            tempArr[5] = player.getStatusEffect(ModEffects.ENBY_POWER).getAmplifier();
        }
        stack.setNbt(new NbtCompound());
        stack.getNbt().putIntArray("gahtmod.stored_gender", tempArr);
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, net.minecraft.item.Item.TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.gahtmod.bottle_of_agender").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
