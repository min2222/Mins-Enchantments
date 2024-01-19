package com.min01.minsenchantments.block;

import javax.annotation.Nullable;

import com.min01.minsenchantments.blockentity.EndEnchantmentTableBlockEntity;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.menu.EndEnchantmentMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EndEnchantmentTableBlock extends AbstractCustomEnchantmentTableBlock
{
	public EndEnchantmentTableBlock(Properties p_52953_) 
	{
		super(p_52953_);
	}

	@Override
	public AbstractContainerMenu getEnchantmentMenu(int id, Inventory inventory, ContainerLevelAccess access) 
	{
		return new EndEnchantmentMenu(id, inventory, access);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153186_, BlockState p_153187_)
	{
		return new EndEnchantmentTableBlockEntity(p_153186_, p_153187_);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153094_, BlockState p_153095_, BlockEntityType<T> p_153096_)
	{
		return createTickerHelper(p_153096_, CustomBlocks.END_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), p_153094_.isClientSide ? EndEnchantmentTableBlockEntity::clientTick : EndEnchantmentTableBlockEntity::serverTick);
	}

	@Override
	public ParticleOptions getBookshelfParticle()
	{
		return ParticleTypes.PORTAL;
	}
}
