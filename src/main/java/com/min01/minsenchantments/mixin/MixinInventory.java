package com.min01.minsenchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Inventory.class)
public class MixinInventory
{
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;onInventoryTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;II)V"))
	private void minsenchantments$onInventoryTick(ItemStack instance, Level level, Player player, int slotIndex, int selectedIndex, Operation<Void> original)
	{
		MEUtil.pushContext(instance);
		original.call(instance, level, player, slotIndex, selectedIndex);
		MEUtil.popContext();
	}
}
