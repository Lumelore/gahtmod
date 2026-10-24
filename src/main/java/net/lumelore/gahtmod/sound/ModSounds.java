package net.lumelore.gahtmod.sound;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static net.lumelore.gahtmod.GAHTMod.MOD_ID;

public abstract class ModSounds {

    public static final RegistryEntry<SoundEvent> REVERSE_DRINKING = registerSoundEventReference("reverse_drinking");
    public static final SoundEvent DASH = registerSoundEvent("dash");
    public static final SoundEvent DASH_RECHARGE = registerSoundEvent("dash_recharge");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerSoundEventReference(String name) {
        Identifier id = Identifier.of(MOD_ID, name);
        return Registry.registerReference(Registries.SOUND_EVENT, Identifier.of(MOD_ID, name), SoundEvent.of(id));
    }

    public static void registerSounds() {
        GAHTMod.LOGGER.info("Registering sounds for " + MOD_ID + "...");
    }

}
