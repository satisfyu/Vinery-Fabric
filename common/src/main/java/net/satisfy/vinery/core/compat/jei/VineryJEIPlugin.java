package net.satisfy.vinery.core.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.satisfy.vinery.client.gui.handler.ApplePressGuiHandler;
import net.satisfy.vinery.core.compat.jei.category.FermentationBarrelCategory;
import net.satisfy.vinery.core.compat.jei.transfer.FermentationTransferInfo;
import net.satisfy.vinery.core.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import net.satisfy.vinery.core.registry.ScreenhandlerTypeRegistry;
import net.satisfy.vinery.core.util.VineryIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


@JeiPlugin
public class VineryJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FermentationBarrelCategory(registration.getJeiHelpers().getGuiHelper()));
      //  registration.addRecipeCategories(new ApplePressCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FermentationBarrelRecipe> fermentationBarrelRecipes = rm.getAllRecipesFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get());
        registration.addRecipes(FermentationBarrelCategory.FERMENTATION_BARREL, fermentationBarrelRecipes);

      //  List<ApplePressRecipe> applePressRecipes = rm.getAllRecipesFor(RecipeTypesRegistry.APPLE_PRESS_RECIPE_TYPE.get());
       // registration.addRecipes(ApplePressCategory.APPLE_PRESS, applePressRecipes);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new VineryIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        //registration.addRecipeTransferHandler(ApplePressGuiHandler.class, ScreenhandlerTypeRegistry.APPLE_PRESS_GUI_HANDLER.get(), ApplePressCategory.APPLE_PRESS,0, 1, 2, 36);
        registration.addRecipeTransferHandler(new FermentationTransferInfo());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ObjectRegistry.FERMENTATION_BARREL.get().asItem().getDefaultInstance(), FermentationBarrelCategory.FERMENTATION_BARREL);
      //  registration.addRecipeCatalyst(ObjectRegistry.APPLE_PRESS.get().asItem().getDefaultInstance(), ApplePressCategory.APPLE_PRESS);
    }

    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }

    private static void addItemStackInputSlot(IRecipeLayoutBuilder builder, int x, int y, ItemStack itemStack) {
        if (Minecraft.getInstance().level == null) return;
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStack(itemStack);
    }

    private static void addItemStackOutputSlot(IRecipeLayoutBuilder builder, int x, int y, ItemStack itemStack) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(itemStack);
    }

    public static void buildSlotsFromRecipe(IRecipeLayoutBuilder builder, FermentationBarrelRecipe recipe) {

        final int TOP_ROW_Y = 4;
        final int BOTTOM_ROW_Y = 45;

        final NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        final int ingredientCount = recipeIngredients.size();
        if (ingredientCount >= 1) VineryJEIPlugin.addSlot(builder, 41, BOTTOM_ROW_Y, recipeIngredients.get(0));
        if (ingredientCount >= 2) VineryJEIPlugin.addSlot(builder, 59, BOTTOM_ROW_Y, recipeIngredients.get(1));
        if (ingredientCount >= 3) VineryJEIPlugin.addSlot(builder, 77, BOTTOM_ROW_Y, recipeIngredients.get(2));

        VineryJEIPlugin.addItemStackInputSlot(builder, 97, BOTTOM_ROW_Y, ObjectRegistry.WINE_BOTTLE.get().getDefaultInstance());

        assert Minecraft.getInstance().level != null;
        VineryJEIPlugin.addItemStackOutputSlot(builder, 77, TOP_ROW_Y, recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}


