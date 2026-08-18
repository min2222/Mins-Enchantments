package com.min01.minsenchantments.menu;

import com.min01.minsenchantments.api.EnchantmentType;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;

public class MEnchantmentMenu extends EnchantmentMenu 
{
	public final EnchantmentType type;
	
	public MEnchantmentMenu(int pContainerId, Inventory pPlayerInventory, EnchantmentType type)
	{
		super(pContainerId, pPlayerInventory);
		this.type = type;
	}
	
	public MEnchantmentMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess, EnchantmentType type)
	{
		super(pContainerId, pPlayerInventory, pAccess);
		this.type = type;
	}
	
	@Override
	public MenuType<?> getType() 
	{
		switch(this.type)
		{
		case OCEAN:
			return MEMenuTypes.OCEAN_ENCHANTMENT.get();
		case NETHER:
			return MEMenuTypes.NETHER_ENCHANTMENT.get();
		case END:
			return MEMenuTypes.END_ENCHANTMENT.get();
		case SCULK:
			return MEMenuTypes.SCULK_ENCHANTMENT.get();
		case BLESS:
			return MEMenuTypes.BLESSMENT.get();
		}
		return super.getType();
	}
}
