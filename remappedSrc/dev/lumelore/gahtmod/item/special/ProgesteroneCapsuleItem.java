package dev.lumelore.gahtmod.item.special;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class ProgesteroneCapsuleItem extends EffectRemoverItem {

    public ProgesteroneCapsuleItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        StatusEffect girlPower = new StatusEffectInstance(ModEffects.GIRL_POWER).getEffectType().value();
        StatusEffect regeneration = new StatusEffectInstance(StatusEffects.REGENERATION).getEffectType().value();
        tooltip.add(Text.translatable(girlPower.getTranslationKey()).append(" (04:00)").formatted(girlPower.getCategory().getFormatting()));
        tooltip.add(Text.translatable(regeneration.getTranslationKey()).append(" (01:00)").formatted(regeneration.getCategory().getFormatting()));
        tooltip.add(Text.empty());
        super.appendTooltip(stack, context, tooltip, type);
    }
}
