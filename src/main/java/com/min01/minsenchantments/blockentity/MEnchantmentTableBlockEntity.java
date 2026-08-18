package com.min01.minsenchantments.blockentity;

import com.min01.minsenchantments.block.MEBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MEnchantmentTableBlockEntity extends EnchantmentTableBlockEntity
{
	public final RandomSource random = RandomSource.create();
	
	public int tickCount;
	public int time;
	public float activeRotation;
	public boolean isActive;
	
	public MEnchantmentTableBlockEntity(BlockPos pPos, BlockState pState) 
	{
		super(pPos, pState);
		this.time = this.random.nextInt(100000);
	}
	
	@Override
	public BlockEntityType<?> getType() 
	{
		return MEBlocks.ME_ENCHANTMENT_TABLE_BLOCK_ENTITY.get();
	}
	
	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, MEnchantmentTableBlockEntity pBlockEntity)
	{
		++pBlockEntity.time;
		++pBlockEntity.tickCount;
		long gameTime = pLevel.getGameTime();
		if(gameTime % 40L == 0L)
		{
			for(BlockPos bookshelfPos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) 
			{
				pBlockEntity.isActive = EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, bookshelfPos);
			}
		}
		
		if(pBlockEntity.isActive)
		{
			++pBlockEntity.activeRotation;
			if(pState.is(MEBlocks.SCULK_ENCHANTMENT_TABLE.get()) && pBlockEntity.tickCount % 50 == 0)
			{
				for(int i = 0; i < 10; ++i) 
				{
					pLevel.addParticle(new ShriekParticleOption(i * 5), false, pPos.getX() + 0.5D, pPos.getY() + SculkShriekerBlock.TOP_Y, pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
	
	public float getActiveRotation(float pPartialTick) 
	{
		return (this.activeRotation + pPartialTick) * -0.0375F;
	}
	
	@Override
	public Component getName() 
	{
		if(this.getBlockState().is(MEBlocks.BLESSMENT_TABLE.get()))
		{
			return this.getCustomName() != null ? this.getCustomName() : Component.translatable("container.bless");
		}
		return super.getName();
	}
}