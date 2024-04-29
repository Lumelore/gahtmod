package dev.lumelore.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public interface PlayerEntityAccessor {
	@Accessor
	boolean getJumping();

	@Accessor
	int getJumpingCooldown();
}

