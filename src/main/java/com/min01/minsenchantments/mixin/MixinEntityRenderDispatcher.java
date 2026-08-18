package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEClientUtil;
import com.min01.minsenchantments.util.MEUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher 
{
	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
	private TextureAtlasSprite minsenchantments$renderFlame0(Material instance, Operation<TextureAtlasSprite> original, PoseStack pPoseStack, MultiBufferSource pBuffer, Entity pEntity)
	{
		if(MEUtil.hasEnchantmentData(pEntity, MEnchantments.SOUL_FIRE_ASPECT.get()))
		{
			instance = MEClientUtil.SOUL_FIRE_0;
		}
		return original.call(instance);
	}
	
	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target ="Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
	private TextureAtlasSprite minsenchantments$renderFlame1(Material instance, Operation<TextureAtlasSprite> original, PoseStack pPoseStack, MultiBufferSource pBuffer, Entity pEntity)
	{
		if(MEUtil.hasEnchantmentData(pEntity, MEnchantments.SOUL_FIRE_ASPECT.get()))
		{
			instance = MEClientUtil.SOUL_FIRE_1;
		}
		return original.call(instance);
	}
}
