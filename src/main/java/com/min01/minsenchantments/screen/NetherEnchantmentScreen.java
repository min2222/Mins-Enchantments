package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;

public class NetherEnchantmentScreen extends MEnchantmentScreen
{
	public final ItemRenderer itemRenderer;
	
	public NetherEnchantmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		this.itemRenderer = MEClientUtil.MC.getItemRenderer();
	}
	
	@Override
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		pPoseStack.pushPose();
		pPoseStack.mulPose(Axis.YP.rotationDegrees(this.tickCount + pPartialTick));
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, 0.25F, 0.0F);
		this.itemRenderer.renderStatic(Items.WITHER_SKELETON_SKULL.getDefaultInstance(), ItemDisplayContext.HEAD, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, this.minecraft.level, 0);
		pPoseStack.popPose();
		
		if(this.isActive)
		{
			pPoseStack.pushPose();
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			pPoseStack.translate(0.0F, 0.7F, 0.5F);
			MEClientUtil.renderFlame(pPoseStack, pBuffer, Axis.ZP.rotationDegrees(180.0F), ModelBakery.FIRE_0, ModelBakery.FIRE_1, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
	}
}
