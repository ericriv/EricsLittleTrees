package com.mymod.ericslittletrees.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.Level;
import java.util.Random;

import com.mymod.ericslittletrees.blocks.BonsaiPot;

public class BonsaiPotBlockEntity extends BlockEntity implements BlockEntityTicker<BlockEntity> {

    private static final Random random = new Random();
    private int tickCounter = 0;
    private int chanceCounter = 0;
    private static final int TICK_INTERVAL = 100;

    public BonsaiPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BONSAI_POT_ENTITY.get(), pos, state);
    }

    private float getChance() {
    	chanceCounter++;
    	float baseChance = 0.001f; // Starting chance (0.1%)
        float maxChance = 0.5f;   // Maximum chance (50%)
        float growthRate = 0.0001f; // Growth rate per tick interval (0.01%)

        // Calculate chance based on how many ticks have passed
        float chance = baseChance + (chanceCounter * growthRate);

        // Cap the chance at maxChance
        return Math.min(chance, maxChance);
    }

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
		if (state.getValue(BonsaiPot.HAS_WIRE) && !state.getValue(BonsaiPot.IS_BONSAI)) {
			tickCounter++;
			if(tickCounter % TICK_INTERVAL == 0) {
	            float chance = getChance();
	            if (random.nextFloat() < chance) {
	                level.setBlock(pos, state.setValue(BonsaiPot.IS_BONSAI, true), 3);
	            }
			}
        }
	}
}
