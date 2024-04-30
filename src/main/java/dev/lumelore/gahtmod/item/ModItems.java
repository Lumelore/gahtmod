package dev.lumelore.gahtmod.item;

import dev.lumelore.gahtmod.GAHTMod;
import dev.lumelore.gahtmod.effect.ModEffects;
import dev.lumelore.gahtmod.item.special.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public abstract class ModItems {

    // Creative Tab Icon - Not Meant To Be Obtainable
    public static final Item GAHT_ICON = registerItem("gaht_icon", new Item(new Item.Settings().maxCount(0)));

    /* ============== *
     * Estrogen Items *
     * ============== */

    public static final Item ESTROGEN_PILL = registerItem("estrogen_pill", new CrunchyPotionItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
            List.of(new StatusEffectInstance(ModEffects.GIRL_POWER, 9600, 0),
                    new StatusEffectInstance(StatusEffects.REGENERATION, 1200, 0))))));

    public static final Item BOTTLE_OF_ESTROGEN = registerItem("bottle_of_estrogen", new ThickPotionItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.GIRL_POWER, 28800, 0),
                            new StatusEffectInstance(StatusEffects.REGENERATION, 2400, 1))))));

    public static final Item TESTOSTERONE_BLOCKER = registerItem("testosterone_blocker", new EffectRemoverItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.BOY_POWER),
                            new StatusEffectInstance(StatusEffects.SLOWNESS),
                            new StatusEffectInstance(StatusEffects.WEAKNESS),
                            new StatusEffectInstance(StatusEffects.WITHER))))));

    public static final Item PROGESTERONE_CAPSULE = registerItem("progesterone_capsule", new ProgesteroneCapsuleItem(new Item.Settings()
            .food(ModFoodComponents.ESTROGEN_ITEM)
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.BOY_POWER),
                            new StatusEffectInstance(StatusEffects.SLOWNESS),
                            new StatusEffectInstance(StatusEffects.WEAKNESS),
                            new StatusEffectInstance(StatusEffects.WITHER))))));

    /* ================== *
     * Testosterone Items *
     * ================== */

    public static final Item TESTOSTERONE_GEL_CAPSULE = registerItem("testosterone_gel_capsule", new CrunchyPotionItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.BOY_POWER, 9600, 0),
                            new StatusEffectInstance(StatusEffects.STRENGTH, 1200, 0))))));

    public static final Item BOTTLE_OF_TESTOSTERONE = registerItem("bottle_of_testosterone", new ThickPotionItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.BOY_POWER, 28800, 0),
                            new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 1))))));

    public static final Item ESTROGEN_BLOCKER = registerItem("estrogen_blocker", new EffectRemoverItem(new Item.Settings()
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.GIRL_POWER),
                            new StatusEffectInstance(StatusEffects.SLOWNESS),
                            new StatusEffectInstance(StatusEffects.WEAKNESS),
                            new StatusEffectInstance(StatusEffects.WITHER))))));

    /* ========== *
     * Enby Items *
     * ========== */

    public static final Item ENBY_JUICE = registerItem("enby_juice", new ThickPotionItem(new Item.Settings()
            .food(FoodComponents.HONEY_BOTTLE)
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.ENBY_POWER, 9600, 0),
                            new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0))))));

    public static final Item LARGE_ENBY_JUICE = registerItem("large_enby_juice", new BigThickPotionItem(new Item.Settings()
            .component(DataComponentTypes.FOOD, ModFoodComponents.BIG_HONEY)
            .component(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.empty(),
                    List.of(new StatusEffectInstance(ModEffects.ENBY_POWER, 28800, 0),
                            new StatusEffectInstance(StatusEffects.RESISTANCE, 2400, 1))))));

    // bottle of genderfluid, agender, + gender Items
    public static final Item BOTTLE_OF_GENDERFLUID = registerItem("bottle_of_genderfluid", new BottleOfGenderfluidItem(new Item.Settings().food(ModFoodComponents.NONE_EFFECT_ITEM)));
    public static final Item BOTTLE_OF_AGENDER = registerItem("bottle_of_agender", new BottleOfAgenderItem(new Item.Settings()));
    public static final Item BOTTLE_OF_GENDER = registerItem("bottle_of_gender", new BottleOfGenderItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(GAHTMod.MOD_ID, name), item);
    }

    private static RegistryEntry<Potion> register(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, new Identifier(GAHTMod.MOD_ID, name), potion);
    }

    public static void registerModItems() {
        GAHTMod.LOGGER.info("Registering items for " + GAHTMod.MOD_ID + "...");
    }


}
