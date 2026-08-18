package com.min01.minsenchantments.screen;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.InventoryMenu;

public class OceanEnchantmentScreen extends MEnchantmentScreen
{
	public static final Material SHELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/base"));
	public static final Material ACTIVE_SHELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/cage"));
	public static final Material WIND_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/wind"));
	public static final Material VERTICAL_WIND_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/wind_vertical"));
	public static final Material OPEN_EYE_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("entity/conduit/open_eye"));

	private final ModelPart eye;
	private final ModelPart shell;
	private final ModelPart cage;
	
	public OceanEnchantmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		EntityModelSet modelSet = MEClientUtil.MC.getEntityModels();
		this.eye = modelSet.bakeLayer(ModelLayers.CONDUIT_EYE);
		this.shell = modelSet.bakeLayer(ModelLayers.CONDUIT_SHELL);
		this.cage = modelSet.bakeLayer(ModelLayers.CONDUIT_CAGE);
	}
	
	@Override
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		float f = this.tickCount + pPartialTick;
		if(!this.isActive) 
		{
			float f5 = this.getActiveRotation(0.0F);
			VertexConsumer vertexConsumer1 = SHELL_TEXTURE.buffer(pBuffer, RenderType::entitySolid);
			pPoseStack.pushPose();
			pPoseStack.mulPose((new Quaternionf()).rotationY(f5 * ((float)Math.PI / 180.0F)));
			this.shell.render(pPoseStack, vertexConsumer1, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
		}
		else 
		{
			float f1 = this.getActiveRotation(pPartialTick) * (180.0F / (float)Math.PI);
			float f2 = Mth.sin(f * 0.1F) / 2.0F + 0.5F;
			f2 = f2 * f2 + f2;
			
			pPoseStack.pushPose();
			pPoseStack.translate(0.0F, -0.15F + f2 * 0.1F, 0.0F);
			Vector3f vector3f = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
			pPoseStack.mulPose((new Quaternionf()).rotationAxis(f1 * ((float)Math.PI / 180.0F), vector3f));
			this.cage.render(pPoseStack, ACTIVE_SHELL_TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
			
			pPoseStack.pushPose();
			pPoseStack.translate(0.0F, -0.15F + f2 * 0.1F, 0.0F);
			pPoseStack.scale(0.5F, 0.5F, 0.5F);
			pPoseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
			this.eye.render(pPoseStack, OPEN_EYE_TEXTURE.buffer(pBuffer, RenderType::entityCutoutNoCull), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
			pPoseStack.popPose();
		}
	}
}
