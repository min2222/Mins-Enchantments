package com.min01.minsenchantments.screen;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.min01.minsenchantments.menu.OceanEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class OceanEnchantmentScreen extends AbstractCustomEnchantmentScreen<OceanEnchantmentMenu>
{
	public static final ResourceLocation SHELL_TEXTURE = ResourceLocation.parse("textures/entity/conduit/base.png");
	public static final ResourceLocation ACTIVE_SHELL_TEXTURE = ResourceLocation.parse("textures/entity/conduit/cage.png");
	public static final ResourceLocation OPEN_EYE_TEXTURE = ResourceLocation.parse("textures/entity/conduit/open_eye.png");
	
	private final ModelPart eye;
	private final ModelPart shell;
	private final ModelPart cage;
	
	public int tickCount;
	private float activeRotation;
	private boolean isActive;
	
	public OceanEnchantmentScreen(OceanEnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_)
	{
		super(p_98754_, p_98755_, p_98756_);
		EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
		this.eye = modelSet.bakeLayer(ModelLayers.CONDUIT_EYE);
		this.shell = modelSet.bakeLayer(ModelLayers.CONDUIT_SHELL);
		this.cage = modelSet.bakeLayer(ModelLayers.CONDUIT_CAGE);
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
		++this.tickCount;
		
		if (this.isActive()) 
		{
			++this.activeRotation;
		}
		
		this.isActive = this.menu.is(this.menu.getSlot(1).getItem()) && !this.menu.getSlot(0).getItem().isEmpty();
	}
	
	@Override
	public void renderCustom(PoseStack stack, float partialTick, MultiBufferSource.BufferSource multibuffersource$buffersource) 
	{
		float f = (float)this.tickCount + partialTick;
		if (!this.isActive())
		{
			float f5 = this.getActiveRotation(0.0F);
			VertexConsumer vertexconsumer1 = multibuffersource$buffersource.getBuffer(RenderType.entitySolid(SHELL_TEXTURE));
			stack.pushPose();
			stack.translate(0, 0.8, 0);
			stack.mulPose((new Quaternionf()).rotationY(f5 * ((float)Math.PI / 180F)));
			this.shell.render(stack, vertexconsumer1, 15728880, OverlayTexture.NO_OVERLAY);
			stack.popPose();
		} 
		else
		{
			float f1 = this.getActiveRotation(partialTick) * (180F / (float)Math.PI);
			float f2 = Mth.sin(f * 0.1F) / 2.0F + 0.5F;
			f2 = f2 * f2 + f2;
			stack.pushPose();
			stack.translate(0, 0.25, 0);
			stack.translate(0, (double)(0.1F + f2 * 0.2F), 0);
			Vector3f vector3f = new Vector3f(0.5F, 1.0F, 0.5F);
			stack.mulPose((new Quaternionf()).rotationAxis(f1 * ((float)Math.PI / 180F), vector3f));
			this.cage.render(stack, multibuffersource$buffersource.getBuffer(RenderType.entityCutoutNoCull(ACTIVE_SHELL_TEXTURE)), 15728880, OverlayTexture.NO_OVERLAY);
			stack.popPose();
			
			stack.pushPose();
			stack.translate(0, 0.25, 0);
			stack.translate(0D, (double)(0.1F + f2 * 0.2F), 0D);
			stack.scale(0.5F, 0.5F, 0.5F);
			stack.scale(1.3333334F, 1.3333334F, 1.3333334F);
			this.eye.render(stack, multibuffersource$buffersource.getBuffer(RenderType.entityCutoutNoCull(OPEN_EYE_TEXTURE)), 15728880, OverlayTexture.NO_OVERLAY);
			stack.popPose();
		}
	}
	
	public boolean isActive()
	{
		return this.isActive;
	}

	public float getActiveRotation(float p_59198_)
	{
		return (this.activeRotation + p_59198_) * -0.0375F;
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one) 
	{
		return one ? "container.enchant.prismarine_crystals.one" : "container.enchant.prismarine_crystals.many";
	}
}
