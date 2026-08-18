package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.enchantment.MEnchantments;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Player.class)
public class MixinPlayer
{
	//prevent dupe exploit of trident or other similar items;
	@WrapMethod(method = "touch")
	private void minsenchantments$touch(Entity pEntity, Operation<Void> original)
	{
		if(pEntity instanceof ItemEntity itemEntity)
		{
			ItemStack stack = itemEntity.getItem();
			if(stack.hasTag() && stack.getTag().contains(MEnchantmentNbtTagKeys.TERRARIAN_SOUL_SUMMONED))
			{
				itemEntity.discard();
				return;
			}
		}
		else
		{
			EnchantmentData data = MEUtil.getEnchantmentData(pEntity, MEnchantments.TERRARIAN_SOUL.get());
			if(data != null)
			{
				CompoundTag tag = data.tag();
				if(tag.contains(MEnchantmentNbtTagKeys.TERRARIAN_SOUL_SUMMONED))
				{
					pEntity.discard();
					return;
				}
			}
		}
		original.call(pEntity);
	}
}
