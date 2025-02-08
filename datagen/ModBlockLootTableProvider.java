package net.int_str.bean_machine.datagen;

import net.int_str.bean_machine.block.ModBlocks;
import net.int_str.bean_machine.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags() , pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BEAN_BLOCK.get());
        dropSelf(ModBlocks.COMPRESSED_BEAN_BLOCK.get());
        dropSelf(ModBlocks.MAGIC_BEAN_BLOCK.get());
        this.add(ModBlocks.BEAN_ORE.get(), x -> createOreDrop(ModBlocks.BEAN_ORE.get(), ModItems.UNPROCESSED_BEAN.get()));
        this.add(ModBlocks.DEEPSLATE_BEAN_ORE.get(), x -> createMultipleOreDrops(ModBlocks.DEEPSLATE_BEAN_ORE.get(), ModItems.UNPROCESSED_BEAN.get(), 1, 2));

        this.add(ModBlocks.BEAN_SLAB.get(), x -> createSlabItemTable(ModBlocks.BEAN_SLAB.get()));
        this.add(ModBlocks.BEAN_DOOR.get(), x -> createDoorTable(ModBlocks.BEAN_DOOR.get()));
        dropSelf(ModBlocks.BEAN_BUTTON.get());
        dropSelf(ModBlocks.BEAN_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BEAN_STAIRS.get());
        dropSelf(ModBlocks.BEAN_TRAPDOOR.get());
        dropSelf(ModBlocks.BEAN_FENCE.get());
        dropSelf(ModBlocks.BEAN_FENCE_GATE.get());
        dropSelf(ModBlocks.BEAN_WALL.get());
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item pItem, float min, float max) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock,
                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
                        pBlock,
                        LootItem.lootTableItem(pItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
