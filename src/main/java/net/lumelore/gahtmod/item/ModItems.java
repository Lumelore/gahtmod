package net.lumelore.gahtmod.item;

import net.lumelore.gahtmod.GAHTMod;
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
    public static final Item GAHT_ICON = registerItem("gaht_icon", Item::new, new Item.Properties().stacksTo(0));

    /* ============== *
     * Estrogen Items *
     * ============== */

    public static final Item BOTTLE_OF_OIL_SOLUTION = registerItem("bottle_of_oil_solution", Item::new, new Item.Properties()
    );

    public static final Item ESTROGEN_PILL = registerItem("estrogen_pill", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.SMALL_CRUNCHY_ESTROGEN_CONSUMABLE)
    );

    public static final Item BOTTLE_OF_ESTROGEN = registerItem("bottle_of_estrogen", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.LARGE_ESTROGEN_CONSUMABLE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item TESTOSTERONE_BLOCKER = registerItem("testosterone_blocker", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.TESTOSTERONE_BLOCKER_CONSUMABLE)
    );

    public static final Item PROGESTERONE_CAPSULE = registerItem("progesterone_capsule", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.PROGESTERONE_CAPSULE_CONSUMABLE)
    );

    /* ================== *
     * Testosterone Items *
     * ================== */

    public static final Item TESTOSTERONE_GEL_CAPSULE = registerItem("testosterone_gel_capsule", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.SMALL_CRUNCHY_TESTOSTERONE_CONSUMABLE)
    );

    public static final Item BOTTLE_OF_TESTOSTERONE = registerItem("bottle_of_testosterone", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.LARGE_TESTOSTERONE_CONSUMABLE)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item ESTROGEN_BLOCKER = registerItem("estrogen_blocker", TooltippedConsumableItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.ESTROGEN_BLOCKER_CONSUMABLE)
    );

    /* ========== *
     * Enby Items *
     * ========== */

    public static final Item ENBY_JUICE = registerItem("enby_juice", TooltippedConsumableItem::new, new Item.Properties()
            .food(Foods.HONEY_BOTTLE, GAHTComponents.SMALL_ENBY)
            .usingConvertsTo(Items.GLASS_BOTTLE)
    );

    public static final Item LARGE_ENBY_JUICE = registerItem("large_enby_juice", LargeBottleEffectItem::new, new Item.Properties()
            .food(GAHTComponents.BIG_HONEY, GAHTComponents.LARGE_ENBY)
    );

    // bottle of genderfluid, agender, + gender Items

    public static final Item BOTTLE_OF_AGENDER = registerItem("bottle_of_agender", BottleOfAgenderItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.REVERSE_DRINK)
    );

    public static final Item BOTTLE_OF_GENDERFLUID = registerItem("bottle_of_genderfluid", BottleOfGenderfluidItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.NORMAL_DRINK)
            .usingConvertsTo(ModItems.BOTTLE_OF_AGENDER)
    );

    public static final Item BOTTLE_OF_GENDER = registerItem("bottle_of_gender", BottleOfGenderItem::new, new Item.Properties()
            .food(GAHTComponents.ALWAYS_EDIBLE, GAHTComponents.NORMAL_DRINK)
            .usingConvertsTo(ModItems.BOTTLE_OF_AGENDER)
    );

    /* ==== *
     * Util *
     * ==== */

    private static Item registerItem(String name, Function<Item.Properties, Item> itemConstructor, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM, ResourceKey.create(Registries.ITEM, GAHTMod.idOf(name)),
                itemConstructor.apply(properties));
    }

    public static void registerModItems() {
        GAHTMod.LOGGER.info("Registering items for " + GAHTMod.MOD_ID + "...");
    }


}
