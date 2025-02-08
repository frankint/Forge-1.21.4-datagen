package net.int_str.bean_machine.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import java.util.concurrent.CompletableFuture;

public class ModRecipeDataProvider extends ModRecipeProvider.Runner {

    public ModRecipeDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        // Return an instance of your actual recipe provider that implements buildRecipes.
        // The ModRecipeProvider must extend RecipeProvider.
        return new ModRecipeProvider(registries, output);
    }
}