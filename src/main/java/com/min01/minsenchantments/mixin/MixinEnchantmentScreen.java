package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.screen.MEnchantmentScreen;
import com.mojang.blaze3d.platform.Lighting;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

@Mixin(EnchantmentScreen.class)
public class MixinEnchantmentScreen 
{
	@Shadow
	@Final
	@Mutable
	private static ResourceLocation ENCHANTING_TABLE_LOCATION;
	
	@Inject(method = "init", at = @At("TAIL"))
	protected void minsenchantments$init(CallbackInfo ci)
	{
		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;
		if(screen instanceof MEnchantmentScreen meScreen)
		{
			ENCHANTING_TABLE_LOCATION = meScreen.getTableLocation();
		}
	}
	
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 1))
	private MutableComponent minsenchantments$translatableOne(String pKey, Operation<MutableComponent> original)
	{
		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;
		if(screen instanceof MEnchantmentScreen meScreen)
		{
			return original.call(meScreen.getTranslationKey(false));
		}
		return original.call(pKey);
	}
	
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 2))
	private MutableComponent minsenchantments$translatableMany(String pKey, Object[] pArgs, Operation<MutableComponent> original)
	{
		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;
		if(screen instanceof MEnchantmentScreen meScreen)
		{
			return original.call(meScreen.getTranslationKey(true), pArgs);
		}
		return original.call(pKey, pArgs);
	}
	
	@WrapMethod(method = "renderBook")
	private void minsenchantments$renderBook(GuiGraphics pGuiGraphics, int pX, int pY, float pPartialTick, Operation<Void> original)
	{
		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;
		if(screen instanceof MEnchantmentScreen meScreen)
		{
			Lighting.setupForEntityInInventory();
			pGuiGraphics.pose().pushPose();
			pGuiGraphics.pose().translate((float)pX + 33.0F, (float)pY + 31.0F, 100.0F);
			pGuiGraphics.pose().scale(40.0F, 40.0F, 40.0F);
			meScreen.render(pGuiGraphics.pose(), pPartialTick, pGuiGraphics.bufferSource());
			pGuiGraphics.flush();
			pGuiGraphics.pose().popPose();
			Lighting.setupFor3DItems();
			return;
		}
		original.call(pGuiGraphics, pX, pY, pPartialTick);
	}
}
