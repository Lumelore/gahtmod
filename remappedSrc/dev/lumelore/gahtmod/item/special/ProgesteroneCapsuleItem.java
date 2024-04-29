package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProgesteroneCapsuleItem extends Item {

    public ProgesteroneCapsuleItem(net.minecraft.item.Item.Settings settings) {
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
        // Remove the following statuses
        if (!world.isClient) {
            user.removeStatusEffect(ModEffects.BOY_POWER);
            user.removeStatusEffect(StatusEffects.WITHER);
            user.removeStatusEffect(StatusEffects.WEAKNESS);
            user.removeStatusEffect(StatusEffects.SLOWNESS);
        }
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, net.minecraft.item.Item.TooltipContext context) {
        tooltip.add(Text.translatable(ModEffects.GIRL_POWER.getTranslationKey()).formatted(ModEffects.GIRL_POWER.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.GIRL_POWER, 9600), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        tooltip.add(Text.translatable(StatusEffects.REGENERATION.getTranslationKey()).formatted(StatusEffects.REGENERATION.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(StatusEffects.REGENERATION, 1200), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("tooltip.gahtmod.blocker_item").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable(ModEffects.BOY_POWER.getTranslationKey()).formatted(ModEffects.BOY_POWER.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.SLOWNESS.getTranslationKey()).formatted(StatusEffects.SLOWNESS.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.WEAKNESS.getTranslationKey()).formatted(StatusEffects.WEAKNESS.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.WITHER.getTranslationKey()).formatted(StatusEffects.WITHER.getCategory().getFormatting()));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
