package dev.lumelore.gahtmod.item.special;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class EffectRemoverItem extends Item {

    public EffectRemoverItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        // Remove effects from the player
        if (!world.isClient) {
            PotionContentsComponent potionContentsComponent = (PotionContentsComponent)stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
            potionContentsComponent.forEachEffect( (effect) -> user.removeStatusEffect(effect.getEffectType()) );
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
        }

        user.emitGameEvent(GameEvent.EAT);
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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        // Add "removes effects:" text
        tooltip.add(Text.translatable("tooltip.gahtmod.removes_effects").formatted(Formatting.DARK_PURPLE));
        // Get potion effects
        PotionContentsComponent potionContentsComponent = (PotionContentsComponent)stack.get(DataComponentTypes.POTION_CONTENTS);
        if (potionContentsComponent != null) {
            potionContentsComponent.forEachEffect((effect) -> {
                // Effect remover only works on non-instant effects
                if (!(effect.getEffectType().value()).isInstant()) {
                    // Get translation key for effect and apply formatting for its category
                    tooltip.add(Text.translatable(effect.getTranslationKey()).formatted(effect.getEffectType().value().getCategory().getFormatting()));
                }
            });
        }
    }

}
