package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AbstractArrow.class)
public class MixinAbstractArrow 
{
	@ModifyReturnValue(method = "getWaterInertia", at = @At("RETURN"))
	private float minsenchantments$getWaterInertia(float original) 
	{
		AbstractArrow arrow = (AbstractArrow) (Object) this;
		if(MEUtil.hasEnchantmentData(arrow, MEnchantments.HYDRODYNAMICS.get()))
		{
			return 1.0F;
		}
		return original;
	}
	
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
	private BlockState minsenchantments$getBlockState(Level instance, BlockPos pPos, Operation<BlockState> original)
	{
		AbstractArrow arrow = (AbstractArrow) (Object) this;
		if(MEUtil.hasEnchantmentData(arrow, MEnchantments.CHORUS_PIERCING.get()))
		{
			return Blocks.AIR.defaultBlockState();
		}
		return original.call(instance, pPos);
	}
}
