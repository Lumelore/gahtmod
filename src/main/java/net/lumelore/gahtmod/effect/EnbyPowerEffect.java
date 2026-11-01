package net.lumelore.gahtmod.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Predicate;

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

    private boolean canClimb(Player player, ServerLevel world) {
        Optional<BlockPos> closetBlock = findClosest(player, 1, 1, b->player.level().getBlockState(b).isFaceSturdy(world, b, player.getMotionDirection().getOpposite()));
        Optional<BlockPos> closetBlockUnder = findClosest(player, 1, 0, b->player.level().getBlockState(b).isFaceSturdy(world, b, player.getMotionDirection().getOpposite()));
        // If it has been found, player can climb
        if (!closetBlock.equals(Optional.empty())) {
            return true;
        }

        // Can also climb if block underneath closetBlock is solid, but player is not on the ground.
        else return closetBlockUnder.isPresent() && world.getBlockState(closetBlockUnder.get().below())
                .isFaceSturdy(world, closetBlockUnder.get(), player.getMotionDirection().getOpposite())
                 && !player.onGround();
    }

    /**
     * Search in a square centered on the entity
     * @param entity The entity to center the search around
     * @param range The search radius
     * @param verticalOffset 0 to search at player feet. 1 to search at player head.
     * @param condition The condition the block should match
     * @return The block matching the condition or an empty
     */
    private static Optional<BlockPos> findClosest(LivingEntity entity, int range, int verticalOffset, Predicate<BlockPos> condition) {
        // A method like this used to exist in vanilla. Maybe it still does, but I can't find it,
        // so I had to recreate the functionality I wanted myself.
        Vec3 root = entity.position().add(0, verticalOffset, 0);

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                BlockPos toCheck = BlockPos.containing(root.x() + x, root.y(), root.z() + z);
                if (condition.test(toCheck)) {
                    return Optional.of(toCheck);
                }
            }
        }
        return Optional.empty();
    }

}
