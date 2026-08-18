package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BlessmentTableRenderer
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "cross"), "main");
	public static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/block/cross.png");

	private final ModelPart cross;
	
	public BlessmentTableRenderer(BlockEntityRendererProvider.Context pContext) 
	{
		this.cross = pContext.bakeLayer(LAYER_LOCATION);
	}
	
	public void render(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		float y = MEClientUtil.getY(pBlockEntity, pPartialTick);
		pPoseStack.pushPose();
		pPoseStack.translate(0.5F, 3.5F, 0.5F);
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.tickCount + pPartialTick));
		pPoseStack.translate(0.0F, 1.5F + y / 2.0F, 0.0F);
		this.cross.render(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION)), pPackedLight, OverlayTexture.NO_OVERLAY);
		pPoseStack.popPose();
	}
	
	public static LayerDefinition createBodyLayer() 
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(6, 0).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.5F));

		root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 3).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(3.0F, -3.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 6).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		root.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(6, 9).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -6.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		root.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(11, 11).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 6.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}
