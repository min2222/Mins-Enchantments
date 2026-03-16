package com.min01.minsenchantments.blockentity.renderer;

import org.joml.Quaternionf;

import com.min01.minsenchantments.blockentity.EndEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EndEnchantmentTableRenderer implements BlockEntityRenderer<EndEnchantmentTableBlockEntity>
{
	private static final ResourceLocation END_CRYSTAL_LOCATION = ResourceLocation.parse("textures/entity/end_crystal/end_crystal.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
	private final ModelPart cube;
	private final ModelPart glass;
	   
	public EndEnchantmentTableRenderer(BlockEntityRendererProvider.Context ctx) 
	{
		ModelPart modelpart = ctx.bakeLayer(ModelLayers.END_CRYSTAL);
		this.glass = modelpart.getChild("glass");
		this.cube = modelpart.getChild("cube");
	}
	
	@Override
	public void render(EndEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
	{
		pPoseStack.pushPose();
		float f = getY(pBlockEntity, pPartialTick);
		float f1 = ((float)pBlockEntity.time + pPartialTick) * 3.0F;
		VertexConsumer vertexconsumer = pBufferSource.getBuffer(RENDER_TYPE);
		pPoseStack.pushPose();
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.translate(0.5F, 0.5F, 0.5F);
		int i = OverlayTexture.NO_OVERLAY;

		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		pPoseStack.translate(0.0F, 1.5F + f / 2.0F, 0.0F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		this.glass.render(pPoseStack, vertexconsumer, pPackedLight, i);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.glass.render(pPoseStack, vertexconsumer, pPackedLight, i);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.cube.render(pPoseStack, vertexconsumer, pPackedLight, i);
		pPoseStack.popPose();
		pPoseStack.popPose();
	}
	
	public static float getY(EndEnchantmentTableBlockEntity pBlockEntity, float pPartialTick)
	{
		float f = (float)pBlockEntity.time + pPartialTick;
		float f1 = Mth.sin(f * 0.2F) / 3.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 1.4F;
	}
}
