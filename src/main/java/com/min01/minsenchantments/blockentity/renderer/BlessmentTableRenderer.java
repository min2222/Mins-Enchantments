package com.min01.minsenchantments.blockentity.renderer;

import org.joml.Quaternionf;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.BlessmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BlessmentTableRenderer implements BlockEntityRenderer<BlessmentTableBlockEntity>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "bless_core"), "main");
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
	private final ModelPart core;
	private final ModelPart out;
	private final ModelPart line;
	private final ModelPart line2;
	private final ModelPart line3;
	
	public BlessmentTableRenderer(BlockEntityRendererProvider.Context ctx) 
	{
		this.core = ctx.bakeLayer(LAYER_LOCATION).getChild("core");
		this.out = ctx.bakeLayer(LAYER_LOCATION).getChild("out");
		this.line = ctx.bakeLayer(LAYER_LOCATION).getChild("line");
		this.line2 = ctx.bakeLayer(LAYER_LOCATION).getChild("line2");
		this.line3 = ctx.bakeLayer(LAYER_LOCATION).getChild("line3");
	}
	
	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.ZERO);

		core.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition out = partdefinition.addOrReplaceChild("out", CubeListBuilder.create(), PartPose.ZERO);

		out.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition line = partdefinition.addOrReplaceChild("line", CubeListBuilder.create(), PartPose.ZERO);

		line.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-22, 42).addBox(-11.0F, 0.0F, -11.0F, 22.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition line2 = partdefinition.addOrReplaceChild("line2", CubeListBuilder.create(), PartPose.ZERO);

		line2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-22, 42).addBox(-11.0F, 0.0F, -11.0F, 22.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		PartDefinition line3 = partdefinition.addOrReplaceChild("line3", CubeListBuilder.create(), PartPose.ZERO);

		line3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(-22, 42).addBox(-11.0F, 0.0F, -11.0F, 22.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	@Override
	public void render(BlessmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
	{
		pPoseStack.pushPose();
		float f1 = ((float)pBlockEntity.time + pPartialTick) * 3.0F;
		VertexConsumer vertexConsumer = pBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/block/bless_core.png")));
		pPoseStack.translate(0.5, 1.5D, 0.5);
		pPoseStack.scale(0.7F, 0.7F, 0.7F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.core.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.out.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.line.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.line2.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
		pPoseStack.scale(1.1F, 1.1F, 1.1F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3.0F), SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.line3.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
		pPoseStack.popPose();
	}
}
