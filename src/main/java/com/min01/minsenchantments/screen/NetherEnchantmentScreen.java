package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.menu.NetherEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NetherEnchantmentScreen extends AbstractCustomEnchantmentScreen<NetherEnchantmentMenu>
{
	private boolean isActive;
	public int tickCount;
	
	public NetherEnchantmentScreen(NetherEnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
	}
	
	@Override
	public boolean renderBookModel() 
	{
		return false;
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		this.isActive = this.menu.is(this.menu.getSlot(1).getItem()) && !this.menu.getSlot(0).getItem().isEmpty();
		++this.tickCount;
	}
	
	@Override
	public void renderCustom(PoseStack stack, float partialTick, BufferSource buffersource)
	{		
		stack.pushPose();
		stack.translate(0, 0.5, 0);
		stack.mulPose(Axis.YP.rotationDegrees(this.tickCount));
		stack.scale(1, -1, 1);
		this.minecraft.getItemRenderer().renderStatic(new ItemStack(Items.WITHER_SKELETON_SKULL), ItemDisplayContext.HEAD, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, stack, buffersource, this.minecraft.level, 0);
		stack.popPose();
		
		if(this.isActive)
		{
			stack.scale(0.5F, 0.5F, 0.5F);
			stack.translate(0, 2, 0);
			this.renderFlame(stack, buffersource);
		}
	}
	
	private void renderFlame(PoseStack pPoseStack, MultiBufferSource pBuffer)
	{
		TextureAtlasSprite textureatlassprite = ModelBakery.FIRE_0.sprite();
		TextureAtlasSprite textureatlassprite1 = ModelBakery.FIRE_1.sprite();
		pPoseStack.pushPose();
		float f = 1.4F;
		pPoseStack.scale(f, f, f);
		float f1 = 0.5F;
		float f3 = 1 / f;
		float f4 = 0.0F;
		pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
		pPoseStack.translate(0.0F, 0.0F, -0.3F + (float)((int)f3) * 0.02F);
		float f5 = 0.0F;
		int i = 0;
		VertexConsumer vertexconsumer = pBuffer.getBuffer(Sheets.cutoutBlockSheet());

		for(PoseStack.Pose posestack$pose = pPoseStack.last(); f3 > 0.0F; ++i)
		{
			TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
			float f6 = textureatlassprite2.getU0();
			float f7 = textureatlassprite2.getV0();
			float f8 = textureatlassprite2.getU1();
			float f9 = textureatlassprite2.getV1();
			if(i / 2 % 2 == 0) 
			{
				float f10 = f8;
				f8 = f6;
				f6 = f10;
			}
			fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f8, f9);
			fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f6, f9);
			fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f6, f7);
			fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f8, f7);
			f3 -= 0.45F;
			f4 -= 0.45F;
			f1 *= 0.9F;
			f5 += 0.03F;
		}
		pPoseStack.popPose();
	}

	private static void fireVertex(PoseStack.Pose pMatrixEntry, VertexConsumer pBuffer, float pX, float pY, float pZ, float pTexU, float pTexV)
	{
		pBuffer.vertex(pMatrixEntry.pose(), pX, pY, pZ).color(255, 255, 255, 255).uv(pTexU, pTexV).overlayCoords(0, 10).uv2(240).normal(pMatrixEntry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.enchant.blaze_powder.one" : "container.enchant.blaze_powder.many";
	}
}
