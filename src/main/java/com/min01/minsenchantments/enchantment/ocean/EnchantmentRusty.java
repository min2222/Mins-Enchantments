package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.enchantment.curse.AbstractCurseEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentRusty extends AbstractCurseEnchantment
{
	public EnchantmentRusty()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + EnchantmentConfig.rustyMaxCost.get();
	}
	
	@Override
	public int getMinCost(int pLevel) 
	{
		return EnchantmentConfig.rustyMinCost.get() + (pLevel - 1) * EnchantmentConfig.rustyMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player)
	{
		if(player.isInWater())
		{
			for(int i = 0; i < player.getInventory().getContainerSize(); i++)
			{
				ItemStack itemstack = player.getInventory().getItem(i);
				int level = itemstack.getEnchantmentLevel(this);
				if(level > 0)
				{
					if(player.tickCount % ((EnchantmentConfig.rustyDecreaseIntervalPerLevel.get() * 20) / level) == 0 && (itemstack.getDamageValue() > 1 || !itemstack.isDamaged()))
					{
						itemstack.hurtAndBreak(level, player, t -> 
						{
							
						});
					}
				}
			}
		}
	}
}
