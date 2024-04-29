package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.GAHTMod;
import dev.lumelore.gahtmod.item.special.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public abstract class ModItems {

    // Creative Tab Icon - Not Meant To Be Obtainable
    public static final Item GAHT_ICON = registerItem("gaht_icon", new Item(new FabricItemSettings().maxCount(0)));

    // Estrogen Items
    public static final Item ESTROGEN_PILL = registerItem("estrogen_pill", new EstrogenPillItem(new FabricItemSettings().food(ModFoodComponents.ESTROGEN_ITEM)));
    public static final Item TESTOSTERONE_BLOCKER = registerItem("testosterone_blocker", new TestosteroneBlockerItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM)));
    public static final Item PROGESTERONE_CAPSULE = registerItem("progesterone_capsule", new ProgesteroneCapsuleItem(new FabricItemSettings().food(ModFoodComponents.ESTROGEN_ITEM)));
    // Testosterone Items
    public static final Item TESTOSTERONE_GEL_CAPSULE = registerItem("testosterone_gel_capsule", new TestosteroneGelCapsuleItem(new FabricItemSettings().food(ModFoodComponents.TESTOSTERONE_ITEM)));
    public static final Item ESTROGEN_BLOCKER = registerItem("estrogen_blocker", new EstrogenBlockerItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM)));
    // Enby Items
    public static final Item ENBY_JUICE = registerItem("enby_juice", new EnbyJuiceItem(new FabricItemSettings().food(ModFoodComponents.ENBY_ITEM)));
    public static final Item LARGE_ENBY_JUICE = registerItem("large_enby_juice", new LargeEnbyJuiceItem(new FabricItemSettings().food(ModFoodComponents.STRONG_ENBY_ITEM)));

    // Strong E + T Items
    public static final Item BOTTLE_OF_ESTROGEN = registerItem("bottle_of_estrogen", new BottleOfEstrogenItem(new FabricItemSettings().food(ModFoodComponents.STRONG_ESTROGEN_ITEM)));
    public static final Item BOTTLE_OF_TESTOSTERONE = registerItem("bottle_of_testosterone", new BottleOfTestosteroneItem(new FabricItemSettings().food(ModFoodComponents.STRONG_TESTOSTERONE_ITEM)));

    // bottle of genderfluid, agender, + gender Items
    public static final Item BOTTLE_OF_GENDERFLUID = registerItem("bottle_of_genderfluid", new BottleOfGenderfluidItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM)));
    public static final Item BOTTLE_OF_AGENDER = registerItem("bottle_of_agender", new BottleOfAgenderItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM).maxCount(1)));
    public static final Item BOTTLE_OF_GENDER = registerItem("bottle_of_gender", new BottleOfGenderItem(new FabricItemSettings().food(ModFoodComponents.NONE_EFFECT_ITEM).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(GAHTMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GAHTMod.LOGGER.info("Registering items for " + GAHTMod.MOD_ID + "...");
    }


}
