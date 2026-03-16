package com.min01.minsenchantments.block;

import javax.annotation.Nullable;

import com.min01.minsenchantments.blockentity.NetherEnchantmentTableBlockEntity;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.menu.NetherEnchantmentMenu;

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

public class NetherEnchantmentTableBlock extends AbstractCustomEnchantmentTableBlock
{
	public NetherEnchantmentTableBlock(Properties pProperties) 
	{
		super(pProperties);
	}

	@Override
	public AbstractContainerMenu getEnchantmentMenu(int id, Inventory inventory, ContainerLevelAccess access) 
	{
		return new NetherEnchantmentMenu(id, inventory, access);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
	{
		return new NetherEnchantmentTableBlockEntity(pPos, pState);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType)
	{
		return createTickerHelper(pType, CustomBlocks.NETHER_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pLevel.isClientSide ? NetherEnchantmentTableBlockEntity::clientTick : NetherEnchantmentTableBlockEntity::serverTick);
	}

	@Override
	public ParticleOptions getBookshelfParticle()
	{
		return ParticleTypes.SMALL_FLAME;
	}
}
