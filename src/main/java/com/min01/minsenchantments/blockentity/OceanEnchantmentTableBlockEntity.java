package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class OceanEnchantmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int tickCount;
	private float activeRotation;
	private boolean isActive;
	   
	public OceanEnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_)
	{
		super(CustomBlocks.OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), p_155501_, p_155502_);
	}
	
	public static void clientTick(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, OceanEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.tickCount;
		long i = p_155404_.getGameTime();
		if (i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				p_155407_.isActive = EnchantmentTableBlock.isValidBookShelf(p_155404_, p_155405_, blockpos);
			}
		}
		
		if (p_155407_.isActive()) 
		{
			++p_155407_.activeRotation;
		}
	}
	
	public static void serverTick(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, OceanEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.tickCount;
		long i = p_155439_.getGameTime();
		if (i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				p_155407_.isActive = EnchantmentTableBlock.isValidBookShelf(p_155439_, p_155440_, blockpos);
			}
		}
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}

	public float getActiveRotation(float p_59198_)
	{
		return (this.activeRotation + p_59198_) * -0.0375F;
	}
}
