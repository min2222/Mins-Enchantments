package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.menu.NetherEnchantmentMenu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class NetherEnchantmentScreen extends AbstractCustomEnchantmentScreen<NetherEnchantmentMenu>
{
	private boolean isActive;
	
	public NetherEnchantmentScreen(NetherEnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_)
	{
		super(p_98754_, p_98755_, p_98756_);
	}
	
	@Override
	public boolean renderBookModel() 
	{
		return false;
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		this.isActive = this.menu.is(this.menu.getSlot(1).getItem()) && !this.menu.getSlot(0).getItem().isEmpty();
	}
	
	@Override
	public ResourceLocation getBackgroundLocation()
	{
		return this.isActive ? new ResourceLocation(MinsEnchantments.MODID, "textures/gui/nether_enchanting_table_active.png") : new ResourceLocation(MinsEnchantments.MODID, "textures/gui/nether_enchanting_table.png");
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.enchant.blaze_powder.one" : "container.enchant.blaze_powder.many";
	}
}
