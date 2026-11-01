package net.lumelore.gahtmod.sound;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import static net.lumelore.gahtmod.GAHTMod.MOD_ID;

public abstract class ModSounds {


    public static final Holder.Reference<SoundEvent> REVERSE_DRINKING = registerForHolder("reverse_drinking");
    public static final SoundEvent DASH = registerSoundEvent("dash");
    public static final SoundEvent DASH_RECHARGE = registerSoundEvent("dash_recharge");

    private static SoundEvent registerSoundEvent(String path) {
        Identifier id = GAHTMod.idOf(path);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String path) {
        Identifier id = GAHTMod.idOf(path);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSounds() {
        GAHTMod.LOGGER.info("Registering sounds for " + MOD_ID + "...");
    }

}
