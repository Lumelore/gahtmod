package dev.lumelore.gahtmod.item.special;

import com.mojang.serialization.MapDecoder;
import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BottleOfGenderItem extends PotionItem {

    public BottleOfGenderItem(Settings settings) {
        // force max count of 1
        super(settings.maxCount(1));
    }

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
                // None of the effects being added are instant but might as well leave it just in case
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
            // Deplete stack by 1
            stack.decrementUnlessCreative(1, playerEntity);
        }
        // Give back bottle of agender
        if (playerEntity == null || !playerEntity.isInCreativeMode()) {
            ItemStack bottleStack = new ItemStack(ModItems.BOTTLE_OF_AGENDER);
            if (stack.isEmpty()) {
                return bottleStack;
            }
            // Drop the bottle on the ground if it can't go into the inventory
            if (playerEntity != null && !playerEntity.getInventory().insertStack(bottleStack)) {
                playerEntity.dropItem(bottleStack, false);
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        // Always give back a bottle of agender after usage
        return new ItemStack(ModItems.BOTTLE_OF_AGENDER);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey();
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

    // Used to convert old data into new data
    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        // If the item has old nbt data
        if (stack.contains(DataComponentTypes.CUSTOM_DATA) && stack.getComponents().get(DataComponentTypes.CUSTOM_DATA).getNbt() != null) {
            // Load nbt data into array
            int[] effects = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA).getNbt().getIntArray("gahtmod.stored_gender");
            // Create list of effects to add to item
            ArrayList<StatusEffectInstance> potionList = new ArrayList<>(3);
            // Put girl power into list if duration is greater than 1 second
            if (effects[0] > 20) {
                potionList.add(new StatusEffectInstance(ModEffects.GIRL_POWER, effects[0], effects[1]));
            }
            // Put boy power into list if duration is greater than 1 second
            if (effects[2] > 20) {
                potionList.add(new StatusEffectInstance(ModEffects.BOY_POWER, effects[2], effects[3]));
            }
            // Put enby power into list if duration is greater than 1 second
            if (effects[4] > 20) {
                potionList.add(new StatusEffectInstance(ModEffects.ENBY_POWER, effects[4], effects[5]));
            }
            stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(), potionList));
            // Remove the old data
            stack.remove(DataComponentTypes.CUSTOM_DATA);
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }

}
