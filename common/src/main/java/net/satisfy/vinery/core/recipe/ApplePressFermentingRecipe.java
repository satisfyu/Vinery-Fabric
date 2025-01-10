package net.satisfy.vinery.core.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

public class ApplePressFermentingRecipe implements Recipe<Container> {
    private final ResourceLocation identifier;
    public final Ingredient input;
    private final ItemStack output;
    private final boolean requiresBottle;
    public final int craftingTime;

    public ApplePressFermentingRecipe(ResourceLocation identifier, Ingredient input, ItemStack output, boolean requiresBottle, int craftingTime) {
        this.identifier = identifier;
        this.input = input;
        this.output = output;
        this.requiresBottle = requiresBottle;
        this.craftingTime = craftingTime;
    }

    public boolean requiresBottle() {
        return requiresBottle;
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        return input.test(inventory.getItem(1));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(input);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.APPLE_PRESS_FERMENTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypesRegistry.APPLE_PRESS_FERMENTING_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ApplePressFermentingRecipe> {
        @Override
        public @NotNull ApplePressFermentingRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            if (ingredient.isEmpty()) {
                throw new JsonParseException("No ingredients for recipe: " + id);
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            boolean requiresBottle = GsonHelper.getAsBoolean(json, "requires_bottle", false);
            int craftingTime = GsonHelper.getAsInt(json, "crafting_time", 200);
            return new ApplePressFermentingRecipe(id, ingredient, output, requiresBottle, craftingTime);
        }

        @Override
        public @NotNull ApplePressFermentingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            boolean requiresBottle = buf.readBoolean();
            int craftingTime = buf.readInt();
            return new ApplePressFermentingRecipe(id, input, output, requiresBottle, craftingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ApplePressFermentingRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeBoolean(recipe.requiresBottle);
            buf.writeInt(recipe.craftingTime);
        }
    }
}
