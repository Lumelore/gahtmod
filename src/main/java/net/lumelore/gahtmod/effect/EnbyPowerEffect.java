package net.lumelore.gahtmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class EnbyPowerEffect extends StatusEffect {

    protected EnbyPowerEffect() {
        // Color is a rich purple :3
        super(StatusEffectCategory.BENEFICIAL, 0x8518C4);
    }

    // Should always be true
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            // Apply effect if player is walking into a block that they can climb
            // and also isn't creative flying
            if (entity.horizontalCollision && canClimb((PlayerEntity) entity, world) && !((PlayerEntity) entity).getAbilities().flying) {
                entity.setVelocity(entity.getVelocity().getX(), 0.2, entity.getVelocity().getZ());
                entity.velocityModified = true;
            }
            // Hold entity in place vertically if sneaking while climbing
            else if (canClimb((PlayerEntity) entity, world) && entity.isSneaking() && !((PlayerEntity) entity).getAbilities().flying) {
                entity.setVelocity(entity.getVelocity().getX(), entity.getFinalGravity(), entity.getVelocity().getZ());
                entity.velocityModified = true;
            }
        }
        return true;
    }

    private boolean canClimb(PlayerEntity entity, ServerWorld world) {
        // Find the adjacent block at head height that is solid.
        Optional<BlockPos> closetBlock = BlockPos.findClosest(entity.getBlockPos().up(), 1, 0, b->entity.getWorld().getBlockState(b).isSideSolidFullSquare(world, b, entity.getMovementDirection().getOpposite()));
        Optional<BlockPos> closetBlockUnder = BlockPos.findClosest(entity.getBlockPos(), 1, 0, b->entity.getWorld().getBlockState(b).isSideSolidFullSquare(world, b, entity.getMovementDirection().getOpposite()));
        // If it has been found, player can climb
        if (!closetBlock.equals(Optional.empty())) {
            return true;
        }

        // Can also climb if block underneath closetBlock is solid, but player is not on the ground.
        else return closetBlockUnder.isPresent() && entity.getWorld().getBlockState(closetBlockUnder.get().down())
                .isSideSolidFullSquare(world, closetBlockUnder.get(), entity.getMovementDirection().getOpposite())
                 && !entity.isOnGround();
    }

}
