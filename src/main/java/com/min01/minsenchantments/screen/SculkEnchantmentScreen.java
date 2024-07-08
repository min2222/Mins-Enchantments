package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.menu.SculkEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SculkEnchantmentScreen extends AbstractCustomEnchantmentScreen<SculkEnchantmentMenu>
{	
	public int time;
	private boolean isActive;
	
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
		stack.translate(0, 0.05, 0);
		stack.scale(0.8F, 0.8F, 0.8F);
		stack.mulPose(Vector3f.XP.rotationDegrees(10));
		this.minecraft.getItemRenderer().renderStatic(new ItemStack(Items.SCULK_SHRIEKER), ItemTransforms.TransformType.GUI, 15728880, OverlayTexture.NO_OVERLAY, stack, multibuffersource$buffersource, 0);
		
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
