package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.init.CustomBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SculkEnchantmentTableBlockEntity extends AbstractCustomEnchantmentTableBlockEntity
{
	public int time;
	private boolean isActive;
	public SculkEnchantmentTableBlockEntity(BlockPos pPos, BlockState pState)
	{
		super(CustomBlocks.SCULK_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pPos, pState);
	}
	
	public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, SculkEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
		long i = pLevel.getGameTime();
		if(i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				pBlockEntity.isActive = EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, blockpos);
			}
		}
		
		if(pBlockEntity.isActive() && pBlockEntity.time % 100 == 0)
		{
			for(int j1 = 0; j1 < 10; ++j1) 
			{
				pLevel.addParticle(new ShriekParticleOption(j1 * 5), false, (double)pPos.getX() + 0.5D, (double)pPos.getY() + SculkShriekerBlock.TOP_Y, (double)pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, SculkEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
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
}
