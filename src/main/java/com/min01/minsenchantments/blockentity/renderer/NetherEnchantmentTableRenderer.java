package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;

public class NetherEnchantmentTableRenderer
{
	public final ItemRenderer itemRenderer;
	public final Camera camera;
	
	public NetherEnchantmentTableRenderer(BlockEntityRendererProvider.Context pContext) 
	{
		this.itemRenderer = pContext.getItemRenderer();
		this.camera = MEClientUtil.MC.gameRenderer.getMainCamera();
	}
	
	public void render(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 1.5F, 0.5F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.tickCount + pPartialTick));
		this.itemRenderer.renderStatic(Items.WITHER_SKELETON_SKULL.getDefaultInstance(), ItemDisplayContext.HEAD, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
		pPoseStack.popPose();
		
		if(pBlockEntity.isActive)
		{
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.0F, 0.5F);
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			MEClientUtil.renderFlame(pPoseStack, pBuffer, Axis.YP.rotationDegrees(-this.camera.getYRot()), ModelBakery.FIRE_0, ModelBakery.FIRE_1, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
	}
}
