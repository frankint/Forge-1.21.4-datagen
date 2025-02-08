package net.int_str.bean_machine.datagen;

import net.int_str.bean_machine.item.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

public class ModItemModelGenerators extends ItemModelGenerators {
    public ModItemModelGenerators(ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput) {
        super(pItemModelOutput, pModelOutput);
    }

    @Override
    public void run() {
        generateFlatItem(ModItems.UNPROCESSED_BEAN.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.BEAN.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.COOKED_BEAN.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.CHARRED_BEAN.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.BEAN_WAND.get(), ModelTemplates.FLAT_ITEM);

        if (this.itemModelOutput instanceof ModModelProvider.ModItemInfoCollector collector)
            collector.generateDefaultBlockModels();
    }
}
