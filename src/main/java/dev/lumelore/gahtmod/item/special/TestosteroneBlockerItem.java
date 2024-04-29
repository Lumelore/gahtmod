package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestosteroneBlockerItem extends Item {

    public TestosteroneBlockerItem(Settings settings) {
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
            user.removeStatusEffect((RegistryEntry<StatusEffect>) ModEffects.BOY_POWER);
            user.removeStatusEffect(StatusEffects.WITHER);
            user.removeStatusEffect(StatusEffects.WEAKNESS);
            user.removeStatusEffect(StatusEffects.SLOWNESS);
        }
        return stack;
    }
    /*
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.gahtmod.blocker_item").formatted(Formatting.DARK_PURPLE));
        tooltip.add(Text.translatable(ModEffects.BOY_POWER.getTranslationKey()).formatted(ModEffects.BOY_POWER.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.SLOWNESS.).formatted(StatusEffects.SLOWNESS.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.WEAKNESS.getTranslationKey()).formatted(StatusEffects.WEAKNESS.getCategory().getFormatting()));
        tooltip.add(Text.translatable(StatusEffects.WITHER.getTranslationKey()).formatted(StatusEffects.WITHER.getCategory().getFormatting()));
        super.appendTooltip(stack, world, tooltip, context);
    }
    */

}
