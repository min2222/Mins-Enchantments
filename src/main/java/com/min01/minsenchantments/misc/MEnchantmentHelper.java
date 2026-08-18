package com.min01.minsenchantments.misc;

import java.util.List;

import com.google.common.collect.Lists;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

public class MEnchantmentHelper 
{
	//copied from EnchantmentHelper
	public static List<EnchantmentInstance> selectEnchantment(EnchantmentType type, RandomSource pRandom, ItemStack pItemStack, int pLevel)
	{
		List<EnchantmentInstance> list = Lists.newArrayList();
		int i = pItemStack.getEnchantmentValue();
		if(MEnchantmentCategory.SHIELD.canEnchant(pItemStack.getItem()))
		{
			//since shield use iron ingot for crafting recipe;
			//for now, we don't care about modded shields;
			i = Tiers.IRON.getEnchantmentValue();
		}
		if(i <= 0) 
		{
			return list;
		}
		else 
		{
			pLevel += 1 + pRandom.nextInt(i / 4 + 1) + pRandom.nextInt(i / 4 + 1);
			float f = (pRandom.nextFloat() + pRandom.nextFloat() - 1.0F) * 0.15F;
			pLevel = Mth.clamp(Math.round((float)pLevel + (float)pLevel * f), 1, Integer.MAX_VALUE);
			List<EnchantmentInstance> list1 = getAvailableEnchantmentResults(type, pLevel, pItemStack);
			if(!list1.isEmpty()) 
			{
				WeightedRandom.getRandomItem(pRandom, list1).ifPresent(list::add);
				while(pRandom.nextInt(50) <= pLevel) 
				{
					if(!list.isEmpty()) 
					{
						EnchantmentHelper.filterCompatibleEnchantments(list1, Util.lastOf(list));
					}
					if(list1.isEmpty())
					{
						break;
					}
					WeightedRandom.getRandomItem(pRandom, list1).ifPresent(list::add);
					pLevel /= 2;
				}
			}
			return list;
		}
	}
	   
	//copied from EnchantmentHelper
	public static List<EnchantmentInstance> getAvailableEnchantmentResults(EnchantmentType type, int pLevel, ItemStack pStack) 
	{
		List<EnchantmentInstance> list = Lists.newArrayList();
		boolean flag = pStack.is(Items.BOOK);
		for(Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) 
		{
			if(!(enchantment instanceof MEnchantment mEnchant) || !mEnchant.type.equals(type))
			{
				continue;
			}
			
			if(enchantment.canApplyAtEnchantingTable(pStack) || (flag && enchantment.isAllowedOnBooks()))
			{
				for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i)
				{
					if(pLevel >= enchantment.getMinCost(i) && pLevel <= enchantment.getMaxCost(i)) 
					{
						list.add(new EnchantmentInstance(enchantment, i));
						break;
					}
				}
			}
		}
		return list;
	}
}
