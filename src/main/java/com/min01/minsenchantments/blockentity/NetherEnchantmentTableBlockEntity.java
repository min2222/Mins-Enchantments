package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class NetherEnchantmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int tickCount;
	public NetherEnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_)
	{
		super(CustomBlocks.NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), p_155501_, p_155502_);
	}
	
	public static void clientTick(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, NetherEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.tickCount;
	}
	
	public static void serverTick(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, NetherEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.tickCount;
	}
}
