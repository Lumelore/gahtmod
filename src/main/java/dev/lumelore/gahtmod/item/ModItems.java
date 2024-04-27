package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.GAHTMod;
import dev.lumelore.gahtmod.item.special.EstrogenBlockerItem;
import dev.lumelore.gahtmod.item.special.TestosteroneBlockerItem;
import dev.lumelore.gahtmod.item.special.VialOfEstrogenItem;
import dev.lumelore.gahtmod.item.special.VialOfTestosteroneItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public abstract class ModItems {

    // Creative Tab Icon - Not Meant To Be Obtainable
    public static final Item GAHT_ICON = registerItem("gaht_icon", new Item(new FabricItemSettings().maxCount(0)));

    public static final Item ESTROGEN_PILL = registerItem("estrogen_pill", new Item(new FabricItemSettings().food(ModFoodComponents.ESTROGEN_ITEM)));
    public static final Item TESTOSTERONE_BLOCKER = registerItem("testosterone_blocker", new TestosteroneBlockerItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM)));
    public static final Item PROGESTERONE_CAPSULE = registerItem("progesterone_capsule", new TestosteroneBlockerItem(new FabricItemSettings().food(ModFoodComponents.ESTROGEN_ITEM)));
    public static final Item TESTOSTERONE_GEL_CAPSULE = registerItem("testosterone_gel_capsule", new Item(new FabricItemSettings().food(ModFoodComponents.TESTOSTERONE_ITEM)));
    public static final Item ESTROGEN_BLOCKER = registerItem("estrogen_blocker", new EstrogenBlockerItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM)));

    public static final Item VIAL_OF_ESTROGEN = registerItem("vial_of_estrogen", new VialOfEstrogenItem(new FabricItemSettings().food(ModFoodComponents.STRONG_ESTROGEN_ITEM)));
    public static final Item VIAL_OF_TESTOSTERONE = registerItem("vial_of_testosterone", new VialOfTestosteroneItem(new FabricItemSettings().food(ModFoodComponents.STRONG_TESTOSTERONE_ITEM)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(GAHTMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GAHTMod.LOGGER.info("Registering items for " + GAHTMod.MOD_ID + "...");
    }


}
