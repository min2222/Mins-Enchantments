package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.minsenchantments.event.RenderEntityEvent;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher 
{
	@SuppressWarnings("unchecked")
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER))
	private <E extends Entity> void beforeRender(E pEntity, double pX, double pY, double pZ, float pRotationYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci)
	{
		MinecraftForge.EVENT_BUS.post(new RenderEntityEvent.Pre<E>(pEntity, (EntityRenderer<E>) EntityRenderDispatcher.class.cast(this).getRenderer(pEntity), pPartialTicks, pPoseStack, pBuffer, pPackedLight));
	}
	
	@SuppressWarnings("unchecked")
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V",shift = At.Shift.BEFORE))
	private <E extends Entity> void afterRender(E pEntity, double pX, double pY, double pZ, float pRotationYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci)
	{
		MinecraftForge.EVENT_BUS.post(new RenderEntityEvent.Post<E>(pEntity, (EntityRenderer<E>) EntityRenderDispatcher.class.cast(this).getRenderer(pEntity), pPartialTicks, pPoseStack, pBuffer, pPackedLight));
	}	
}
