package com.min01.minsenchantments.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;

public class SculkEnchantmentScreen extends MEnchantmentScreen
{
	public SculkEnchantmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		pPoseStack.pushPose();
		pPoseStack.translate(0.0F, -0.1F, 0.0F);
		pPoseStack.scale(0.8F, -0.8F, 0.8F);
		this.minecraft.getItemRenderer().renderStatic(Items.SCULK_SHRIEKER.getDefaultInstance(), ItemDisplayContext.GUI, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, this.minecraft.level, 0);
		pPoseStack.popPose();
	}
}
