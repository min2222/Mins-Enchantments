package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class NetherEnchantmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int tickCount;
	public NetherEnchantmentTableBlockEntity(BlockPos pPos, BlockState pState)
	{
		super(CustomBlocks.NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pPos, pState);
	}
	
	public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, NetherEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.tickCount;
	}
	
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, NetherEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.tickCount;
	}
}
