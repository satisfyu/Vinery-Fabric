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
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelRecipe implements Recipe<FermentationBarrelBlockEntity> {
    private final ResourceLocation identifier;
    private final NonNullList<Ingredient> inputs;
    private final String juiceType;
    private final int juiceAmount;
    private final ItemStack output;
    private final boolean wineBottleRequired;
    private final int craftingTime;

    public FermentationBarrelRecipe(ResourceLocation identifier, NonNullList<Ingredient> inputs, String juiceType, int juiceAmount, ItemStack output, boolean wineBottleRequired, int craftingTime) {
        this.identifier = identifier;
        this.inputs = inputs;
        this.juiceType = juiceType;
        this.juiceAmount = juiceAmount;
        this.output = output;
        this.wineBottleRequired = wineBottleRequired;
        this.craftingTime = craftingTime;
    }

    public String getJuiceType() {
        return juiceType;
    }

    public int getJuiceAmount() {
        return juiceAmount;
    }

    public boolean isWineBottleRequired() {
        return wineBottleRequired;
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    @Override
    public boolean matches(FermentationBarrelBlockEntity blockEntity, Level world) {
        if (this.juiceAmount > 0) {
            if (blockEntity.getFluidLevel() < this.juiceAmount) {
                return false;
            }

            if (!this.juiceType.equals(blockEntity.getJuiceType())) {
                return false;
            }
        }

        if (this.wineBottleRequired) {
            ItemStack wineBottle = blockEntity.getItem(FermentationBarrelBlockEntity.WINE_BOTTLE_SLOT);
            if (wineBottle.isEmpty() || !wineBottle.is(ObjectRegistry.WINE_BOTTLE.get())) {
                return false;
            }
        }

        StackedContents recipeMatcher = new StackedContents();
        int matchingStacks = 0;

        for (int i = 1; i < 4; ++i) {
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
            NonNullList<Ingredient> ingredients = NonNullList.create();
            if (json.has("ingredients")) {
                var jsonArray = GsonHelper.getAsJsonArray(json, "ingredients");
                for (int i = 0; i < jsonArray.size() && i < 3; i++) {
                    ingredients.add(Ingredient.fromJson(jsonArray.get(i)));
                }
            }
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Fermentation Barrel recipe");
            }

            String juiceType = "white_general";
            int juiceAmount = 10;
            if (json.has("juice")) {
                JsonObject juiceObject = GsonHelper.getAsJsonObject(json, "juice");
                juiceType = GsonHelper.getAsString(juiceObject, "type", "white_general");
                juiceAmount = GsonHelper.getAsInt(juiceObject, "amount", 10);
            }

            boolean wineBottleRequired = false;
            if (json.has("wine_bottle")) {
                JsonObject wineBottleObject = GsonHelper.getAsJsonObject(json, "wine_bottle");
                wineBottleRequired = GsonHelper.getAsBoolean(wineBottleObject, "required", false);
            }

            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            int craftingTime = GsonHelper.getAsInt(json, "crafting_time", 200);

            return new FermentationBarrelRecipe(id, ingredients, juiceType, juiceAmount, result, wineBottleRequired, craftingTime);
        }

        @Override
        public @NotNull FermentationBarrelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < ingredientCount && i < 3; i++) {
                ingredients.add(Ingredient.fromNetwork(buf));
            }
            String juiceType = buf.readUtf();
            int juiceAmount = buf.readVarInt();
            boolean wineBottleRequired = buf.readBoolean();
            ItemStack result = buf.readItem();
            int craftingTime = buf.readInt();
            return new FermentationBarrelRecipe(id, ingredients, juiceType, juiceAmount, result, wineBottleRequired, craftingTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FermentationBarrelRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.toNetwork(buf);
            }
            buf.writeUtf(recipe.juiceType);
            buf.writeVarInt(recipe.juiceAmount);
            buf.writeBoolean(recipe.wineBottleRequired);
            buf.writeItem(recipe.output);
            buf.writeInt(recipe.craftingTime);
        }
    }
}
