package com.min01.minsenchantments.blockentity.renderer;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.min01.minsenchantments.blockentity.OceanEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class OceanEnchantmentTableRenderer implements BlockEntityRenderer<OceanEnchantmentTableBlockEntity>
{
	public static final Material SHELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/base"));
	public static final Material ACTIVE_SHELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/cage"));
	public static final Material WIND_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/wind"));
	public static final Material VERTICAL_WIND_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/wind_vertical"));
	public static final Material OPEN_EYE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/open_eye"));
	private final ModelPart eye;
	private final ModelPart wind;
	private final ModelPart shell;
	private final ModelPart cage;
	private final BlockEntityRenderDispatcher renderer;
	   
	public OceanEnchantmentTableRenderer(BlockEntityRendererProvider.Context p_173613_) 
	{
		this.renderer = p_173613_.getBlockEntityRenderDispatcher();
		this.eye = p_173613_.bakeLayer(ModelLayers.CONDUIT_EYE);
		this.wind = p_173613_.bakeLayer(ModelLayers.CONDUIT_WIND);
		this.shell = p_173613_.bakeLayer(ModelLayers.CONDUIT_SHELL);
		this.cage = p_173613_.bakeLayer(ModelLayers.CONDUIT_CAGE);
	}

	@Override
	public void render(OceanEnchantmentTableBlockEntity p_112399_, float p_112400_, PoseStack p_112401_, MultiBufferSource p_112402_, int p_112403_, int p_112404_) 
	{
		float f = (float)p_112399_.tickCount + p_112400_;
		if (!p_112399_.isActive()) 
		{
			float f5 = p_112399_.getActiveRotation(0.0F);
			VertexConsumer vertexconsumer1 = SHELL_TEXTURE.buffer(p_112402_, RenderType::entitySolid);
			p_112401_.pushPose();
			p_112401_.translate(0.5D, 1.0D, 0.5D);
			p_112401_.mulPose((new Quaternionf()).rotationY(f5 * ((float)Math.PI / 180F)));
			this.shell.render(p_112401_, vertexconsumer1, p_112403_, p_112404_);
			p_112401_.popPose();
		} 
		else
		{
			float f1 = p_112399_.getActiveRotation(p_112400_) * (180F / (float)Math.PI);
			float f2 = Mth.sin(f * 0.1F) / 3.0F + 0.5F;
			f2 = f2 * f2 + f2;
			p_112401_.pushPose();
			p_112401_.translate(0.5D, (double)(1.0F + f2 * 0.2F), 0.5D);
			Vector3f vector3f = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
			p_112401_.mulPose((new Quaternionf()).rotationAxis(f1 * ((float)Math.PI / 180F), vector3f));
			this.cage.render(p_112401_, ACTIVE_SHELL_TEXTURE.buffer(p_112402_, RenderType::entityCutoutNoCull), p_112403_, p_112404_);
			p_112401_.popPose();
			int i = p_112399_.tickCount / 66 % 3;
			p_112401_.pushPose();
			p_112401_.translate(0.5D, 1.0D, 0.5D);
			if (i == 1) 
			{
				p_112401_.mulPose((new Quaternionf()).rotationX(((float)Math.PI / 2F)));
			}
			else if (i == 2) 
			{
				p_112401_.mulPose((new Quaternionf()).rotationZ(((float)Math.PI / 2F)));
			}

			VertexConsumer vertexconsumer = (i == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer(p_112402_, RenderType::entityCutoutNoCull);
			this.wind.render(p_112401_, vertexconsumer, p_112403_, p_112404_);
			p_112401_.popPose();
			p_112401_.pushPose();
			p_112401_.translate(0.5D, 1.0D, 0.5D);
			p_112401_.scale(0.875F, 0.875F, 0.875F);
			p_112401_.mulPose((new Quaternionf()).rotationXYZ((float)Math.PI, 0.0F, (float)Math.PI));
			this.wind.render(p_112401_, vertexconsumer, p_112403_, p_112404_);
			p_112401_.popPose();
			Camera camera = this.renderer.camera;
			p_112401_.pushPose();
			p_112401_.translate(0.5D, (double)(1.0F + f2 * 0.2F), 0.5D);
			p_112401_.scale(0.5F, 0.5F, 0.5F);
			float f3 = -camera.getYRot();
			p_112401_.mulPose((new Quaternionf()).rotationYXZ(f3 * ((float)Math.PI / 180F), camera.getXRot() * ((float)Math.PI / 180F), (float)Math.PI));
			p_112401_.scale(1.3333334F, 1.3333334F, 1.3333334F);
			this.eye.render(p_112401_, (OPEN_EYE_TEXTURE).buffer(p_112402_, RenderType::entityCutoutNoCull), p_112403_, p_112404_);
			p_112401_.popPose();
		}
	}
}
