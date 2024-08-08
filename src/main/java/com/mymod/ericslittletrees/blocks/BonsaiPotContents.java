package com.mymod.ericslittletrees.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.StringRepresentable;

public enum BonsaiPotContents implements StringRepresentable {
    EMPTY("empty", null),
    OAK_SAPLING("oak_sapling", Blocks.OAK_SAPLING),
    SPRUCE_SAPLING("spruce_sapling", Blocks.SPRUCE_SAPLING),
    BIRCH_SAPLING("birch_sapling", Blocks.BIRCH_SAPLING),
    JUNGLE_SAPLING("jungle_sapling", Blocks.JUNGLE_SAPLING),
    ACACIA_SAPLING("acacia_sapling", Blocks.ACACIA_SAPLING),
    DARK_OAK_SAPLING("dark_oak_sapling", Blocks.DARK_OAK_SAPLING),
    CHERRY_SAPLING("cherry_sapling", Blocks.CHERRY_SAPLING);

    private final String name;
    private final Block saplingBlock;

    BonsaiPotContents(String name, Block saplingBlock) {
        this.name = name;
        this.saplingBlock = saplingBlock;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Block getSaplingBlock() {
        return this.saplingBlock;
    }
}
