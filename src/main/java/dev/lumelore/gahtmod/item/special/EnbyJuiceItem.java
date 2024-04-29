package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
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

public class EnbyJuiceItem extends Item {

    public EnbyJuiceItem(Settings settings) {
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
        // Give the player a glass bottle when they are done drinking it
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            if (!playerEntity.getAbilities().creativeMode) {
                ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
        }
        return stack;
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(ModEffects.ENBY_POWER.getTranslationKey()).formatted(ModEffects.ENBY_POWER.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.ENBY_POWER, 9600), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        tooltip.add(Text.translatable(StatusEffects.RESISTANCE.getTranslationKey()).formatted(StatusEffects.RESISTANCE.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
