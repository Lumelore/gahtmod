package dev.lumelore.gahtmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
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
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            // Apply effect if player is walking into a block that they can climb
            // and also isn't in creative mode
            if (entity.horizontalCollision && canClimb((PlayerEntity) entity) && !((PlayerEntity) entity).getAbilities().flying) {
                entity.setVelocity(entity.getVelocity().getX(), 0.2, entity.getVelocity().getZ());

            }
            // Hold entity in place vertically if sneaking while climbing
            else if (canClimb((PlayerEntity) entity) && entity.isSneaking() && !((PlayerEntity) entity).getAbilities().flying) {
                entity.setVelocity(entity.getVelocity().getX(), 0, entity.getVelocity().getZ());
            }
        }
        return true;
    }

    private boolean canClimb(PlayerEntity entity) {
        // Find the adjacent block at head height that is solid.
        Optional<BlockPos> closetBlock = BlockPos.findClosest(entity.getBlockPos().up(), 1, 0, b->entity.getWorld().getBlockState(b).isSolid());
        Optional<BlockPos> closetBlockUnder = BlockPos.findClosest(entity.getBlockPos(), 1, 0, b->entity.getWorld().getBlockState(b).isSolid());
        // If it has been found, player can climb
        if (!closetBlock.equals(Optional.empty())) {
            return true;
        }
        // Can also climb if block underneath closetBlock is solid, but player is not on the ground.
        else if (closetBlockUnder.isPresent() && entity.getWorld().getBlockState(closetBlockUnder.get().down()).isSolid() && !entity.isOnGround()) {
            return true;
        }
        return false;
    }

}
