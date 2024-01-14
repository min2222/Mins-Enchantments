package com.min01.minsenchantments.block;

import javax.annotation.Nullable;

import com.min01.minsenchantments.blockentity.AbstractCustomEnchantmentTableBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractCustomEnchantmentTableBlock extends EnchantmentTableBlock
{
	public AbstractCustomEnchantmentTableBlock(Properties p_52953_) 
	{
		super(p_52953_);
	}
	
	@Override
	public void animateTick(BlockState p_221092_, Level p_221093_, BlockPos p_221094_, RandomSource p_221095_)
	{
		for(BlockPos blockpos : BOOKSHELF_OFFSETS) 
		{
			if (p_221095_.nextInt(16) == 0 && isValidBookShelf(p_221093_, p_221094_, blockpos)) 
			{
				p_221093_.addParticle(this.getBookshelfParticle(), (double)p_221094_.getX() + 0.5D, (double)p_221094_.getY() + 2.0D, (double)p_221094_.getZ() + 0.5D, (double)((float)blockpos.getX() + p_221095_.nextFloat()) - 0.5D, (double)((float)blockpos.getY() - p_221095_.nextFloat() - 1.0F), (double)((float)blockpos.getZ() + p_221095_.nextFloat()) - 0.5D);
			}
		}
	}
	
	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState p_52993_, Level p_52994_, BlockPos p_52995_) 
	{
		BlockEntity blockentity = p_52994_.getBlockEntity(p_52995_);
		if (blockentity instanceof AbstractCustomEnchantmentTableBlockEntity)
		{
			Component component = ((Nameable)blockentity).getDisplayName();
			return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) ->
			{
				return this.getEnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.create(p_52994_, p_52995_));
			}, component);
		} 
		else 
		{
			return null;
		}
	}
	
	public abstract AbstractContainerMenu getEnchantmentMenu(int id, Inventory inventory, ContainerLevelAccess access);
	
	public abstract ParticleOptions getBookshelfParticle();
}
