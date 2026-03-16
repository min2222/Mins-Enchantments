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
import net.minecraft.client.renderer.texture.OverlayTexture;
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
	   
	public OceanEnchantmentTableRenderer(BlockEntityRendererProvider.Context ctx) 
	{
		this.renderer = ctx.getBlockEntityRenderDispatcher();
		this.eye = ctx.bakeLayer(ModelLayers.CONDUIT_EYE);
		this.wind = ctx.bakeLayer(ModelLayers.CONDUIT_WIND);
		this.shell = ctx.bakeLayer(ModelLayers.CONDUIT_SHELL);
		this.cage = ctx.bakeLayer(ModelLayers.CONDUIT_CAGE);
	}

	@Override
	public void render(OceanEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		float f = (float)pBlockEntity.tickCount + pPartialTick;
		if(!pBlockEntity.isActive()) 
		{
			float f5 = pBlockEntity.getActiveRotation(0.0F);
			VertexConsumer vertexconsumer1 = SHELL_TEXTURE.buffer(pBuffer, RenderType::entitySolid);
			pPoseStack.pushPose();
			pPoseStack.translate(0.5D, 1.0D, 0.5D);
			pPoseStack.mulPose((new Quaternionf()).rotationY(f5 * ((float)Math.PI / 180F)));
			this.shell.render(pPoseStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
		} 
		else
		{
			float f1 = pBlockEntity.getActiveRotation(pPartialTick) * (180F / (float)Math.PI);
			float f2 = Mth.sin(f * 0.1F) / 3.0F + 0.5F;
			f2 = f2 * f2 + f2;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5D, (double)(1.0F + f2 * 0.2F), 0.5D);
			Vector3f vector3f = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
			pPoseStack.mulPose((new Quaternionf()).rotationAxis(f1 * ((float)Math.PI / 180F), vector3f));
			this.cage.render(pPoseStack, ACTIVE_SHELL_TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), pPackedLight, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
			int i = pBlockEntity.tickCount / 66 % 3;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5D, 1.0D, 0.5D);
			if(i == 1) 
			{
				pPoseStack.mulPose((new Quaternionf()).rotationX(((float)Math.PI / 2F)));
			}
			else if(i == 2) 
			{
				pPoseStack.mulPose((new Quaternionf()).rotationZ(((float)Math.PI / 2F)));
			}

			VertexConsumer vertexconsumer = (i == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer(pBuffer, RenderType::entityCutoutNoCull);
			this.wind.render(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
			pPoseStack.pushPose();
			pPoseStack.translate(0.5D, 1.0D, 0.5D);
			pPoseStack.scale(0.875F, 0.875F, 0.875F);
			pPoseStack.mulPose((new Quaternionf()).rotationXYZ((float)Math.PI, 0.0F, (float)Math.PI));
			this.wind.render(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
			Camera camera = this.renderer.camera;
			pPoseStack.pushPose();
			pPoseStack.translate(0.5D, (double)(1.0F + f2 * 0.2F), 0.5D);
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			float f3 = -camera.getYRot();
			pPoseStack.mulPose((new Quaternionf()).rotationYXZ(f3 * ((float)Math.PI / 180F), camera.getXRot() * ((float)Math.PI / 180F), (float)Math.PI));
			pPoseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
			this.eye.render(pPoseStack, (OPEN_EYE_TEXTURE).buffer(pBuffer, RenderType::entityCutoutNoCull), pPackedLight, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
		}
	}
}
