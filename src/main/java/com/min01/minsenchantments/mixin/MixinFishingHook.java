package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;

@Mixin(FishingHook.class)
public class MixinFishingHook
{
	@WrapOperation(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootParams$Builder;withLuck(F)Lnet/minecraft/world/level/storage/loot/LootParams$Builder;"))
	private LootParams.Builder minsenchantments$withLuck(LootParams.Builder instance, float pLuck, Operation<LootParams.Builder> original, ItemStack pStack)
	{
		int level = pStack.getEnchantmentLevel(MEnchantments.DEPTH_ANGLER.get());
		if(level > 0)
		{
			FishingHook hook = (FishingHook) (Object) this;
			int depth = level * MEConfig.depthAnglerMaxDepthPerLevel.get();
			float luck = level * MEConfig.depthAnglerLuckPerLevel.get().floatValue();
			int i = 0;
	        BlockPos.MutableBlockPos mutablePos = hook.blockPosition().mutable();
	        do
	        {
				pLuck += luck;
	        	mutablePos.move(Direction.DOWN);
	        	i++;
	        }
	        while(i < depth && hook.level.getFluidState(mutablePos).is(FluidTags.WATER));
		}
		return original.call(instance, pLuck);
	}
}
