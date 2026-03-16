package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EndEnchantmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int time;
	public RandomSource random = RandomSource.create();
	public EndEnchantmentTableBlockEntity(BlockPos pPos, BlockState pState)
	{
		super(CustomBlocks.END_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pPos, pState);
		this.time = this.random.nextInt(100000);
	}
	
	public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, EndEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
	}
	
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, EndEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
	}
}
