package net.lumelore.gahtmod.util;

import net.lumelore.gahtmod.effect.ModEffects;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static net.lumelore.gahtmod.effect.ModEffects.BOY_POWER;
import static net.lumelore.gahtmod.effect.ModEffects.GIRL_POWER;

public abstract class EffectUtil {

    /**
     * Returns either the amplifier level of girl power or boy power, depending on which is higher.
     */
    public static int getHighestGirlBoyAmplifier(PlayerEntity player) {
        return Stream
                .of(player.getStatusEffect(GIRL_POWER), player.getStatusEffect(BOY_POWER))
                .filter(Objects::nonNull)
                .map(StatusEffectInstance::getAmplifier)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    public static void playRechargeSoundIfRecharged(ServerWorld world, PlayerEntity player, NbtCompound dashCooldownData) {
        if (dashCooldownData.getInt("dashCooldown") == 0 && dashCooldownData.getInt("absoluteDashCooldown") == 0) {
            // First param as player makes this client side, use null to make server side so all hear it
            world.playSoundFromEntity(null, player, ModSounds.DASH_RECHARGE, SoundCategory.PLAYERS, 1f, 1f);
        }
    }

    public static void spawnParticleIfDashed(ServerWorld world, ServerPlayerEntity player, NbtCompound dashCooldownData) {
        // Account for faster recharge time if both effects are active
        if (player.getStatusEffect(ModEffects.BOY_POWER) == null && dashCooldownData.getInt("dashCooldown") > 45) {

            world.spawnParticles(ParticleTypes.CLOUD, true, false, player.getX(), player.getY() + 0.2, player.getZ(), 1, 0,0,0,0 );
        }
        else if (player.getStatusEffect(ModEffects.BOY_POWER) != null && dashCooldownData.getInt("dashCooldown") > 15) {
            world.spawnParticles(ParticleTypes.CLOUD, true, false, player.getX(), player.getY() + 0.2, player.getZ(), 1, 0,0,0,0 );

        }
    }

    public static void dash(PlayerEntity player, GAHTPayloads.SendJumpForBoostPayload sendJumpForBoost) {
        NbtCompound dashCooldownData = ((EntityDataSaver) player).getPersistentData();

        /* Make the Player Dash Only if
        // They are not on the ground
        // They are not creative mode flying
        // Haven't just recently jumped (jumpCooldown)
        // Are pressing the jump key (isJumping)
        // Both dashCooldowns are zero */

        // These are checked in the effect (except for jumping), but we are gonna check again
        // when the server receives the packet just in case
        if (!player.isOnGround()
                && !player.getAbilities().flying
                && sendJumpForBoost.jumpingData().getBoolean("isJumping")
                && sendJumpForBoost.jumpingData().getInt("jumpCooldown") < 5
                && dashCooldownData.getInt("dashCooldown") == 0
                && dashCooldownData.getInt("absoluteDashCooldown") == 0
        ) {

            player.addVelocity(player
                    .getRotationVector(EffectUtil.clampPitch(player.getPitch()), player.getYaw())
                    .multiply((EffectUtil.getHighestGirlBoyAmplifier(player) + 1) * 0.8));
            player.velocityModified = true;

            //If the sourcePlayer has boy power, half the cooldown time
            if (player.getStatusEffect(ModEffects.BOY_POWER) == null) {
                dashCooldownData.putInt("dashCooldown", 60);
                dashCooldownData.putInt("absoluteDashCooldown", 30);
            } else {
                dashCooldownData.putInt("dashCooldown", 30);
                dashCooldownData.putInt("absoluteDashCooldown", 15);
            }

            // Add audio to dash
            player.getWorld().playSoundFromEntity(null, player, ModSounds.DASH, SoundCategory.PLAYERS, 1f, (float) (1 + (Math.random() * 0.25)));
        }
    }

    public static float clampPitch(float pitch) {
        if (pitch <= -80) {
            return -90f;
        }
        else if (pitch <= -60) {
            return -70f;
        }
        else if (pitch <= -15) {
            return -45f;
        }
        else if (pitch <= 15) {
            return 0f;
        }
        else if (pitch <= 60) {
            return 45f;
        }
        else if (pitch <= 80) {
            return 70f;
        }
        else {
            return 90f;
        }
    }

}
