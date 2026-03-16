package com.min01.minsenchantments.menu;

import com.min01.minsenchantments.enchantment.ocean.IOceanEnchantment;
import com.min01.minsenchantments.init.CustomBlocks;
import com.min01.minsenchantments.init.CustomMenuType;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class OceanEnchantmentMenu extends AbstractCustomEnchantmentMenu
{
	public OceanEnchantmentMenu(int pId, Inventory pPlayerInventory) 
	{
		this(pId, pPlayerInventory, ContainerLevelAccess.NULL);
	}
	
	public OceanEnchantmentMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) 
	{
		super(CustomMenuType.OCEAN_ENCHANTMENT.get(), pContainerId, pPlayerInventory, pAccess);
	}

	@Override
	public Block getBlock()
	{
		return CustomBlocks.OCEAN_ENCHANTMENT_TABLE.get();
	}

	@Override
	public boolean is(ItemStack stack)
	{
		return stack.getItem() == Items.PRISMARINE_CRYSTALS;
	}

	@Override
	public boolean isValidType(Enchantment enchantment)
	{
		return enchantment instanceof IOceanEnchantment;
	}
}
