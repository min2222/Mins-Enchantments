package com.min01.minsenchantments.blessment;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BlessmentSolaria extends AbstractBlessment
{
	public BlessmentSolaria() 
	{
		super(EnchantmentCategory.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.solariaMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.solariaMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.solariaMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onLivingTick(LivingEntity living)
	{
		for(EquipmentSlot slot : EquipmentSlot.values())
		{
			ItemStack stack = living.getItemBySlot(slot);
			int level = stack.getEnchantmentLevel(this);
			BlockPos blockPos = BlockPos.containing(living.getX(), living.getEyeY(), living.getZ());
			if(level > 0 && living.level().canSeeSky(blockPos))
			{
				if(living.tickCount % (level * (EnchantmentConfig.solariaIntervalPerLevel.get() * 20)) == 0)
				{
					stack.setDamageValue(stack.getDamageValue() - (level * EnchantmentConfig.solariaAmountPerLevel.get()));
				}
			}
		}
	}
}
