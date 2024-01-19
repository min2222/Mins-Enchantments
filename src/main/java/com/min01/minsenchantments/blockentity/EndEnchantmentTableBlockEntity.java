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
	public EndEnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_)
	{
		super(CustomBlocks.END_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), p_155501_, p_155502_);
		this.time = this.random.nextInt(100000);
	}
	
	public static void clientTick(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, EndEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.time;
	}
	
	public static void serverTick(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, EndEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.time;
	}
}
