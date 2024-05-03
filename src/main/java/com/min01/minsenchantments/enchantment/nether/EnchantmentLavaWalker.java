package com.min01.minsenchantments.enchantment.nether;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;

public class EnchantmentLavaWalker extends AbstractNetherEnchantment
{
	public EnchantmentLavaWalker()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.lavaWalkerMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.lavaWalkerMinCost.get();
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		int level = living.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(this);
		if(level > 0)
		{
			BlockPos pos = BlockPos.containing(living.getX(), Mth.floor(living.getBoundingBox().minY), living.getZ());
	        if(living.level().getBlockState(pos).getBlock() == Blocks.LAVA) 
	        {
	            if(living.getDeltaMovement().y < 0) 
	            {
	            	living.setDeltaMovement(living.getDeltaMovement().x, 0, living.getDeltaMovement().z);
	            	living.setPos(living.position().add(0, -living.getDeltaMovement().y, 0));
	            }
	            living.fallDistance = 0.0F;
	            living.setOnGround(true);
	        }
		}
	}
}
