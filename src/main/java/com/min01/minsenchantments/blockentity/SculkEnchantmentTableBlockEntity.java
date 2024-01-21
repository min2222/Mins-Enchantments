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
	public SculkEnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_)
	{
		super(CustomBlocks.SCULK_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), p_155501_, p_155502_);
	}
	
	public static void clientTick(Level p_155404_, BlockPos p_155405_, BlockState p_155406_, SculkEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.time;
		long i = p_155404_.getGameTime();
		if (i % 40L == 0L)
		{
			for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				p_155407_.isActive = EnchantmentTableBlock.isValidBookShelf(p_155404_, p_155405_, blockpos);
			}
		}
		
		if(p_155407_.isActive() && p_155407_.time % 100 == 0)
		{
			for(int j1 = 0; j1 < 10; ++j1) 
			{
				p_155404_.addParticle(new ShriekParticleOption(j1 * 5), false, (double)p_155405_.getX() + 0.5D, (double)p_155405_.getY() + SculkShriekerBlock.TOP_Y, (double)p_155405_.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	public static void serverTick(Level p_155439_, BlockPos p_155440_, BlockState p_155441_, SculkEnchantmentTableBlockEntity p_155407_)
	{
		++p_155407_.time;
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
}
