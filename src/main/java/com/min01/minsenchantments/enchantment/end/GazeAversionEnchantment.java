package com.min01.minsenchantments.enchantment.end;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;

public class GazeAversionEnchantment extends MEnchantment
{
	public GazeAversionEnchantment() 
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD}, EnchantmentType.END);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return 5 + pLevel * 7;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return 50;
	}
	
	@Override
	public void onEnderManAnger(EnderManAngerEvent event) 
	{
		Player player = event.getPlayer();
		if(EnchantmentHelper.getEnchantmentLevel(this, player) > 0)
		{
			event.setCanceled(true);
		}
	}
}
