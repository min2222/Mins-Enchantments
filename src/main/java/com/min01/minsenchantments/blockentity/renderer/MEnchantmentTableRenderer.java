package com.min01.minsenchantments.blockentity.renderer;

import com.min01.minsenchantments.block.MEBlocks;
import com.min01.minsenchantments.blockentity.MEnchantmentTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MEnchantmentTableRenderer implements BlockEntityRenderer<MEnchantmentTableBlockEntity>
{
	public final OceanEnchantmentTableRenderer oceanRenderer;
	public final NetherEnchantmentTableRenderer netherRenderer;
	public final EndEnchantmentTableRenderer endRenderer;
	public final BlessmentTableRenderer blessRenderer;
	
	public MEnchantmentTableRenderer(BlockEntityRendererProvider.Context pContext) 
	{
		this.oceanRenderer = new OceanEnchantmentTableRenderer(pContext);
		this.netherRenderer = new NetherEnchantmentTableRenderer(pContext);
		this.endRenderer = new EndEnchantmentTableRenderer(pContext);
		this.blessRenderer = new BlessmentTableRenderer(pContext);
	}

	@Override
	public void render(MEnchantmentTableBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) 
	{
		BlockState state = pBlockEntity.getBlockState();
		if(state.is(MEBlocks.OCEAN_ENCHANTMENT_TABLE.get()))
		{
			this.oceanRenderer.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		}
		else if(state.is(MEBlocks.NETHER_ENCHANTMENT_TABLE.get()))
		{
			this.netherRenderer.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		}
		else if(state.is(MEBlocks.END_ENCHANTMENT_TABLE.get()))
		{
			this.endRenderer.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		}
		else if(state.is(MEBlocks.BLESSMENT_TABLE.get()))
		{
			this.blessRenderer.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		}
	}
}
