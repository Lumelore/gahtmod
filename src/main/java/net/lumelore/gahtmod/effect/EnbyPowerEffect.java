package net.lumelore.gahtmod.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class EnbyPowerEffect extends MobEffect {

    protected EnbyPowerEffect() {
        // Color is a rich purple :3
        super(MobEffectCategory.BENEFICIAL, 0x8518C4);
    }

    // Should always be true
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    // TODO: Test if i used the right methods that replaced the old velocity ones
    @Override
    public boolean applyEffectTick(@NonNull ServerLevel world, @NonNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Apply effect if player is walking into a block that they can climb
            // and also isn't creative flying
            if (entity.horizontalCollision && canClimb(player, world) && !player.getAbilities().flying) {
                entity.setDeltaMovement(entity.getDeltaMovement().get(Direction.Axis.X), 0.2, entity.getDeltaMovement().get(Direction.Axis.Z));
                entity.syncVelocity = true;

            }
            // Hold entity in place vertically if sneaking while climbing
            else if (canClimb(player, world) && player.isCrouching() && !player.getAbilities().flying) {
                entity.setDeltaMovement(entity.getDeltaMovement().get(Direction.Axis.X), entity.getGravity(), entity.getDeltaMovement().get(Direction.Axis.Z));
                entity.syncVelocity = true;
            }
        }
        return true;
    }

    private boolean canClimb(Player entity, ServerLevel world) {
        // Find the adjacent block at head height that is solid.
        // TODO: MASSIVE TODO
        /*
        Optional<BlockPos> closetBlock = BlockPos.findClosest(entity.getBlockPos().up(), 1, 0, b->entity.getWorld().getBlockState(b).isSideSolidFullSquare(world, b, entity.getMovementDirection().getOpposite()));
        Optional<BlockPos> closetBlockUnder = BlockPos.findClosest(entity.getBlockPos(), 1, 0, b->entity.getWorld().getBlockState(b).isSideSolidFullSquare(world, b, entity.getMovementDirection().getOpposite()));
        // If it has been found, player can climb
        if (!closetBlock.equals(Optional.empty())) {
            return true;
        }

        // Can also climb if block underneath closetBlock is solid, but player is not on the ground.
        else return closetBlockUnder.isPresent() && world.getBlockState(closetBlockUnder.get().below())
                .isFaceSturdy(world, closetBlockUnder.get(), entity.getMotionDirection().getOpposite())
                 && !entity.onGround();

         */
        return true;
    }

}
