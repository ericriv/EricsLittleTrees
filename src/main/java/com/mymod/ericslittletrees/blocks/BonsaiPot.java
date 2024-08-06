package com.mymod.ericslittletrees.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BonsaiPot extends FlowerPotBlock {
    public BonsaiPot(BlockBehaviour.Properties properties) {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> Blocks.AIR, properties);
    }

    private boolean isSapling(Block block) {
        return block == Blocks.OAK_SAPLING ||
               block == Blocks.SPRUCE_SAPLING ||
               block == Blocks.BIRCH_SAPLING ||
               block == Blocks.JUNGLE_SAPLING ||
               block == Blocks.ACACIA_SAPLING ||
               block == Blocks.DARK_OAK_SAPLING ||
               block == Blocks.CHERRY_SAPLING;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        Block block = Block.byItem(itemstack.getItem());

        if (itemstack.getItem() instanceof BlockItem && isSapling(block)) {
            return super.use(state, world, pos, player, hand, hit);
        }

        if (itemstack.getItem() instanceof BlockItem) {
            return InteractionResult.FAIL;
        }

        return super.use(state, world, pos, player, hand, hit);
    }
}
