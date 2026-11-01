package net.lumelore.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerDataSaver implements net.lumelore.gahtmod.util.PlayerDataSaver {

    @Unique
    private int dashCooldown;

    @Unique
    private int absoluteDashCooldown;

    @Override
    public int getDashCooldown() {
        return dashCooldown;
    }

    @Override
    public int getAbsoluteDashCooldown() {
        return absoluteDashCooldown;
    }

    @Override
    public void setDashCooldown(int dashCooldown) {
        this.dashCooldown = dashCooldown;
    }

    @Override
    public void setAbsoluteDashCooldown(int absoluteDashCooldown) {
        this.absoluteDashCooldown = absoluteDashCooldown;
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void injectAddAdditionalSaveData(ValueOutput output, CallbackInfo info) {
        output.putInt("GAHTMod.DashCooldown", this.dashCooldown);
        output.putInt("GAHTMod.AbsoluteDashCooldown", this.absoluteDashCooldown);
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void injectReadAdditionalSaveData(ValueInput input, CallbackInfo info) {
        dashCooldown = input.getIntOr("GAHTMod.DashCooldown", 0);
        absoluteDashCooldown = input.getIntOr("GAHTMod.AbsoluteDashCooldown", 0);
    }


}
