package com.min01.minsenchantments.enchantment.sculk;

import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantmentInfection extends AbstractSculkEnchantment
{
	public EnchantmentInfection()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.infectionMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.infectionMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.infectionMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public int onBlockBreak(BlockPos pos, BlockState state, Player player, int exp) 
	{
		ItemStack stack = player.getMainHandItem();
		int level = stack.getEnchantmentLevel(this);
		if(level > 0 && exp > 0)
		{
			return exp + (level * EnchantmentConfig.infectionExpPerLevel.get());
		}
		return super.onBlockBreak(pos, state, player, exp);
	}
}
