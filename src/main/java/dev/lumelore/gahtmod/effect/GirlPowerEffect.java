package dev.lumelore.gahtmod.effect;

import dev.lumelore.gahtmod.util.EntityDataSaver;
import dev.lumelore.mixin.PlayerEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

public class GirlPowerEffect extends StatusEffect {

    public GirlPowerEffect() {
        // Color is a saturated berry red/pink :3
        super(StatusEffectCategory.BENEFICIAL, 0xDE2A84);
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

                //If the player has boy power, half the cooldown time
                if (entity.getStatusEffect(ModEffects.BOY_POWER) == null) {
                    dashCooldownData.putInt("dashCooldown", 60);
                    dashCooldownData.putInt("absoluteDashCooldown", 30);
                }
                else {
                    dashCooldownData.putInt("dashCooldown", 30);
                    dashCooldownData.putInt("absoluteDashCooldown", 15);
                }

                // Add audio to dash
                ((PlayerEntity) entity).playSound(SoundEvents.ENTITY_BREEZE_JUMP, 1f, 0.75f);
            }
            // If they touch the ground, zero the regular dashCooldown
            else if (entity.isOnGround()) {
                dashCooldownData.putInt("dashCooldown", 0);
            }
            // Reduce both timers until they hit zero
            if (dashCooldownData.getInt("dashCooldown") > 0) {
                dashCooldownData.putInt("dashCooldown", dashCooldownData.getInt("dashCooldown") - 1);
                // Play recharge noise if zero
                playRechargeSoundIfRecharged((PlayerEntity) entity, dashCooldownData);
            }
            if (dashCooldownData.getInt("absoluteDashCooldown") > 0) {
                dashCooldownData.putInt("absoluteDashCooldown", dashCooldownData.getInt("absoluteDashCooldown") - 1);
                // Play recharge noise if zero
                playRechargeSoundIfRecharged((PlayerEntity) entity, dashCooldownData);
            }
            // Spawn Particles if the player has recently dashed
            spawnParticleIfDashed((PlayerEntity) entity, dashCooldownData);
        }
    }

    private void playRechargeSoundIfRecharged(PlayerEntity player, NbtCompound dashCooldownData) {
        if (dashCooldownData.getInt("dashCooldown") == 0 && dashCooldownData.getInt("absoluteDashCooldown") == 0) {
            player.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1f, 2);
        }
    }

    private void spawnParticleIfDashed(PlayerEntity player, NbtCompound dashCooldownData) {
        // Account for faster recharge time if both effects are active
        if (player.getStatusEffect(ModEffects.BOY_POWER) == null && dashCooldownData.getInt("dashCooldown") > 45) {
            player.getWorld().addParticle(ParticleTypes.CLOUD,
                    player.getX(), player.getY() + 0.2, player.getZ(),
                    0, 0, 0);
        }
        else if (player.getStatusEffect(ModEffects.BOY_POWER) != null && dashCooldownData.getInt("dashCooldown") > 15) {
            player.getWorld().addParticle(ParticleTypes.CLOUD,
                    player.getX(), player.getY() + 0.2, player.getZ(),
                    0, 0, 0);
        }
    }

}
