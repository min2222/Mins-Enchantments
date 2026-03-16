package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.blockentity.NetherEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NetherEnchantmentTableRenderer implements BlockEntityRenderer<NetherEnchantmentTableBlockEntity>
{
	public NetherEnchantmentTableRenderer(BlockEntityRendererProvider.Context ctx) 
	{
		
	}
	
	@Override
	public void render(NetherEnchantmentTableBlockEntity pBlockEntity, float p_112308_, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
	{
		Minecraft mc = Minecraft.getInstance();
		pPoseStack.translate(0.5F, 1.5F, 0.5F);
		pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.tickCount));
		mc.getItemRenderer().renderStatic(new ItemStack(Items.WITHER_SKELETON_SKULL), ItemDisplayContext.HEAD, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
	}
}
