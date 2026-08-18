package com.min01.minsenchantments.util;

import org.joml.Quaternionf;

import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class MEClientUtil 
{
	public static final Minecraft MC = Minecraft.getInstance();

	public static final Material SOUL_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("block/soul_fire_0"));
	public static final Material SOUL_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.parse("block/soul_fire_1"));
	   
	//copied from EndCrystalRenderer
	public static float getY(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick)
	{
		float f = pBlockEntity.time + pPartialTick;
		float f1 = Mth.sin(f * 0.2F) / 2.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 1.4F;
	}
	
	//copied from EntityRenderDispatcher
	public static void renderFlame(PoseStack pPoseStack, MultiBufferSource pBuffer, Quaternionf rotation, Material fire0, Material fire1, float width, float height)
	{
		TextureAtlasSprite textureatlassprite = fire0.sprite();
		TextureAtlasSprite textureatlassprite1 = fire1.sprite();
		pPoseStack.pushPose();
		float f = width * 1.4F;
		pPoseStack.scale(f, f, f);
		float f1 = 0.5F;
		float f3 = height / f;
		float f4 = 0.0F;
		pPoseStack.mulPose(rotation);
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

	public static void fireVertex(PoseStack.Pose pMatrixEntry, VertexConsumer pBuffer, float pX, float pY, float pZ, float pTexU, float pTexV)
	{
		pBuffer.vertex(pMatrixEntry.pose(), pX, pY, pZ).color(255, 255, 255, 255).uv(pTexU, pTexV).overlayCoords(0, 10).uv2(240).normal(pMatrixEntry.normal(), 0.0F, 1.0F, 0.0F).endVertex();
	}
}
