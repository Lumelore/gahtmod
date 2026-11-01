package net.lumelore.gahtmod.item;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Registry;

public abstract class ModItemGroups {

    public static final CreativeModeTab GAHT_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), GAHTMod.idOf("gaht_group")),
            FabricCreativeModeTab.builder()
                    .title(Component.translatable("itemgroup.gaht_group"))
                    .icon(()-> new ItemStack(ModItems.GAHT_ICON))
                    .displayItems((parameters, out) -> {
                        out.accept(ModItems.ESTROGEN_PILL);
                        out.accept(ModItems.TESTOSTERONE_BLOCKER);
                        out.accept(ModItems.PROGESTERONE_CAPSULE);
                        out.accept(ModItems.TESTOSTERONE_GEL_CAPSULE);
                        out.accept(ModItems.ESTROGEN_BLOCKER);
                        out.accept(ModItems.ENBY_JUICE);
                        out.accept(ModItems.LARGE_ENBY_JUICE);
                        out.accept(ModItems.BOTTLE_OF_ESTROGEN);
                        out.accept(ModItems.BOTTLE_OF_TESTOSTERONE);
                        out.accept(ModItems.BOTTLE_OF_OIL_SOLUTION);
                        out.accept(ModItems.BOTTLE_OF_GENDERFLUID);
                        out.accept(ModItems.BOTTLE_OF_AGENDER);
                    })
                    .build()
            );

    public static void registerItemGroups() {
        GAHTMod.LOGGER.info("Registering item groups for " + GAHTMod.MOD_ID + "...");
    }

}
