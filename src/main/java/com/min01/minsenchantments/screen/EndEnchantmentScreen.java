package com.min01.minsenchantments.screen;

import org.joml.Quaternionf;

import com.min01.minsenchantments.util.MEClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;

public class EndEnchantmentScreen extends MEnchantmentScreen
{
	private static final ResourceLocation END_CRYSTAL_LOCATION = ResourceLocation.parse("textures/entity/end_crystal/end_crystal.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4.0D));
	private final ModelPart cube;
	private final ModelPart glass;
	
	public EndEnchantmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		ModelPart modelPart = MEClientUtil.MC.getEntityModels().bakeLayer(ModelLayers.END_CRYSTAL);
		this.glass = modelPart.getChild("glass");
		this.cube = modelPart.getChild("cube");
	}
	
	@Override
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		float y = this.getY(pPartialTick, 0.2F);
		float f1 = (this.time + pPartialTick) * 3.0F;
		pPoseStack.pushPose();
		VertexConsumer vertexConsumer = pBuffer.getBuffer(RENDER_TYPE);
		pPoseStack.pushPose();
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.translate(0.0F, -1.0F, 0.0F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		pPoseStack.translate(0.0F, 1.5F + y / 2.0F, 0.0F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		this.glass.render(pPoseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.glass.render(pPoseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		pPoseStack.scale(0.875F, 0.875F, 0.875F);
		pPoseStack.mulPose((new Quaternionf()).setAngleAxis(Math.PI / 3.0F, SIN_45, 0.0F, SIN_45));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
		this.cube.render(pPoseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
		pPoseStack.popPose();
		pPoseStack.popPose();
	}
}
