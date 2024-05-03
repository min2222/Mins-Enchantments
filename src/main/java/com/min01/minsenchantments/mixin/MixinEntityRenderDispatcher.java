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
}
