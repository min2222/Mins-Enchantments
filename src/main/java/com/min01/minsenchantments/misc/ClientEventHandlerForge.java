package com.min01.minsenchantments.misc;

import java.util.HashMap;
import java.util.Map;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.event.RenderEntityEvent;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandlerForge 
{
	public static final Minecraft MC = Minecraft.getInstance();
	public static final Map<Projectile, Float> SCALE_MAP = new HashMap<>();
	
    @SubscribeEvent
    public static void onRenderEntity(RenderEntityEvent.Pre<? extends Projectile> event) 
    {
    	if(event.getEntity() instanceof Projectile proj)
    	{
    		PoseStack stack = event.getPoseStack();
    		if(SCALE_MAP.containsKey(proj))
    		{
    			float scale = SCALE_MAP.get(proj);
        		stack.scale(scale, scale, scale);
    		}
    	}
    }
	
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) 
    {
        FogType fogType = event.getCamera().getFluidInCamera();
		if(EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_ABYSS.get(), MC.player) > 0 && fogType == FogType.WATER)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(CustomEnchantments.CURSE_OF_ABYSS.get(), MC.player);
            RenderSystem.setShaderFogStart(-8.0F + (level * EnchantmentConfig.abyssCurseFogDensityPerLevel.get()));
            RenderSystem.setShaderFogEnd(50.0F - (level * EnchantmentConfig.abyssCurseFogDensityPerLevel.get()));
		}
    }
}
