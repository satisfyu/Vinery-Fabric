package net.satisfy.vinery.core.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.core.block.entity.FermentationBarrelBlockEntity;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import net.satisfy.vinery.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelRecipe implements Recipe<FermentationBarrelBlockEntity> {
    private final ResourceLocation identifier;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final int juiceAmount;

    public FermentationBarrelRecipe(ResourceLocation identifier, NonNullList<Ingredient> inputs, int juiceAmount, ItemStack output) {
        this.identifier = identifier;
        this.inputs = inputs;
        this.juiceAmount = juiceAmount;
        this.output = output;
    }

    public int getJuiceAmount() {
        return juiceAmount;
    }

    @Override
    public boolean matches(FermentationBarrelBlockEntity blockEntity, Level world) {
        if (blockEntity.getFluidLevel() < this.juiceAmount) {
            return false;
        }

        StackedContents recipeMatcher = new StackedContents();
        int matchingStacks = 0;

        for (int i = 1; i < 5; ++i) {
            ItemStack itemStack = blockEntity.getItem(i);
            if (!itemStack.isEmpty()) {
                ++matchingStacks;
                recipeMatcher.accountStack(itemStack, 1);
            }
        }

        return matchingStacks == this.inputs.size() && recipeMatcher.canCraft(this, null);
    }

    @Override
    public @NotNull ItemStack assemble(FermentationBarrelBlockEntity blockEntity, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
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
        return RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipe> {

        @Override
        public @NotNull FermentationBarrelRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> ingredients = GeneralUtil.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel recipe");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Fermentation Barrel recipe");
            }

            int juiceAmount = 10;
            if (json.has("juice")) {
                JsonObject juiceObject = GsonHelper.getAsJsonObject(json, "juice");
                juiceAmount = GsonHelper.getAsInt(juiceObject, "amount", 10);
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new FermentationBarrelRecipe(id, ingredients, juiceAmount, result);
        }

        @Override
        public @NotNull FermentationBarrelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }
            int juiceAmount = buf.readVarInt(); // Read juice amount
            ItemStack result = buf.readItem();
            return new FermentationBarrelRecipe(id, ingredients, juiceAmount, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FermentationBarrelRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.toNetwork(buf);
            }
            buf.writeVarInt(recipe.juiceAmount);
            buf.writeItem(recipe.output);
        }
    }
}
