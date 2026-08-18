package com.min01.minsenchantments.enchantment.nether;

import java.util.Map;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PyroMendingEnchantment extends MEnchantment
{
	public PyroMendingEnchantment() 
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.values(), EnchantmentType.NETHER);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 25;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity entity = event.getEntity();
		DamageSource source = event.getSource();
		if(source.is(DamageTypeTags.IS_FIRE))
		{
			Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(this, entity, ItemStack::isDamaged);
			if(entry != null)
			{
				ItemStack stack = entry.getValue();
				int i = Math.min((int) (event.getAmount() * stack.getXpRepairRatio()), stack.getDamageValue());
				stack.setDamageValue(stack.getDamageValue() - i);
			}
		}
	}
}
