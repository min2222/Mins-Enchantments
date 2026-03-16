package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.menu.SculkEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SculkEnchantmentScreen extends AbstractCustomEnchantmentScreen<SculkEnchantmentMenu>
{	
	public int time;
	private boolean isActive;
	
	public SculkEnchantmentScreen(SculkEnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	public boolean renderBookModel() 
	{
		return false;
	}
	
	@Override
	public void renderCustom(PoseStack stack, float partialTick, BufferSource buffersource)
	{
		stack.translate(0, 0.75, 0);
		stack.scale(0.8F, -0.8F, 0.8F);
		this.minecraft.getItemRenderer().renderStatic(new ItemStack(Items.SCULK_SHRIEKER), ItemDisplayContext.GUI, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, stack, buffersource, this.minecraft.level, 0);
		
		if(this.isActive)
		{
			//TODO make shrieker particle
		}
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		++this.time;
		this.isActive = this.menu.is(this.menu.getSlot(1).getItem()) && !this.menu.getSlot(0).getItem().isEmpty();
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.enchant.echo_shard.one" : "container.enchant.echo_shard.many";
	}
}
