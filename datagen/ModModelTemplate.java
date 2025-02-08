package net.int_str.bean_machine.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModModelTemplate extends ModelTemplate {
    private final Optional<ResourceLocation> model;
    private final Set<TextureSlot> requiredSlots;
    private final Optional<String> suffix;

    public ModModelTemplate(Optional<ResourceLocation> pModel, Optional<String> pSuffix, TextureSlot... pRequiredSlots) {
        super(pModel, pSuffix, pRequiredSlots);
        this.model = pModel;
        this.suffix = pSuffix;
        this.requiredSlots = ImmutableSet.copyOf(pRequiredSlots);
    }
    public ResourceLocation create(Block pBlock, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput) {
        if (pBlock instanceof DoorBlock || pBlock instanceof TrapDoorBlock)
            return this.createDoor(ModelLocationUtils.getModelLocation(pBlock, this.suffix.orElse("")), pTextureMapping, pOutput);
        return this.create(ModelLocationUtils.getModelLocation(pBlock, this.suffix.orElse("")), pTextureMapping, pOutput);
    }
    public ResourceLocation create(ResourceLocation pModelLocation, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput) {
        Map<TextureSlot, ResourceLocation> map = this.createMap(pTextureMapping);
        pOutput.accept(pModelLocation, () -> {
            JsonObject jsonobject = new JsonObject();
            this.model.ifPresent(p_376687_ -> jsonobject.addProperty("parent", p_376687_.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonobject1 = new JsonObject();
                map.forEach((p_375899_, p_377821_) -> jsonobject1.addProperty(p_375899_.getId(), p_377821_.toString()));
                jsonobject.add("textures", jsonobject1);
            }
            return jsonobject;
        });
        return pModelLocation;
    }
    public ResourceLocation createDoor(ResourceLocation pModelLocation, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput) {
        Map<TextureSlot, ResourceLocation> map = this.createMap(pTextureMapping);
        pOutput.accept(pModelLocation, () -> {
            JsonObject jsonobject = new JsonObject();
            this.model.ifPresent(p_376687_ -> jsonobject.addProperty("parent", p_376687_.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonobject1 = new JsonObject();
                map.forEach((p_375899_, p_377821_) -> jsonobject1.addProperty(p_375899_.getId(), p_377821_.toString()));
                jsonobject.add("textures", jsonobject1);
            }
            jsonobject.addProperty("render_type", "minecraft:cutout");
            return jsonobject;
        });
        return pModelLocation;
    }
    private Map<TextureSlot, ResourceLocation> createMap(TextureMapping pTextureMapping) {
        return Streams.concat(this.requiredSlots.stream(), pTextureMapping.getForced()).collect(ImmutableMap.toImmutableMap(Function.identity(), pTextureMapping::get));
    }
}
