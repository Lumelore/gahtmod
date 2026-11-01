package net.lumelore.gahtmod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class NoRemaindersShapelessRecipe extends ShapelessRecipe {

    final CommonInfo group;
    final CraftingBookInfo bookInfo;
    final ItemStackTemplate result;
    final List<Ingredient> ingredients;


    public static final MapCodec<NoRemaindersShapelessRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
                    CraftingBookInfo.MAP_CODEC.forGetter(r -> r.bookInfo),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
                    Ingredient.CODEC.listOf(1,9).fieldOf("ingredients").forGetter(r -> r.ingredients)
            ).apply(instance, NoRemaindersShapelessRecipe::new)
    );

    // Use StreamCodec for 26.2: See https://docs.fabricmc.net/develop/recipes/extending-vanilla-recipes
    public static final StreamCodec<RegistryFriendlyByteBuf, NoRemaindersShapelessRecipe> STREAM_CODEC = StreamCodec.composite(
            CommonInfo.STREAM_CODEC,
            r -> r.commonInfo,
            CraftingBookInfo.STREAM_CODEC,
            r -> r.bookInfo,
            ItemStackTemplate.STREAM_CODEC,
            r -> r.result,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
            r -> r.ingredients,

            NoRemaindersShapelessRecipe::new
    );

    public static final RecipeSerializer<NoRemaindersShapelessRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);


    public NoRemaindersShapelessRecipe(CommonInfo commonInfo, CraftingBookInfo craftingInfo, ItemStackTemplate result, List<Ingredient> ingredients) {
        super(commonInfo, craftingInfo, result, ingredients);
        this.group = commonInfo;
        this.bookInfo = craftingInfo;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public @NonNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        // I'm not taking my sneakers off! (and leaving them behind in the crafting table!)
        return NonNullList.withSize(input.size(), ItemStack.EMPTY);
    }

}
