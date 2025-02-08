package net.int_str.bean_machine.datagen;

import com.google.gson.JsonElement;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class PackRecipeOutputAdapter implements RecipeOutput {
    private final PackOutput packOutput;
    private final HolderLookup.Provider registry;

    public PackRecipeOutputAdapter(PackOutput packOutput, HolderLookup.Provider registry) {
        this.packOutput = packOutput;
        this.registry = registry;
    }

    @Override
    public void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe,
                       @Nullable ResourceLocation advancementId,
                       @Nullable JsonElement advancement) {
        // Create the path for the recipe file.
        // Here we assume that recipes should be stored under the "recipes" folder in the DATA_PACK.
        PackOutput.PathProvider pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        Path outputFile = pathProvider.json(id.location());

        // Now serialize your recipe (and advancement, if any) to JSON and write to the file.
        // This is a simplified example; in practice you might use your mod's or vanilla's JSON serializers.
        // For example:
        //
        // JsonObject recipeJson = recipe.toJson(); // or use your own conversion method
        // Files.createDirectories(outputFile.getParent());
        // Files.writeString(outputFile, recipeJson.toString(), StandardCharsets.UTF_8);
        //
        // Likewise, if advancement is non-null, write it to its proper location.
    }

    @Override
    public HolderLookup.Provider registry() {
        return registry;
    }

    @Override
    public Advancement.Builder advancement() {
        // Return a new builder for advancements.
        return Advancement.Builder.advancement();
    }

    @Override
    public void includeRootAdvancement() {
        // Implement if you need to include a "root" advancement for recipes.
        // This might be a no-op if your mod does not require it.
    }
}
