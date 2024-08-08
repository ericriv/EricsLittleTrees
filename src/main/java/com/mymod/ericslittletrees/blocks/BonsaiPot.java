package com.mymod.ericslittletrees.blocks;

import com.mymod.ericslittletrees.EricsLittleTrees;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class BonsaiPot extends FlowerPotBlock {
    public static final EnumProperty<BonsaiPotContents> CONTENTS = EnumProperty.create("contents", BonsaiPotContents.class);

    public BonsaiPot(BlockBehaviour.Properties properties) {
        super(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> Blocks.AIR, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CONTENTS, BonsaiPotContents.EMPTY));
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
        if (state.getValue(CONTENTS) != BonsaiPotContents.EMPTY) {
            // If player is right-clicking with an empty hand, remove the sapling
            if (player.getItemInHand(hand).isEmpty()) {
                if (!world.isClientSide) {
                    // Drop the sapling item
                    popResource(world, pos, new ItemStack(state.getValue(CONTENTS).getSaplingBlock()));
                    // Set the pot back to empty
                    world.setBlock(pos, state.setValue(CONTENTS, BonsaiPotContents.EMPTY), 3);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        ItemStack itemstack = player.getItemInHand(hand);
        Block block = Block.byItem(itemstack.getItem());

        if (itemstack.getItem() instanceof BlockItem && isSapling(block)) {
            BonsaiPotContents newContents = getContentsFromBlock(block);

            if (!world.isClientSide) {
                BlockState newState = state.setValue(CONTENTS, newContents);
                world.setBlock(pos, newState, 3);

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    
    private BonsaiPotContents getContentsFromBlock(Block block) {
        if (block == Blocks.OAK_SAPLING) return BonsaiPotContents.OAK_SAPLING;
        if (block == Blocks.SPRUCE_SAPLING) return BonsaiPotContents.SPRUCE_SAPLING;
        if (block == Blocks.BIRCH_SAPLING) return BonsaiPotContents.BIRCH_SAPLING;
        if (block == Blocks.JUNGLE_SAPLING) return BonsaiPotContents.JUNGLE_SAPLING;
        if (block == Blocks.ACACIA_SAPLING) return BonsaiPotContents.ACACIA_SAPLING;
        if (block == Blocks.DARK_OAK_SAPLING) return BonsaiPotContents.DARK_OAK_SAPLING;
        if (block == Blocks.CHERRY_SAPLING) return BonsaiPotContents.CHERRY_SAPLING;
        return BonsaiPotContents.EMPTY;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();

        // Always drop the Bonsai Pot item
        drops.add(new ItemStack(EricsLittleTrees.BONSAI_POT_ITEM.get()));

        // Check if the pot contains a sapling
        BonsaiPotContents contents = state.getValue(CONTENTS);
        if (contents != BonsaiPotContents.EMPTY) {
            // Add the sapling as a drop
            Block saplingBlock = contents.getSaplingBlock();
            if (saplingBlock != null) {
                drops.add(new ItemStack(saplingBlock));
            }
        }

        return drops;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTENTS);
    }
}
