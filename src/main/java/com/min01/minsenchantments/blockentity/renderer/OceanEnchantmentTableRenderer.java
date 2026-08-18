package com.min01.minsenchantments.blockentity.renderer;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class OceanEnchantmentTableRenderer
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
	
	public OceanEnchantmentTableRenderer(BlockEntityRendererProvider.Context pContext) 
	{
		this.renderer = pContext.getBlockEntityRenderDispatcher();
		this.eye = pContext.bakeLayer(ModelLayers.CONDUIT_EYE);
		this.wind = pContext.bakeLayer(ModelLayers.CONDUIT_WIND);
		this.shell = pContext.bakeLayer(ModelLayers.CONDUIT_SHELL);
		this.cage = pContext.bakeLayer(ModelLayers.CONDUIT_CAGE);
	}
	
	public void render(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		float f = pBlockEntity.tickCount + pPartialTick;
		if(!pBlockEntity.isActive) 
		{
			float f5 = pBlockEntity.getActiveRotation(0.0F);
			VertexConsumer vertexConsumer1 = SHELL_TEXTURE.buffer(pBuffer, RenderType::entitySolid);
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.05F, 0.5F);
			pPoseStack.mulPose((new Quaternionf()).rotationY(f5 * ((float)Math.PI / 180.0F)));
			this.shell.render(pPoseStack, vertexConsumer1, pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
		} 
		else 
		{
			float f1 = pBlockEntity.getActiveRotation(pPartialTick) * (180.0F / (float)Math.PI);
			float f2 = Mth.sin(f * 0.1F) / 2.0F + 0.5F;
			f2 = f2 * f2 + f2;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.0F + f2 * 0.2F, 0.5F);
			Vector3f vector3f = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
			pPoseStack.mulPose((new Quaternionf()).rotationAxis(f1 * ((float)Math.PI / 180.0F), vector3f));
			this.cage.render(pPoseStack, ACTIVE_SHELL_TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
			int i = pBlockEntity.tickCount / 66 % 3;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.0F, 0.5F);
			if(i == 1) 
			{
				pPoseStack.mulPose((new Quaternionf()).rotationX(((float)Math.PI / 2.0F)));
			} 
			else if(i == 2)
			{
				pPoseStack.mulPose((new Quaternionf()).rotationZ(((float)Math.PI / 2.0F)));
			}

			VertexConsumer vertexConsumer = (i == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer(pBuffer, RenderType::entityCutoutNoCull);
			this.wind.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.0F, 0.5F);
			pPoseStack.scale(0.875F, 0.875F, 0.875F);
			pPoseStack.mulPose((new Quaternionf()).rotationXYZ((float)Math.PI, 0.0F, (float)Math.PI));
			this.wind.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
			Camera camera = this.renderer.camera;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5F, 1.0F + f2 * 0.2F, 0.5F);
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			float f3 = -camera.getYRot();
			pPoseStack.mulPose((new Quaternionf()).rotationYXZ(f3 * ((float)Math.PI / 180.0F), camera.getXRot() * ((float)Math.PI / 180.0F), (float)Math.PI));
			pPoseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
			this.eye.render(pPoseStack, OPEN_EYE_TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
		}
	}
}
