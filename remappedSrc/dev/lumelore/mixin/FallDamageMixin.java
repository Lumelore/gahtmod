package dev.lumelore.mixin;

import dev.lumelore.gahtmod.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class FallDamageMixin {
    @Inject(method="computeFallDamage", at = @At("RETURN"), cancellable = true)
    private void modifyFallDamageIfEnbyGenderIsPresent(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> c) {
        // mod fall damage for entities with enby power
        LivingEntity entity = ((LivingEntity) ((Object)this));
        if (entity.getStatusEffect(ModEffects.ENBY_POWER) != null) {
            c.setReturnValue((c.getReturnValue() + 2) / 2);
        }
    }
}
