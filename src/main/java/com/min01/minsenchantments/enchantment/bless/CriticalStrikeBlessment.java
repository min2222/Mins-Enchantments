package com.min01.minsenchantments.enchantment.bless;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event.Result;

public class CriticalStrikeBlessment extends MEnchantment
{
	public CriticalStrikeBlessment()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 17;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 30;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public void onCriticalHit(CriticalHitEvent event)
	{
		Player player = event.getEntity();
		int level = EnchantmentHelper.getEnchantmentLevel(this, player);
		if(level > 0)
		{
			double chance = level * MEConfig.criticalStrikeChancePerLevel.get();
			if(Math.random() <= chance / 100.0F)
			{
				event.setResult(Result.ALLOW);
			}
		}
	}
}
