package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestosteroneGelCapsuleItem extends Item {

    public TestosteroneGelCapsuleItem(Settings settings) {
        super(settings);
    }
    /*
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(ModEffects.BOY_POWER.getTranslationKey()).formatted(ModEffects.BOY_POWER.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(ModEffects.BOY_POWER, 9600), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        tooltip.add(Text.translatable(StatusEffects.STRENGTH.getTranslationKey()).formatted(StatusEffects.STRENGTH.getCategory().getFormatting())
                .append(" (")
                .append(StatusEffectUtil.getDurationText(new StatusEffectInstance(StatusEffects.STRENGTH, 1200), 1f, world == null ? 20.0f : world.getTickManager().getTickRate()))
                .append(")"));
        super.appendTooltip(stack, world, tooltip, context);
    }

     */
}
