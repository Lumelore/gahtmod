package net.lumelore.gahtmod.recipe;

import net.lumelore.gahtmod.GAHTMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModRecipes {

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, GAHTMod.idOf("no_remainders_shapeless"), NoRemaindersShapelessRecipe.SERIALIZER );

        GAHTMod.LOGGER.info("Registering recipes for " + GAHTMod.MOD_ID + "...");
    }

}
