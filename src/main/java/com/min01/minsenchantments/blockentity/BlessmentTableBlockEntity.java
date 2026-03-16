package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlessmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int time;
	public RandomSource random = RandomSource.create();
	public BlessmentTableBlockEntity(BlockPos pPos, BlockState pState)
	{
		super(CustomBlocks.BLESSMENT_TABLE_BLOCK_ENTITY.get(), pPos, pState);
	}
	
	public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, BlessmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
	}
	
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BlessmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
	}
}
