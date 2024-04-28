package dev.lumelore.gahtmod.effect;

import dev.lumelore.gahtmod.sound.ModSounds;
import dev.lumelore.gahtmod.util.EntityDataSaver;
import dev.lumelore.mixin.PlayerEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class BoyPowerEffect extends StatusEffect {

    public BoyPowerEffect() {
        // Color is a saturated sky blue :3
        super(StatusEffectCategory.BENEFICIAL, 0x0D79DE);
    }

    // Should always be true
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // This is the implementation of the actual effect
    // dashCooldown - Integer which stores the time in ticks until the player can dash again but zeros when player hits ground.
    // absoluteDashCooldown - Integer which stores the time in ticks until the player can dash again.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // Only apply effect to players
        if (entity instanceof PlayerEntity) {
            // Don't apply effect twice if the player also has girl power
            if (entity.getStatusEffect(ModEffects.GIRL_POWER) == null) {
                // Nbt stuff storing the mods data
                NbtCompound dashCooldownData = ((EntityDataSaver) entity).getPersistentData();
                // Make the Player Dash Only if
                // They are not on the ground
                // Haven't just recently jumped (getJumpingCooldown)
                // Are pressing the jump key (getJumping)
                // Both dashCooldowns are zero
                if (!entity.isOnGround() && ((PlayerEntityAccessor) entity).getJumpingCooldown() < 5 && ((PlayerEntityAccessor) entity).getJumping()
                        && dashCooldownData.getInt("dashCooldown") == 0 && dashCooldownData.getInt("absoluteDashCooldown") == 0) {

                    // Add to their velocity and reset the cooldown
                    ((PlayerEntity) entity).addVelocity(entity.getRotationVector().multiply((amplifier + 1) * 0.8));
                    dashCooldownData.putInt("dashCooldown", 60);
                    dashCooldownData.putInt("absoluteDashCooldown", 30);

                    // Add audio to dash
                    ((PlayerEntity) entity).playSound(ModSounds.DASH, 1f, (float) (1 + (Math.random() * 0.25)));

                }
                // If they touch the ground, zero the regular dashCooldown
                else if (entity.isOnGround()) {
                    dashCooldownData.putInt("dashCooldown", 0);
                }
                // Reduce both timers until they hit zero
                if (dashCooldownData.getInt("dashCooldown") > 0) {
                    dashCooldownData.putInt("dashCooldown", dashCooldownData.getInt("dashCooldown") - 1);
                    playRechargeSoundIfRecharged((PlayerEntity) entity, dashCooldownData);
                }
                if (dashCooldownData.getInt("absoluteDashCooldown") > 0) {
                    dashCooldownData.putInt("absoluteDashCooldown", dashCooldownData.getInt("absoluteDashCooldown") - 1);
                    playRechargeSoundIfRecharged((PlayerEntity) entity, dashCooldownData);
                }
                // Spawn Particles if the player has recently dashed
                spawnParticleIfDashed((PlayerEntity) entity, dashCooldownData);
            }
        }
    }

    private void playRechargeSoundIfRecharged(PlayerEntity player, NbtCompound dashCooldownData) {
        if (dashCooldownData.getInt("dashCooldown") == 0 && dashCooldownData.getInt("absoluteDashCooldown") == 0) {
            player.playSound(ModSounds.DASH_RECHARGE, 1f,1f);
        }
    }

    private void spawnParticleIfDashed(PlayerEntity player, NbtCompound dashCooldownData) {
        if (dashCooldownData.getInt("dashCooldown") > 45) {
            player.getWorld().addParticle(ParticleTypes.CLOUD,
                    player.getX(), player.getY() + 0.2, player.getZ(),
                    0, 0, 0);
        }
    }

}
