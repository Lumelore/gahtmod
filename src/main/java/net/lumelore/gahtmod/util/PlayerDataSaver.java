package net.lumelore.gahtmod.util;

public interface PlayerDataSaver {
    int getDashCooldown();

    int getAbsoluteDashCooldown();

    void setDashCooldown(int cooldown);

    void setAbsoluteDashCooldown(int cooldown);
}
