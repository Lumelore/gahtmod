package net.lumelore.gahtmod.effect;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.lumelore.gahtmod.network.GAHTPayloads;
import net.lumelore.gahtmod.util.EffectUtil;
import net.lumelore.gahtmod.util.PlayerDataSaver;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

public class GirlPowerEffect extends MobEffect {

    public GirlPowerEffect() {
        // Color is a saturated berry red/pink :3
        super(MobEffectCategory.BENEFICIAL, 0xDE2A84);
    }

    // Should always be true
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    // THIS USED TO RUN ON SERVER AND CLIENT. IT IS NOW SERVER SIDE ONLY. WE CANNOT KNOW IF PLAYER IS JUMPING FROM HERE.
    // WE HAVE TO ASK THE CLIENT AND HAVE IT SEND THAT INFO BACK TO THE SERVER.
    // dashCooldown - Integer which stores the time in ticks until the player can dash again but zeros when player hits ground.
    // absoluteDashCooldown - Integer which stores the time in ticks until the player can dash again.
    @Override
    public boolean applyEffectTick(@NonNull ServerLevel world, @NonNull LivingEntity entity, int amplifier) {
        // Only apply effect to players
        if (entity instanceof Player player) {

            // Make the Player Dash Only if
            // They are not on the ground
            // They are not creative mode flying
            // Haven't just recently jumped (getJumpingCooldown)
            // Are pressing the jump key (getJumping)
            // Both dashCooldowns are zero

            if (       !player.onGround()
                    && !player.getAbilities().flying
                    && ((PlayerDataSaver)player).getDashCooldown() == 0
                    && ((PlayerDataSaver)player).getAbsoluteDashCooldown() == 0
            ) {
                // Ask the client if the player is jumping, if so the rest is handled in EffectUtil
                ServerPlayNetworking.send(world.getServer().getPlayerList().getPlayer(player.getUUID()),
                        new GAHTPayloads.RequestJumpForBoostPayload((byte) 0));
            }

            // If they touch the ground, zero the regular dashCooldown
            if (entity.onGround()) {
                ((PlayerDataSaver)player).setDashCooldown(0);
            }
            // Reduce both timers until they hit zero
            if (((PlayerDataSaver)player).getDashCooldown() > 0) {
                ((PlayerDataSaver)player).setDashCooldown(((PlayerDataSaver)player).getDashCooldown() - 1);
                // Play recharge noise if zero
                EffectUtil.playRechargeSoundIfRecharged(world, player);
            }
            if (((PlayerDataSaver)player).getAbsoluteDashCooldown() > 0) {
                ((PlayerDataSaver)player).setAbsoluteDashCooldown(((PlayerDataSaver)player).getAbsoluteDashCooldown() - 1);
                // Play recharge noise if zero
                EffectUtil.playRechargeSoundIfRecharged(world, player);
            }
            // Spawn Particles if the player has recently dashed
            EffectUtil.spawnParticleIfDashed(world, (ServerPlayer) entity);
        }
        return true;
    }


}
