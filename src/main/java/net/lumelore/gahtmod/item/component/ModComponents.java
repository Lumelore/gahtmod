package net.lumelore.gahtmod.item.component;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import static net.lumelore.gahtmod.GAHTMod.MOD_ID;

public class ModComponents {

    public static final DataComponentType<GenderEffects> GENDER_EFFECTS = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            GAHTMod.idOf("gender_effects"),
            DataComponentType.<GenderEffects>builder()
                    .persistent(GenderEffects.CODEC)
                    .networkSynchronized(GenderEffects.STREAM_CODEC)
                    .build()
    );

    public static void registerComponents() {
        GAHTMod.LOGGER.info("Registering components for " + MOD_ID + "...");
    }
}
