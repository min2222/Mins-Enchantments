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
	   
	public OceanEnchantmentTableBlockEntity(BlockPos pPos, BlockState pState)
	{
		super(CustomBlocks.OCEAN_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pPos, pState);
	}
	
	public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, OceanEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.tickCount;
		long i = pLevel.getGameTime();
		if(i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				pBlockEntity.isActive = EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, blockpos);
			}
		}
		
		if(pBlockEntity.isActive()) 
		{
			++pBlockEntity.activeRotation;
		}
	}
	
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, OceanEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.tickCount;
		long i = pLevel.getGameTime();
		if(i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				pBlockEntity.isActive = EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, blockpos);
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
