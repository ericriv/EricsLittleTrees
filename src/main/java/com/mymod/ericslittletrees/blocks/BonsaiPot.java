package com.mymod.ericslittletrees.blocks;

import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;

public class BonsaiPot extends FlowerPotBlock {

    public BonsaiPot(BlockBehaviour.Properties properties) {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> Blocks.AIR, properties);
    }
}
