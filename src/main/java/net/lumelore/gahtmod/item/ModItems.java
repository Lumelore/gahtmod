package net.lumelore.gahtmod.item;

import net.lumelore.gahtmod.GAHTMod;
import net.lumelore.gahtmod.item.component.ModComponents;
import net.lumelore.gahtmod.item.special.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public abstract class ModItems {

    // Creative Tab Icon - Not Meant To Be Obtainable
    public static final Item GAHT_ICON = registerItem("gaht_icon", Item::new, new Item.Properties().stacksTo(1));

    /* ============== *
     * Estrogen Items *
     * ============== */

    public static final Item BOTTLE_OF_OIL_SOLUTION = registerItem("bottle_of_oil_solution", Item::new, new Item.Properties()
    );

    public static final Item ESTROGEN_PILL = registerItem("estrogen_pill", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.ESTROGEN_PILL)
    );

    public static final Item BOTTLE_OF_ESTROGEN = registerItem("bottle_of_estrogen", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE, GAHTComponents.THICK_DRINK)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.BOTTLE_OF_ESTROGEN)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item TESTOSTERONE_BLOCKER = registerItem("testosterone_blocker", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.TESTOSTERONE_BLOCKER)
    );

    public static final Item PROGESTERONE_CAPSULE = registerItem("progesterone_capsule", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.PROGESTERONE_CAPSULE)
    );

    /* ================== *
     * Testosterone Items *
     * ================== */

    public static final Item TESTOSTERONE_GEL_CAPSULE = registerItem("testosterone_gel_capsule", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.TESTOSTERONE_GEL_CAPSULE)
    );

    public static final Item BOTTLE_OF_TESTOSTERONE = registerItem("bottle_of_testosterone", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE, GAHTComponents.THICK_DRINK)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.BOTTLE_OF_TESTOSTERONE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item ESTROGEN_BLOCKER = registerItem("estrogen_blocker", GenderConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.ESTROGEN_BLOCKER)
    );

    /* ========== *
     * Enby Items *
     * ========== */

    public static final Item ENBY_JUICE = registerItem("enby_juice", GenderConsumableItem::new, new Item.Properties()
            .food(Foods.HONEY_BOTTLE, GAHTComponents.THICK_DRINK)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.ENBY_JUICE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item LARGE_ENBY_JUICE = registerItem("large_enby_juice", LargeBottleEffectItem::new, new Item.Properties()
            .food(GAHTComponents.LARGE_HONEY, GAHTComponents.THICK_DRINK)
            .component(ModComponents.GENDER_EFFECTS, GAHTComponents.LARGE_ENBY_JUICE)
    );

    // bottle of genderfluid, agender, + gender Items

    public static final Item BOTTLE_OF_AGENDER = registerItem("bottle_of_agender", BottleOfAgenderItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE, GAHTComponents.REVERSE_DRINK)
    );

    public static final Item BOTTLE_OF_GENDERFLUID = registerItem("bottle_of_genderfluid", BottleOfGenderfluidItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE, GAHTComponents.GENERIC_DRINK)
            .usingConvertsTo(ModItems.BOTTLE_OF_AGENDER)
    );

    public static final Item BOTTLE_OF_GENDER = registerItem("bottle_of_gender", BottleOfGenderItem::new, new Item.Properties()
            .food(GAHTComponents.GENERIC_ALWAYS_EDIBLE, GAHTComponents.GENERIC_DRINK)
            .usingConvertsTo(ModItems.BOTTLE_OF_AGENDER)
    );

    /* ==== *
     * Util *
     * ==== */

    private static Item registerItem(String name, Function<Item.Properties, Item> itemConstructor, Item.Properties properties) {
        ResourceKey<Item> rKey = ResourceKey.create(Registries.ITEM, GAHTMod.idOf(name));
        Item item = itemConstructor.apply(properties.setId(rKey));
        Registry.register(BuiltInRegistries.ITEM, rKey, item);
        return item;
    }
    public static void registerModItems() {
        GAHTMod.LOGGER.info("Registering items for " + GAHTMod.MOD_ID + "...");
    }


}
