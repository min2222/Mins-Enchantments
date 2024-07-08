package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.blockentity.renderer.BlessmentTableRenderer;
import com.min01.minsenchantments.menu.BlessmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlessmentScreen extends AbstractCustomEnchantmentScreen<BlessmentMenu>
{	
	public int time;
	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));
	private final ModelPart core;
	private final ModelPart out;
	private final ModelPart line;
	private final ModelPart line2;
	private final ModelPart line3;
	
	public BlessmentScreen(BlessmentMenu p_98754_, Inventory p_98755_, Component p_98756_)
	{
		super(p_98754_, p_98755_, p_98756_);
		EntityModelSet entityModelSet = Minecraft.getInstance().getEntityModels();
		this.core = entityModelSet.bakeLayer(BlessmentTableRenderer.LAYER_LOCATION).getChild("core");
		this.out = entityModelSet.bakeLayer(BlessmentTableRenderer.LAYER_LOCATION).getChild("out");
		this.line = entityModelSet.bakeLayer(BlessmentTableRenderer.LAYER_LOCATION).getChild("line");
		this.line2 = entityModelSet.bakeLayer(BlessmentTableRenderer.LAYER_LOCATION).getChild("line2");
		this.line3 = entityModelSet.bakeLayer(BlessmentTableRenderer.LAYER_LOCATION).getChild("line3");
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
		float f1 = ((float)this.time + partialTick) * 3.0F;
		VertexConsumer vertexConsumer = multibuffersource$buffersource.getBuffer(RenderType.entityCutout(new ResourceLocation(MinsEnchantments.MODID, "textures/block/bless_core.png")));
		stack.translate(0, 0.05, 0);
		stack.scale(0.7F, 0.7F, 0.7F);
		stack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		stack.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.core.render(stack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
		stack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		stack.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.out.render(stack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
		stack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		stack.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line.render(stack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
		stack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		stack.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line2.render(stack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
		stack.scale(1.1F, 1.1F, 1.1F);
		stack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
		stack.mulPose(Vector3f.YP.rotationDegrees(f1));
		this.line3.render(stack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
		stack.popPose();
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		++this.time;
	}
	
	@Override
	public String getTransltateStringForRequiredItem(boolean one)
	{
		return one ? "container.bless.holy_emblem.one" : "container.bless.holy_emblem.many";
	}
}
