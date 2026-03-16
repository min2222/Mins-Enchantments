package com.min01.minsenchantments.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ToolActions;

public abstract class AbstractCustomEnchantment extends Enchantment
{
	public AbstractCustomEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) 
	{
		super(pRarity, pCategory, pApplicableSlots);
	}

	@Override
	public boolean isTradeable()
	{
		return false;
	}
	
	@Override
	public boolean isDiscoverable() 
	{
		return false;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		if(stack.canPerformAction(ToolActions.SHIELD_BLOCK))
		{
			return this.category.canEnchant(stack.getItem());
		}
		return super.canApplyAtEnchantingTable(stack);
	}
}
