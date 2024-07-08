package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.BlessmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

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
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MinsEnchantments.MODID, "bless_core"), "main");
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
	private final ModelPart core;
	private final ModelPart out;
	private final ModelPart line;
	private final ModelPart line2;
	private final ModelPart line3;
	
	public BlessmentTableRenderer(BlockEntityRendererProvider.Context p_173613_) 
	{
		this.core = p_173613_.bakeLayer(LAYER_LOCATION).getChild("core");
		this.out = p_173613_.bakeLayer(LAYER_LOCATION).getChild("out");
		this.line = p_173613_.bakeLayer(LAYER_LOCATION).getChild("line");
		this.line2 = p_173613_.bakeLayer(LAYER_LOCATION).getChild("line2");
		this.line3 = p_173613_.bakeLayer(LAYER_LOCATION).getChild("line3");
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
	public void render(BlessmentTableBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_)
	{
		p_112309_.pushPose();
		float f1 = ((float)p_112307_.time + p_112308_) * 3.0F;
		VertexConsumer vertexConsumer = p_112310_.getBuffer(RenderType.entityCutout(new ResourceLocation(MinsEnchantments.MODID, "textures/block/bless_core.png")));
		p_112309_.translate(0.5, 1.5D, 0.5);
		p_112309_.scale(0.7F, 0.7F, 0.7F);
		p_112309_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.core.render(p_112309_, vertexConsumer, p_112311_, p_112312_);
		p_112309_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.out.render(p_112309_, vertexConsumer, p_112311_, p_112312_);
		p_112309_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line.render(p_112309_, vertexConsumer, p_112311_, p_112312_);
		p_112309_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line2.render(p_112309_, vertexConsumer, p_112311_, p_112312_);
		p_112309_.scale(1.1F, 1.1F, 1.1F);
		p_112309_.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line3.render(p_112309_, vertexConsumer, p_112311_, p_112312_);
		p_112309_.popPose();
	}
}
