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
	   
	public EndEnchantmentTableRenderer(BlockEntityRendererProvider.Context p_173613_) 
	{
		ModelPart modelpart = p_173613_.bakeLayer(ModelLayers.END_CRYSTAL);
		this.glass = modelpart.getChild("glass");
		this.cube = modelpart.getChild("cube");
	}
	
	@Override
	public void render(EndEnchantmentTableBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_)
	{
		p_112309_.pushPose();
		float f = getY(p_112307_, p_112308_);
		float f1 = ((float)p_112307_.time + p_112308_) * 3.0F;
		VertexConsumer vertexconsumer = p_112310_.getBuffer(RENDER_TYPE);
		p_112309_.pushPose();
		p_112309_.scale(0.875F, 0.875F, 0.875F);
		p_112309_.translate(0.5F, 0.5F, 0.5F);
		int i = OverlayTexture.NO_OVERLAY;

		p_112309_.mulPose(Axis.YP.rotationDegrees(f1));
		p_112309_.translate(0.0F, 1.5F + f / 2.0F, 0.0F);
		p_112309_.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		this.glass.render(p_112309_, vertexconsumer, p_112311_, i);
		p_112309_.scale(0.875F, 0.875F, 0.875F);
		p_112309_.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		p_112309_.mulPose(Axis.YP.rotationDegrees(f1));
		this.glass.render(p_112309_, vertexconsumer, p_112311_, i);
		p_112309_.scale(0.875F, 0.875F, 0.875F);
		p_112309_.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		p_112309_.mulPose(Axis.YP.rotationDegrees(f1));
		this.cube.render(p_112309_, vertexconsumer, p_112311_, i);
		p_112309_.popPose();
		p_112309_.popPose();
	}
	
	public static float getY(EndEnchantmentTableBlockEntity p_114159_, float p_114160_)
	{
		float f = (float)p_114159_.time + p_114160_;
		float f1 = Mth.sin(f * 0.2F) / 3.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 1.4F;
	}
}
