package net.lumelore.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface PlayerEntityAccessor {
	@Accessor
	boolean getJumping();

	@Accessor
	int getJumpingCooldown();

}

