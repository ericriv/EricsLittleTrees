package com.mymod.ericslittletrees.blocks;

import com.mymod.ericslittletrees.EricsLittleTrees;
import com.mymod.ericslittletrees.entities.BonsaiPotBlockEntity;
import com.mymod.ericslittletrees.items.BonsaiScissors;
import com.mymod.ericslittletrees.items.BonsaiWire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

public class BonsaiPot extends FlowerPotBlock implements EntityBlock {
    public static final EnumProperty<BonsaiPotContents> CONTENTS = EnumProperty.create("contents", BonsaiPotContents.class);
    public static final EnumProperty<BonsaiModel> BONSAI_MODEL = EnumProperty.create("bonsai_model", BonsaiModel.class);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty HAS_WIRE = BooleanProperty.create("has_wire");
    public static final BooleanProperty IS_BONSAI = BooleanProperty.create("is_bonsai");
    
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
               block == Blocks.MANGROVE_PROPAGULE ||
               block == Blocks.CHERRY_SAPLING;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (state.getValue(CONTENTS) != BonsaiPotContents.EMPTY) {
            // Handle sapling removal when not a bonsai
            if (itemStack.isEmpty() && !state.getValue(IS_BONSAI)) {
                if (!world.isClientSide) {
                    popResource(world, pos, new ItemStack(state.getValue(CONTENTS).getSaplingBlock()));
                    if(state.getValue(HAS_WIRE))
                    	popResource(world, pos, new ItemStack(EricsLittleTrees.BONSAI_WIRE.get()));
                    world.setBlock(pos, state.setValue(CONTENTS, BonsaiPotContents.EMPTY).setValue(HAS_WIRE, false), 3);
                }
                return InteractionResult.SUCCESS;
            }
            // Handle interaction with Bonsai Wire
            if (itemStack.getItem() instanceof BonsaiWire) {
                if (!world.isClientSide) {
                	if(!state.getValue(HAS_WIRE)) {
                		world.setBlock(pos, state.setValue(HAS_WIRE, true), 3);
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1); // Consume the wire item
                        }
                	}
                	else {
            			return InteractionResult.FAIL;
                	}
                    
                }
                return InteractionResult.SUCCESS;
            }
            //Handle interaction with bonsai scissors
            if (itemStack.getItem() instanceof BonsaiScissors) {
            	if(!world.isClientSide) {
            		BonsaiModel current = state.getValue(BONSAI_MODEL);
            		BonsaiModel next = current.getNextModel();
            		world.setBlock(pos, state.setValue(BONSAI_MODEL, next), 3);
            		return InteractionResult.SUCCESS;
            	}
            }
            //Handle interaction with bonemeal
            if(itemStack.getItem() == Items.BONE_MEAL) {
            	if (!world.isClientSide) {
            		if(!state.getValue(IS_BONSAI) && state.getValue(HAS_WIRE)) {
            			world.setBlock(pos, state.setValue(IS_BONSAI, true), 3);
                        if (!player.getAbilities().instabuild) {
                            itemStack.shrink(1); // Consume the wire item
                        }
            		}
            		else {
            			return InteractionResult.FAIL;
            		}
            	}
            	return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        // Handle sapling placement
        Block block = Block.byItem(itemStack.getItem());
        if (itemStack.getItem() instanceof BlockItem && isSapling(block)) {
            BonsaiPotContents newContents = getContentsFromBlock(block);
            if (!world.isClientSide) {
                BlockState newState = state.setValue(CONTENTS, newContents);
                world.setBlock(pos, newState, 3);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
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
        if (block == Blocks.MANGROVE_PROPAGULE) return BonsaiPotContents.MANGROVE_SAPLING;
        if (block == Blocks.CHERRY_SAPLING) return BonsaiPotContents.CHERRY_SAPLING;
        return BonsaiPotContents.EMPTY;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();

        // Always drop the Bonsai Pot item
        drops.add(new ItemStack(EricsLittleTrees.BONSAI_POT_ITEM.get()));

        // Check if the pot contains a sapling and add it as a drop
        BonsaiPotContents contents = state.getValue(CONTENTS);
        if (contents != BonsaiPotContents.EMPTY) {
            Block saplingBlock = contents.getSaplingBlock();
            if (saplingBlock != null) {
                drops.add(new ItemStack(saplingBlock));
            }
        }

        // Check if the pot has a wire and is not a bonsai, add wire as a drop
        if (state.getValue(HAS_WIRE) && !state.getValue(IS_BONSAI)) {
            drops.add(new ItemStack(EricsLittleTrees.BONSAI_WIRE.get()));
        }

        return drops;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTENTS, BONSAI_MODEL, FACING, HAS_WIRE, IS_BONSAI);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
        		.setValue(BONSAI_MODEL, BonsaiModel.BASE)
        		.setValue(HAS_WIRE, false)
        		.setValue(IS_BONSAI, false);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BonsaiPotBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (BlockEntityTicker<T>) (level1, pos, state1, entity) -> {
            if (entity instanceof BonsaiPotBlockEntity) {
                ((BonsaiPotBlockEntity) entity).tick(level1, pos, state1, (BonsaiPotBlockEntity) entity);
            }
        };
    }
    
    
}