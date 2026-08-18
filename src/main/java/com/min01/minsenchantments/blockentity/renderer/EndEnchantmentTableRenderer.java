package com.min01.minsenchantments.blockentity.renderer;

import org.joml.Quaternionf;

import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EndEnchantmentTableRenderer
{
	private static final ResourceLocation END_CRYSTAL_LOCATION = ResourceLocation.parse("textures/entity/end_crystal/end_crystal.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4.0D));
	private final ModelPart cube;
	private final ModelPart glass;
	
	public EndEnchantmentTableRenderer(BlockEntityRendererProvider.Context pContext) 
	{
		ModelPart modelPart = pContext.bakeLayer(ModelLayers.END_CRYSTAL);
		this.glass = modelPart.getChild("glass");
		this.cube = modelPart.getChild("cube");
	}
	
	public void render(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		float y = MEClientUtil.getY(pBlockEntity, pPartialTick);
		float f1 = (pBlockEntity.time + pPartialTick) * 3.0F;
		pPoseStack.pushPose();
		VertexConsumer vertexConsumer = pBuffer.getBuffer(RENDER_TYPE);
		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 0.5F, 0.5F);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		pPoseStack.translate(0.0F, 1.5F + y / 2.0F, 0.0F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		this.glass.render(pPoseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.glass.render(pPoseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.cube.render(pPoseStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
		pPoseStack.popPose();
		pPoseStack.popPose();
	}
}
