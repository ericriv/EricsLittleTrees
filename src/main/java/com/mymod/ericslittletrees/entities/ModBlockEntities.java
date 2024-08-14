package com.mymod.ericslittletrees.entities;

import com.mymod.ericslittletrees.EricsLittleTrees;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EricsLittleTrees.MODID);

    public static final RegistryObject<BlockEntityType<BonsaiPotBlockEntity>> BONSAI_POT_ENTITY = BLOCK_ENTITIES.register("bonsai_pot_entity",
        () -> BlockEntityType.Builder.of(BonsaiPotBlockEntity::new, EricsLittleTrees.BONSAI_POT.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
