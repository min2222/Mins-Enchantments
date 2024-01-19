package com.min01.minsenchantments.screen;

import org.joml.Quaternionf;

import com.min01.minsenchantments.menu.EndEnchantmentMenu;
import com.min01.minsenchantments.misc.ClientEventHandlerForge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class EndEnchantmentScreen extends AbstractCustomEnchantmentScreen<EndEnchantmentMenu>
{
	private static final ResourceLocation END_CRYSTAL_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
	private final ModelPart cube;
	private final ModelPart glass;
	public int time;
	
	public EndEnchantmentScreen(EndEnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_)
	{
		super(p_98754_, p_98755_, p_98756_);
		EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
		ModelPart modelpart = modelSet.bakeLayer(ModelLayers.END_CRYSTAL);
		this.glass = modelpart.getChild("glass");
		this.cube = modelpart.getChild("cube");
		this.time = ClientEventHandlerForge.MC.level.random.nextInt(100000);
	}
	
	@Override
	public boolean renderBookModel() 
	{
		return false;
	}
	
	@Override
	public void renderCustom(PoseStack stack, float partialTick, BufferSource multibuffersource$buffersource)
	{
		stack.pushPose();
		float f = getY(this, partialTick);
		float f1 = ((float)this.time + partialTick) * 3.0F;
		VertexConsumer vertexconsumer = multibuffersource$buffersource.getBuffer(RENDER_TYPE);
		stack.pushPose();
		stack.scale(0.875F, 0.875F, 0.875F);
		stack.translate(0.0F, -0.5F, 0.0F);
		int i = OverlayTexture.NO_OVERLAY;

		stack.mulPose(Axis.YP.rotationDegrees(f1));
		stack.translate(0.0F, 1.5F + f / 2.0F, 0.0F);
		stack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		this.glass.render(stack, vertexconsumer, 15728880, i);
		stack.scale(0.875F, 0.875F, 0.875F);
		stack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		stack.mulPose(Axis.YP.rotationDegrees(f1));
		this.glass.render(stack, vertexconsumer, 15728880, i);
		stack.scale(0.875F, 0.875F, 0.875F);
		stack.mulPose((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
		stack.mulPose(Axis.YP.rotationDegrees(f1));
		this.cube.render(stack, vertexconsumer, 15728880, i);
		stack.popPose();
		stack.popPose();
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		++this.time;
	}
	
	public static float getY(EndEnchantmentScreen p_114159_, float p_114160_)
	{
		float f = (float)p_114159_.time + p_114160_;
		float f1 = Mth.sin(f * 0.2F) / 2.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 1.4F;
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.enchant.ender_eye.one" : "container.enchant.ender_eye.many";
	}
}
