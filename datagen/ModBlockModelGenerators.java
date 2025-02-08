package net.int_str.bean_machine.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.int_str.bean_machine.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModBlockModelGenerators extends BlockModelGenerators {
    final List<Block> nonOrientableTrapdoor = ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR, ModBlocks.BEAN_TRAPDOOR.get());
    final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
            .build();

    static final ImmutableMap<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>> SHAPE_CONSUMERS = ImmutableMap.<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>>builder()
            .put(BlockFamily.Variant.BUTTON, ModBlockFamilyProvider::button)
            .put(BlockFamily.Variant.DOOR, ModBlockFamilyProvider::door)
            .put(BlockFamily.Variant.CHISELED, ModBlockFamilyProvider::fullBlockVariant)
            .put(BlockFamily.Variant.CRACKED, ModBlockFamilyProvider::fullBlockVariant)
            .put(BlockFamily.Variant.CUSTOM_FENCE, ModBlockFamilyProvider::customFence)
            .put(BlockFamily.Variant.FENCE, ModBlockFamilyProvider::fence)
            .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, ModBlockFamilyProvider::customFenceGate)
            .put(BlockFamily.Variant.FENCE_GATE, ModBlockFamilyProvider::fenceGate)
            .put(BlockFamily.Variant.SIGN, ModBlockFamilyProvider::sign)
            .put(BlockFamily.Variant.SLAB, ModBlockFamilyProvider::slab)
            .put(BlockFamily.Variant.STAIRS, ModBlockFamilyProvider::stairs)
            .put(BlockFamily.Variant.PRESSURE_PLATE, ModBlockFamilyProvider::pressurePlate)
            .put(BlockFamily.Variant.TRAPDOOR, ModBlockFamilyProvider::trapdoor)
            .put(BlockFamily.Variant.WALL, ModBlockFamilyProvider::wall)
            .build();

    protected void createDoor(Block pDoorBlock) {
        TextureMapping texturemapping = TextureMapping.door(pDoorBlock);
        ResourceLocation resourcelocation = ModModelTemplates.DOOR_BOTTOM_LEFT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModModelTemplates.DOOR_BOTTOM_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation3 = ModModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation4 = ModModelTemplates.DOOR_TOP_LEFT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation5 = ModModelTemplates.DOOR_TOP_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation6 = ModModelTemplates.DOOR_TOP_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation7 = ModModelTemplates.DOOR_TOP_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        this.registerSimpleFlatItemModel(pDoorBlock.asItem());
        this.blockStateOutput
                .accept(
                        createDoor(
                                pDoorBlock,
                                resourcelocation,
                                resourcelocation1,
                                resourcelocation2,
                                resourcelocation3,
                                resourcelocation4,
                                resourcelocation5,
                                resourcelocation6,
                                resourcelocation7
                        )
                );
    }

    protected static BlockStateGenerator createTrapdoor(Block pTrapdoorBlock, ResourceLocation pTopModelLocation, ResourceLocation pBottomModelLocation, ResourceLocation pOpenModelLocation) {
        return MultiVariantGenerator.multiVariant(pTrapdoorBlock)
                .with(
                        PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN)
                                .select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.SOUTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.EAST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.WEST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.SOUTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.EAST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.WEST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation))
                                .select(
                                        Direction.SOUTH,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .select(
                                        Direction.EAST,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .select(
                                        Direction.WEST,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                                .select(Direction.NORTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation))
                                .select(
                                        Direction.SOUTH,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .select(
                                        Direction.EAST,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .select(
                                        Direction.WEST,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                );
    }

    public ModBlockModelGenerators(Consumer<BlockStateGenerator> pBlockStateOutput, ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput) {
        super(pBlockStateOutput, pItemModelOutput, pModelOutput);
    }

    @Override
    public void run() {

        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(
                p_375984_ -> this.family(p_375984_.getBaseBlock()).generateFor(p_375984_)
        );
//        createTrivialCube(ModBlocks.BEAN_BLOCK.get());
        createTrivialCube(ModBlocks.COMPRESSED_BEAN_BLOCK.get());
        createTrivialCube(ModBlocks.BEAN_ORE.get());
        createTrivialCube(ModBlocks.DEEPSLATE_BEAN_ORE.get());
        createTrivialCube(ModBlocks.MAGIC_BEAN_BLOCK.get());

    }
    public class ModBlockFamilyProvider extends BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
        @Nullable
        private BlockFamily family;
        @Nullable
        private ResourceLocation fullBlock;
        private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
        public ModBlockFamilyProvider(TextureMapping pMapping) {
            super(pMapping);
            this.mapping = pMapping;
        }

        @Override
        public BlockFamilyProvider generateFor(BlockFamily pFamily) {
            this.family = pFamily;
            pFamily.getVariants().forEach((p_375413_, p_375795_) -> {
                if (!this.skipGeneratingModelsFor.contains(p_375795_)) {
                    BiConsumer<ModBlockFamilyProvider, Block> biconsumer = ModBlockModelGenerators.SHAPE_CONSUMERS.get(p_375413_);
                    if (biconsumer != null) {
                        biconsumer.accept(this, p_375795_);
                    }
                }
            });
            return this;
        }

        protected BlockModelGenerators.BlockFamilyProvider door(Block pDoorBlock) {
            ModBlockModelGenerators.this.createDoor(pDoorBlock);
            return this;
        }
        public BlockModelGenerators.BlockFamilyProvider button(Block pButtonBlock) {
            ResourceLocation resourcelocation = ModModelTemplates.BUTTON.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.BUTTON_PRESSED.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton(pButtonBlock, resourcelocation, resourcelocation1));
            ResourceLocation resourcelocation2 = ModModelTemplates.BUTTON_INVENTORY.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pButtonBlock, resourcelocation2);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider wall(Block pWallBlock) {
            ResourceLocation resourcelocation = ModModelTemplates.WALL_POST.create(pWallBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.WALL_LOW_SIDE.create(pWallBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation2 = ModModelTemplates.WALL_TALL_SIDE.create(pWallBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createWall(pWallBlock, resourcelocation, resourcelocation1, resourcelocation2));
            ResourceLocation resourcelocation3 = ModModelTemplates.WALL_INVENTORY.create(pWallBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pWallBlock, resourcelocation3);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider customFence(Block pFenceBlock) {
            TextureMapping texturemapping = TextureMapping.customParticle(pFenceBlock);
            ResourceLocation resourcelocation = ModModelTemplates.CUSTOM_FENCE_POST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation2 = ModModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation3 = ModModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation4 = ModModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createCustomFence(pFenceBlock, resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3, resourcelocation4));
            ResourceLocation resourcelocation5 = ModModelTemplates.CUSTOM_FENCE_INVENTORY.create(pFenceBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pFenceBlock, resourcelocation5);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider fence(Block pFenceBlock) {
            ResourceLocation resourcelocation = ModModelTemplates.FENCE_POST.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.FENCE_SIDE.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence(pFenceBlock, resourcelocation, resourcelocation1));
            ResourceLocation resourcelocation2 = ModModelTemplates.FENCE_INVENTORY.create(pFenceBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pFenceBlock, resourcelocation2);
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider customFenceGate(Block pCustomFenceGateBlock) {
            TextureMapping texturemapping = TextureMapping.customParticle(pCustomFenceGateBlock);
            ResourceLocation resourcelocation = ModModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation2 = ModModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation3 = ModModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(pCustomFenceGateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createFenceGate(pCustomFenceGateBlock, resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3, false));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider fenceGate(Block pFenceGateBlock) {
            ResourceLocation resourcelocation = ModModelTemplates.FENCE_GATE_OPEN.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.FENCE_GATE_CLOSED.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation2 = ModModelTemplates.FENCE_GATE_WALL_OPEN.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation3 = ModModelTemplates.FENCE_GATE_WALL_CLOSED.create(pFenceGateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput
                    .accept(BlockModelGenerators.createFenceGate(pFenceGateBlock, resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3, true));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider pressurePlate(Block pPressurePlateBlock) {
            ResourceLocation resourcelocation = ModModelTemplates.PRESSURE_PLATE_UP.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation resourcelocation1 = ModModelTemplates.PRESSURE_PLATE_DOWN.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pPressurePlateBlock, resourcelocation, resourcelocation1));
            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider sign(Block pSignBlock) {
            if (this.family == null) {
                throw new IllegalStateException("Family not defined");
            } else {
                Block block = this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
                ResourceLocation resourcelocation = ModModelTemplates.PARTICLE_ONLY.create(pSignBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
                ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pSignBlock, resourcelocation));
                ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
                ModBlockModelGenerators.this.registerSimpleFlatItemModel(pSignBlock.asItem());
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider slab(Block pSlabBlock) {
            if (this.fullBlock == null) {
                throw new IllegalStateException("Full block not generated yet");
            } else {
                ResourceLocation resourcelocation = this.getOrCreateModel(ModModelTemplates.SLAB_BOTTOM, pSlabBlock);
                ResourceLocation resourcelocation1 = this.getOrCreateModel(ModModelTemplates.SLAB_TOP, pSlabBlock);
                ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSlab(pSlabBlock, resourcelocation, resourcelocation1, this.fullBlock));
                ModBlockModelGenerators.this.registerSimpleItemModel(pSlabBlock, resourcelocation);
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider stairs(Block pStairsBlock) {
            ResourceLocation resourcelocation = this.getOrCreateModel(ModModelTemplates.STAIRS_INNER, pStairsBlock);
            ResourceLocation resourcelocation1 = this.getOrCreateModel(ModModelTemplates.STAIRS_STRAIGHT, pStairsBlock);
            ResourceLocation resourcelocation2 = this.getOrCreateModel(ModModelTemplates.STAIRS_OUTER, pStairsBlock);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createStairs(pStairsBlock, resourcelocation, resourcelocation1, resourcelocation2));
            ModBlockModelGenerators.this.registerSimpleItemModel(pStairsBlock, resourcelocation1);
            return this;
        }

        protected BlockModelGenerators.BlockFamilyProvider fullBlockVariant(Block pBlock) {
            TexturedModel texturedmodel = ModBlockModelGenerators.this.texturedModels.getOrDefault(pBlock, TexturedModel.CUBE.get(pBlock));
            ResourceLocation resourcelocation = texturedmodel.create(pBlock, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pBlock, resourcelocation));
            return this;
        }

        protected void trapdoor(Block pTrapdoorBlock) {
            if (ModBlockModelGenerators.this.nonOrientableTrapdoor.contains(pTrapdoorBlock)) {
                ModBlockModelGenerators.this.createTrapdoor(pTrapdoorBlock);
            } else {
                ModBlockModelGenerators.this.createOrientableTrapdoor(pTrapdoorBlock);
            }
        }
    }
    protected void createOrientableTrapdoor(Block pOrientableTrapdoorBlock) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pOrientableTrapdoorBlock);
        ResourceLocation resourcelocation = ModModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(pOrientableTrapdoorBlock, resourcelocation, resourcelocation1, resourcelocation2));
        this.registerSimpleItemModel(pOrientableTrapdoorBlock, resourcelocation1);
    }

    protected void createTrapdoor(Block pTrapdoorBlock) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pTrapdoorBlock);
        ResourceLocation resourcelocation = ModModelTemplates.TRAPDOOR_TOP.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModModelTemplates.TRAPDOOR_BOTTOM.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModModelTemplates.TRAPDOOR_OPEN.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createTrapdoor(pTrapdoorBlock, resourcelocation, resourcelocation1, resourcelocation2));
        this.registerSimpleItemModel(pTrapdoorBlock, resourcelocation1);
    }
}
