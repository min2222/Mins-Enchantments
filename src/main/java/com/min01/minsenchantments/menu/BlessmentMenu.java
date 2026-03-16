package com.min01.minsenchantments.menu;

import com.min01.minsenchantments.blessment.IBlessment;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomItems;
import com.min01.minsenchantments.init.CustomMenuType;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class BlessmentMenu extends AbstractCustomEnchantmentMenu
{
	public BlessmentMenu(int pId, Inventory pPlayerInventory) 
	{
		this(pId, pPlayerInventory, ContainerLevelAccess.NULL);
	}
	
	public BlessmentMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) 
	{
		super(CustomMenuType.BLESSMENT.get(), pContainerId, pPlayerInventory, pAccess);
	}

	@Override
	public Block getBlock()
	{
		return CustomBlocks.BLESSMENT_TABLE.get();
	}

	@Override
	public boolean is(ItemStack stack)
	{
		return stack.getItem() == CustomItems.HOLY_EMBLEM.get();
	}

	@Override
	public boolean isValidType(Enchantment enchantment)
	{
		return enchantment instanceof IBlessment;
	}
}
