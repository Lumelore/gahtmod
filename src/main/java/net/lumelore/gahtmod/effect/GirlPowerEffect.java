package net.lumelore.gahtmod.effect;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.gahtmod.util.EffectUtil;
import net.lumelore.gahtmod.util.EntityDataSaver;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

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

    // THIS USED TO RUN ON SERVER AND CLIENT. IT IS NOW SERVER SIDE ONLY. WE CANNOT KNOW IF PLAYER IS JUMPING FROM HERE.
    // WE HAVE TO ASK THE CLIENT AND HAVE IT SEND THAT INFO BACK TO THE SERVER.
    // dashCooldown - Integer which stores the time in ticks until the player can dash again but zeros when player hits ground.
    // absoluteDashCooldown - Integer which stores the time in ticks until the player can dash again.
    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        // Only apply effect to players
        if (entity instanceof PlayerEntity) {
            // Nbt stuff storing the mods data
            NbtCompound dashCooldownData = ((EntityDataSaver) entity).getPersistentData();
            // Make the Player Dash Only if
            // They are not on the ground
            // They are not creative mode flying
            // Haven't just recently jumped (getJumpingCooldown)
            // Are pressing the jump key (getJumping)
            // Both dashCooldowns are zero

            if (       !entity.isOnGround()
                    && !((PlayerEntity)entity).getAbilities().flying
                    && dashCooldownData.getInt("dashCooldown") == 0
                    && dashCooldownData.getInt("absoluteDashCooldown") == 0
            ) {
                // Add to their velocity and reset the cooldown
                ServerPlayNetworking.send(world.getServer().getPlayerManager().getPlayer(entity.getUuid()),
                        new GAHTPayloads.RequestJumpForBoostPayload((byte) 0));
            }

            // If they touch the ground, zero the regular dashCooldown
            if (entity.isOnGround()) {
                dashCooldownData.putInt("dashCooldown", 0);
            }
            // Reduce both timers until they hit zero
            if (dashCooldownData.getInt("dashCooldown") > 0) {
                dashCooldownData.putInt("dashCooldown", dashCooldownData.getInt("dashCooldown") - 1);
                // Play recharge noise if zero
                EffectUtil.playRechargeSoundIfRecharged(world, (PlayerEntity) entity, dashCooldownData);
            }
            if (dashCooldownData.getInt("absoluteDashCooldown") > 0) {
                dashCooldownData.putInt("absoluteDashCooldown", dashCooldownData.getInt("absoluteDashCooldown") - 1);
                // Play recharge noise if zero
                EffectUtil.playRechargeSoundIfRecharged(world, (PlayerEntity) entity, dashCooldownData);
            }
            // Spawn Particles if the player has recently dashed
            EffectUtil.spawnParticleIfDashed(world, (ServerPlayerEntity) entity, dashCooldownData);
        }
        return true;
    }


}
