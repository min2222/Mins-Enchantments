package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.event.RenderEntityEvent;
import com.min01.minsenchantments.misc.IExtraEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.common.MinecraftForge;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher 
{
	@Shadow
	public Camera camera;
	
	@SuppressWarnings("unchecked")
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER))
	private <E extends Entity> void translate(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci)
	{
		MinecraftForge.EVENT_BUS.post(new RenderEntityEvent.Pre<E>(p_114385_, (EntityRenderer<E>) EntityRenderDispatcher.class.cast(this).getRenderer(p_114385_), p_114390_, p_114391_, p_114392_, p_114393_));
	}
	
	@SuppressWarnings("unchecked")
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V",shift = At.Shift.BEFORE))
	private <E extends Entity> void popPose(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci)
	{
		MinecraftForge.EVENT_BUS.post(new RenderEntityEvent.Post<E>(p_114385_, (EntityRenderer<E>) EntityRenderDispatcher.class.cast(this).getRenderer(p_114385_), p_114390_, p_114391_, p_114392_, p_114393_));
	}
	
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.AFTER))
	private <E extends Entity> void render(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci)
	{
		if(p_114385_ instanceof LivingEntity living)
		{
			if(((IExtraEntity) living).isSoulFire())
			{
				this.renderSoulFlame(p_114391_, p_114392_, p_114385_);
			}
		}
	}
	
	private void renderSoulFlame(PoseStack p_114454_, MultiBufferSource p_114455_, Entity p_114456_)
	{
		Material soulfire = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_0"));
		Material soulfire1 = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("block/soul_fire_1"));
		TextureAtlasSprite textureatlassprite = soulfire.sprite();
		TextureAtlasSprite textureatlassprite1 = soulfire1.sprite();
		p_114454_.pushPose();
		float f = p_114456_.getBbWidth() * 1.4F;
		p_114454_.scale(f, f, f);
		float f1 = 0.5F;
		float f3 = p_114456_.getBbHeight() / f;
		float f4 = 0.0F;
		p_114454_.mulPose(Axis.YP.rotationDegrees(-this.camera.getYRot()));
		p_114454_.translate(0.0F, 0.0F, -0.3F + (float)((int)f3) * 0.02F);
		float f5 = 0.0F;
		int i = 0;
		VertexConsumer vertexconsumer = p_114455_.getBuffer(Sheets.cutoutBlockSheet());
		
		for(PoseStack.Pose posestack$pose = p_114454_.last(); f3 > 0.0F; ++i)
		{
			TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
			float f6 = textureatlassprite2.getU0();
			float f7 = textureatlassprite2.getV0();
			float f8 = textureatlassprite2.getU1();
			float f9 = textureatlassprite2.getV1();
			if (i / 2 % 2 == 0) 
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
		p_114454_.popPose();
	}
	
	@Shadow
	private static void fireVertex(PoseStack.Pose p_114415_, VertexConsumer p_114416_, float p_114417_, float p_114418_, float p_114419_, float p_114420_, float p_114421_)
	{
		
	}
}
