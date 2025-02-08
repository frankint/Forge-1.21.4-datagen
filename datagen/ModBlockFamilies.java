package net.int_str.bean_machine.datagen;

import com.google.common.collect.Maps;
import net.int_str.bean_machine.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilies extends BlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily BEAN = familyBuilder(ModBlocks.BEAN_BLOCK.get())
            .stairs(ModBlocks.BEAN_STAIRS.get())
            .button(ModBlocks.BEAN_BUTTON.get())
            .fence(ModBlocks.BEAN_FENCE.get())
            .fenceGate(ModBlocks.BEAN_FENCE_GATE.get())
            .door(ModBlocks.BEAN_DOOR.get())
            .pressurePlate(ModBlocks.BEAN_PRESSURE_PLATE.get())
            .slab(ModBlocks.BEAN_SLAB.get())
            .trapdoor(ModBlocks.BEAN_TRAPDOOR.get())
            .wall(ModBlocks.BEAN_WALL.get())

            .getFamily();

    private static BlockFamily.Builder familyBuilder(Block pBaseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(pBaseBlock);
        BlockFamily blockfamily = MAP.put(pBaseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(pBaseBlock));
        } else {
            return blockfamily$builder;
        }
    }
    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
