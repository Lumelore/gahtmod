package net.lumelore.gahtmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class NoRemaindersShapelessRecipe extends ShapelessRecipe {

    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final List<Ingredient> ingredients;

    public static final MapCodec<NoRemaindersShapelessRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                    CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(r -> r.category),
                    ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(r -> r.result),
                    Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(r -> r.ingredients)
            ).apply(instance, NoRemaindersShapelessRecipe::new)
    );

    // Use StreamCodec for 26.2: See https://docs.fabricmc.net/develop/recipes/extending-vanilla-recipes
    public static final PacketCodec<RegistryByteBuf, NoRemaindersShapelessRecipe> STREAM_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            r -> r.group,

            CraftingRecipeCategory.PACKET_CODEC,
            r -> r.category,

            ItemStack.PACKET_CODEC,
            r -> r.result,

            Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()),
            r -> r.ingredients,

            NoRemaindersShapelessRecipe::new

    );

    public static final RecipeSerializer<NoRemaindersShapelessRecipe> SERIALIZER = new RecipeSerializer<>() {
        @Override
        public MapCodec<NoRemaindersShapelessRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, NoRemaindersShapelessRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    };

    public NoRemaindersShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, List<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        // I'm not taking my sneakers off! (and leaving them behind in the crafting table!)
        return DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
    }



}
