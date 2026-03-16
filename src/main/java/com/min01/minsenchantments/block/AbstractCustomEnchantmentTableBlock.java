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
	public AbstractCustomEnchantmentTableBlock(Properties pProperties) 
	{
		super(pProperties);
	}
	
	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
	{
		for(BlockPos blockpos : BOOKSHELF_OFFSETS) 
		{
			if(pRandom.nextInt(16) == 0 && isValidBookShelf(pLevel, pPos, blockpos)) 
			{
				pLevel.addParticle(this.getBookshelfParticle(), (double)pPos.getX() + 0.5D, (double)pPos.getY() + 2.0D, (double)pPos.getZ() + 0.5D, (double)((float)blockpos.getX() + pRandom.nextFloat()) - 0.1D, (double)((float)blockpos.getY() - pRandom.nextFloat() - 0.1F), (double)((float)blockpos.getZ() + pRandom.nextFloat()) - 0.1D);
			}
		}
	}
	
	@Nullable
	@Override
	public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) 
	{
		BlockEntity blockentity = pLevel.getBlockEntity(pPos);
		if(blockentity instanceof AbstractCustomEnchantmentTableBlockEntity)
		{
			Component component = ((Nameable)blockentity).getDisplayName();
			return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) ->
			{
				return this.getEnchantmentMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(pLevel, pPos));
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
