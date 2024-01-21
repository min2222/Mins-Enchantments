package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.menu.SculkEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SculkEnchantmentScreen extends AbstractCustomEnchantmentScreen<SculkEnchantmentMenu>
{	
	public int time;
	
	public SculkEnchantmentScreen(SculkEnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_)
	{
		super(p_98754_, p_98755_, p_98756_);
	}
	
	@Override
	public boolean renderBookModel() 
	{
		return false;
	}
	
	@Override
	public void renderCustom(PoseStack stack, float partialTick, BufferSource multibuffersource$buffersource)
	{

	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		++this.time;
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.enchant.echo_shard.one" : "container.enchant.echo_shard.many";
	}
}
