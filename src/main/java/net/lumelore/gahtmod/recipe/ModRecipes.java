package net.lumelore.gahtmod.recipe;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static void registerRecipes() {
        //v26.2 Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ExampleMod.id("smithing_enchanting"), EnchantingSmithingRecipe.SERIALIZER);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(GAHTMod.MOD_ID, "no_remainders_shapeless"), NoRemaindersShapelessRecipe.SERIALIZER );

        GAHTMod.LOGGER.info("Registering recipes for " + GAHTMod.MOD_ID + "...");
    }

}
