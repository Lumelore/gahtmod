package net.lumelore.gahtmod.util;

import net.lumelore.gahtmod.effect.ModMobEffects;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.gahtmod.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static net.lumelore.gahtmod.effect.ModMobEffects.BOY_POWER;
import static net.lumelore.gahtmod.effect.ModMobEffects.GIRL_POWER;

public abstract class EffectUtil {

    /**
     * Returns either the amplifier level of girl power or boy power, depending on which is higher.
     */
    public static int getHighestGirlBoyAmplifier(Player player) {
        return Stream
                .of(player.getEffect(GIRL_POWER), player.getEffect(BOY_POWER))
                .filter(Objects::nonNull)
                .map(MobEffectInstance::getAmplifier)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    public static void playRechargeSoundIfRecharged(ServerLevel level, Player player) {
        if (((PlayerDataSaver) player).getDashCooldown() == 0 && ((PlayerDataSaver) player).getAbsoluteDashCooldown() == 0) {
            // First param as player makes this client side, use null to make server side so all hear it
            level.playSound(null, player, ModSounds.DASH_RECHARGE, SoundSource.PLAYERS, 1f, 1f);
        }
    }

    public static void spawnParticleIfDashed(ServerLevel level, ServerPlayer player) {
        // Account for faster recharge time if both effects are active
        if (player.getEffect(ModMobEffects.BOY_POWER) == null && ((PlayerDataSaver) player).getDashCooldown() > 45) {
            level.sendParticles(ParticleTypes.CLOUD, true, true, player.getX(), player.getY() + 0.2, player.getZ(), 1, 0, 0, 0, 0);
        }
        else if (player.getEffect(ModMobEffects.BOY_POWER) != null && ((PlayerDataSaver) player).getDashCooldown() > 15) {
            level.sendParticles(ParticleTypes.CLOUD, true, true, player.getX(), player.getY() + 0.2, player.getZ(), 1, 0, 0, 0, 0);
        }
    }

    public static void dash(Player player, GAHTPayloads.SendJumpForBoostPayload sendJumpForBoost) {
        /* Make the Player Dash Only if
        // They are not on the ground
        // They are not creative mode flying
        // Haven't just recently jumped (noJumpDelay)
        // Are pressing the jump key (isJumping)
        // Both dashCooldowns are zero */

        // These are checked in the effect (except for jumping), but we are gonna check again
        // when the server receives the packet just in case
        if (   !player.onGround()
            && !player.getAbilities().flying
            && sendJumpForBoost.isJumping()
            && sendJumpForBoost.noJumpDelay() < 5
            && ((PlayerDataSaver) player).getDashCooldown() == 0
            && ((PlayerDataSaver) player).getAbsoluteDashCooldown() == 0
        ) {

            player.addDeltaMovement(Vec3.directionFromRotation(clampPitch(player.getXRot()), player.getYRot())
                    .scale((EffectUtil.getHighestGirlBoyAmplifier(player) + 1) * 0.8));
            player.syncVelocity = true;

            //If the sourcePlayer has boy power, half the cooldown time
            if (player.getEffect(ModMobEffects.BOY_POWER) == null) {
                ((PlayerDataSaver) player).setDashCooldown(60);
                ((PlayerDataSaver) player).setAbsoluteDashCooldown(30);
            } else {
                ((PlayerDataSaver) player).setDashCooldown(30);
                ((PlayerDataSaver) player).setAbsoluteDashCooldown(15);
            }

            // Add audio to dash
            player.level().playSound(null, player, ModSounds.DASH, SoundSource.PLAYERS, 1f, 1f);
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
