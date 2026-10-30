package net.lumelore.gahtmod.item.special;

import net.lumelore.gahtmod.item.component.GenderEffects;
import net.lumelore.gahtmod.item.component.ModComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class GenderConsumableItem extends Item {

    public GenderConsumableItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {

        GenderEffects effects = itemStack.get(ModComponents.GENDER_EFFECTS);
        if (effects != null) {
            effects.applyGenderEffects(level, (Player) entity, itemStack);
        }

        return super.finishUsingItem(itemStack, level, entity);
    }

}
