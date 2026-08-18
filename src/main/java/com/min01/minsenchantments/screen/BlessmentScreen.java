package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.renderer.BlessmentTableRenderer;
import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;

public class BlessmentScreen extends MEnchantmentScreen
{
	public static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/block/cross.png");

	private final ModelPart cross;
	
	public BlessmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		this.cross = MEClientUtil.MC.getEntityModels().bakeLayer(BlessmentTableRenderer.LAYER_LOCATION);
	}
	
	@Override
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		float y = this.getY(pPartialTick, 0.1F);
		pPoseStack.pushPose();
		pPoseStack.translate(0.0F, -2.0F, 0.0F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(this.tickCount + pPartialTick));
		pPoseStack.translate(0.0F, 1.5F + y / 2.0F, 0.0F);
		this.cross.render(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		pPoseStack.popPose();
	}
}
