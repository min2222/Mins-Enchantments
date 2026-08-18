package com.min01.minsenchantments.block;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.min01.minsenchantments.menu.MEnchantmentMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MEnchantmentTableBlock extends EnchantmentTableBlock
{
	public final ParticleOptions particle;
	public final EnchantmentType type;
	
	public MEnchantmentTableBlock(Properties pProperties, ParticleOptions particle, EnchantmentType type) 
	{
		super(pProperties);
		this.particle = particle;
		this.type = type;
	}
	
	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
	{
		for(BlockPos bookshelfPos : BOOKSHELF_OFFSETS) 
		{
			if(pRandom.nextInt(16) == 0 && isValidBookShelf(pLevel, pPos, bookshelfPos)) 
			{
				float motionX = (float) ((bookshelfPos.getX() + pRandom.nextFloat()) - 0.5D);
				float motionY = (bookshelfPos.getY() - pRandom.nextFloat() - 1.0F);
				float motionZ = (float) ((bookshelfPos.getZ() + pRandom.nextFloat()) - 0.5D);
				
				float x = (float) (pPos.getX() + 0.5D);
				float y = (float) (pPos.getY() + 1.5D);
				float z = (float) (pPos.getZ() + 0.5D);
				
				if(this.particle != ParticleTypes.PORTAL)
				{
					float relX = x + motionX;
					float relY = y + motionY;
					float relZ = z + motionZ;
					float speed = 0.05F;
					
					if(this.particle == ParticleTypes.END_ROD)
					{
						speed = 0.1F;
					}
					
					motionX = (x - relX) * speed;
					motionY = (y - relY) * speed;
					motionZ = (z - relZ) * speed;
					
					x = relX;
					y = relY;
					z = relZ;
				}
				
				pLevel.addParticle(this.particle, x, y, z, motionX, motionY, motionZ);
			}
		}
	}
	
	@Override
	public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) 
	{
		BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
		if(blockEntity instanceof MEnchantmentTableBlockEntity block)
		{
			Component component = block.getDisplayName();
			return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) ->
			{
				return new MEnchantmentMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(pLevel, pPos), this.type);
			}, component);
		} 
		else 
		{
			return null;
		}
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
	{
		return new MEnchantmentTableBlockEntity(pPos, pState);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType)
	{
		return createTickerHelper(pType, MEBlocks.ME_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), MEnchantmentTableBlockEntity::tick);
	}
}
