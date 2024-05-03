package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
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

    @SubscribeEvent
    public static void onRenderEntity(RenderEntityEvent.Pre<? extends Entity> event) 
    {
    	Entity entity = event.getEntity();
    	entity.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
    	{
    		if(t.hasEnchantment(CustomEnchantments.CELL_DIVISION.get()))
    		{
    			float scale = t.getEnchantmentData(CustomEnchantments.CELL_DIVISION.get()).getData().getFloat(EnchantmentTags.CELL_DIVISION_SCALE);
        		event.getPoseStack().scale(scale, scale, scale);
    		}
    	});
    	if(event.getEntity() instanceof LivingEntity living)
    	{
    		living.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
    		{
    			if(t.hasEnchantment(CustomEnchantments.SOUL_FIRE.get()))
    			{
    				renderSoulFlame(event.getPoseStack(), event.getMultiBufferSource(), living);
    			}
    		});
    	}
    }
    
	private static void renderSoulFlame(PoseStack p_114454_, MultiBufferSource p_114455_, Entity p_114456_)
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
		p_114454_.mulPose(Axis.YP.rotationDegrees(-MC.gameRenderer.getMainCamera().getYRot()));
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
	
	private static void fireVertex(PoseStack.Pose p_114415_, VertexConsumer p_114416_, float p_114417_, float p_114418_, float p_114419_, float p_114420_, float p_114421_)
	{
		p_114416_.vertex(p_114415_.pose(), p_114417_, p_114418_, p_114419_).color(255, 255, 255, 255).uv(p_114420_, p_114421_).overlayCoords(0, 10).uv2(240).normal(p_114415_.normal(), 0.0F, 1.0F, 0.0F).endVertex();
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
