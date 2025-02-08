package net.int_str.bean_machine.datagen;

import net.int_str.bean_machine.BeanMachine;
import net.int_str.bean_machine.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BeanMachine.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COMPRESSED_BEAN_BLOCK.get())
                .add(ModBlocks.BEAN_ORE.get())
                .add(ModBlocks.DEEPSLATE_BEAN_ORE.get())
                .add(ModBlocks.MAGIC_BEAN_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.BEAN_ORE.get())
                .add(ModBlocks.DEEPSLATE_BEAN_ORE.get());
        tag(BlockTags.FENCES)
                .add(ModBlocks.BEAN_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.BEAN_FENCE_GATE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.BEAN_WALL.get());
    }
}
