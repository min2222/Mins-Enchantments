package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.blockentity.NetherEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NetherEnchantmentTableRenderer implements BlockEntityRenderer<NetherEnchantmentTableBlockEntity>
{
	public NetherEnchantmentTableRenderer(BlockEntityRendererProvider.Context p_173613_) 
	{
		
	}
	
	@Override
	public void render(NetherEnchantmentTableBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_)
	{
		Minecraft mc = Minecraft.getInstance();
		p_112309_.translate(0.5, 1.5, 0.5);
		p_112309_.mulPose(Vector3f.YP.rotationDegrees(p_112307_.tickCount));
		mc.getItemRenderer().renderStatic(new ItemStack(Items.WITHER_SKELETON_SKULL), ItemTransforms.TransformType.HEAD, 15728880, OverlayTexture.NO_OVERLAY, p_112309_, p_112310_, 0);
	}
}
