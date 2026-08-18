package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEClientUtil;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(LightTexture.class)
public class MixinLightTexture
{
	@ModifyReturnValue(method = "getDarknessGamma", at = @At("RETURN"))
	private float minsenchantments$getDarknessGamma(float original)
	{
		Player player = MEClientUtil.MC.player;
		if(player != null)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(MEnchantments.SCULK_ADAPTATION.get(), player);
			if(level > 0)
			{
				float percent = MEUtil.percent(original, level * MEConfig.sculkAdaptationPercentPerLevel.get().floatValue());
				return Math.max(original - percent, 0.0F);
			}
		}
		return original;
	}
}
