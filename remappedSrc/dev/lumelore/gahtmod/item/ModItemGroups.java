package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.GAHTMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class ModItemGroups {

    public static final ItemGroup GAHT_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(GAHTMod.MOD_ID, "gaht_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.gaht_group"))
                    .icon(() -> new ItemStack(ModItems.GAHT_ICON))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.ESTROGEN_PILL);
                        entries.add(ModItems.TESTOSTERONE_BLOCKER);
                        entries.add(ModItems.PROGESTERONE_CAPSULE);
                        entries.add(ModItems.TESTOSTERONE_GEL_CAPSULE);
                        entries.add(ModItems.ESTROGEN_BLOCKER);
                        entries.add(ModItems.ENBY_JUICE);
                        entries.add(ModItems.LARGE_ENBY_JUICE);
                        entries.add(ModItems.BOTTLE_OF_ESTROGEN);
                        entries.add(ModItems.BOTTLE_OF_TESTOSTERONE);
                        entries.add(ModItems.BOTTLE_OF_GENDERFLUID);
                        entries.add(ModItems.BOTTLE_OF_AGENDER);
                    })
                    .build());

    public static void registerItemGroups() {
        GAHTMod.LOGGER.info("Registering item groups for " + GAHTMod.MOD_ID + "...");
    }



}
