package net.int_str.bean_machine.datagen;

import net.int_str.bean_machine.BeanMachine;
import net.int_str.bean_machine.block.ModBlocks;
import net.int_str.bean_machine.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements DataProvider {
    protected RecipeOutput pOutput;
    protected ModRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput) {
        super(pRegistries, pOutput);
    }

    @Override
    protected void buildRecipes() {
        this.output.includeRootAdvancement();
        this.generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));

        this.shaped(RecipeCategory.MISC, ModBlocks.BEAN_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.BEAN.get())
                .unlockedBy(getHasName(ModItems.BEAN.get()), has(ModItems.BEAN.get()))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, ModBlocks.COMPRESSED_BEAN_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.BEAN_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.BEAN_BLOCK.get()), has(ModBlocks.BEAN_BLOCK.get()))
                .save(this.output);

        this.shaped(RecipeCategory.TOOLS, ModItems.BEAN_WAND.get())
                .pattern("  Y")
                .pattern(" X ")
                .pattern("#  ")
                .define('Y', ModItems.BEAN.get())
                .define('X', Items.NETHER_STAR)
                .define('#', Items.STICK)
                .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.NETHER_STAR))
                .unlockedBy(getHasName(ModItems.BEAN.get()), has(ModItems.BEAN.get()))
                .save(this.output);

        this.shapeless(RecipeCategory.MISC, ModBlocks.BEAN_BLOCK.get(), 9)
                .requires(ModBlocks.COMPRESSED_BEAN_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.COMPRESSED_BEAN_BLOCK.get()), has(ModBlocks.COMPRESSED_BEAN_BLOCK.get()))
                .save(this.output, BeanMachine.MODID + ":bean_block_from_compressed_bean_block");

        this.shapeless(RecipeCategory.MISC, ModItems.BEAN.get(), 9)
                .requires(ModBlocks.BEAN_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.BEAN_BLOCK.get()), has(ModBlocks.BEAN_BLOCK.get()))
                .save(this.output, BeanMachine.MODID + ":bean_from_bean_block");

        this.oreSmelting(List.of(ModItems.UNPROCESSED_BEAN.get(), ModBlocks.DEEPSLATE_BEAN_ORE.get(), ModBlocks.BEAN_ORE.get()), RecipeCategory.MISC, ModItems.BEAN.get(), 0.25f, 200, "bean");
        this.oreBlasting(List.of(ModItems.UNPROCESSED_BEAN.get(), ModBlocks.DEEPSLATE_BEAN_ORE.get(), ModBlocks.BEAN_ORE.get()), RecipeCategory.MISC, ModItems.BEAN.get(), 0.25f, 200, "bean");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.BEAN.get()), RecipeCategory.FOOD, ModItems.COOKED_BEAN.get(), 0.35f, 200)
                .unlockedBy(getHasName(ModItems.BEAN.get()), has(ModItems.BEAN.get()))
                .save(this.output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COOKED_BEAN.get()), RecipeCategory.MISC, ModItems.CHARRED_BEAN.get(), 0.35f, 200)
                .unlockedBy(getHasName(ModItems.COOKED_BEAN.get()), has(ModItems.COOKED_BEAN.get()))
                .save(this.output);



    }
    @Override
    protected void generateForEnabledBlockFamilies(FeatureFlagSet pEnabledFeatures) {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(p_358446_ -> this.generateRecipes(p_358446_, pEnabledFeatures));
    }

    protected void oreSmelting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        this.oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected void oreBlasting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        this.oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    private <T extends AbstractCookingRecipe> void oreCooking(
            RecipeSerializer<T> pSerializer,
            AbstractCookingRecipe.Factory<T> pRecipeFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pSuffix
    ) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), this.has(itemlike))
                    .save(this.output, BeanMachine.MODID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput p_365932_, CompletableFuture<HolderLookup.Provider> p_363203_) {
            super(p_365932_, p_363203_);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput) {
            return new ModRecipeProvider(pRegistries, pOutput);
        }

        @Override
        public String getName() {
            return "Bean Machine Recipes";
        }
    }
}
