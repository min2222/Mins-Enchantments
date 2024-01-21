package com.min01.minsenchantments.menu;

import com.min01.minsenchantments.enchantment.sculk.ISculkEnchantment;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomMenuType;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class SculkEnchantmentMenu extends AbstractCustomEnchantmentMenu
{
	public SculkEnchantmentMenu(int p_39454_, Inventory p_39455_) 
	{
		this(p_39454_, p_39455_, ContainerLevelAccess.NULL);
	}
	
	public SculkEnchantmentMenu(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) 
	{
		super(CustomMenuType.SCULK_ENCHANTMENT.get(), p_39457_, p_39458_, p_39459_);
	}

	@Override
	public Block getBlock()
	{
		return CustomBlocks.SCULK_ENCHANTMENT_TABLE.get();
	}

	@Override
	public boolean is(ItemStack stack)
	{
		return stack.getItem() == Items.ECHO_SHARD;
	}

	@Override
	public boolean isValidType(Enchantment enchantment)
	{
		return enchantment instanceof ISculkEnchantment;
	}
}
