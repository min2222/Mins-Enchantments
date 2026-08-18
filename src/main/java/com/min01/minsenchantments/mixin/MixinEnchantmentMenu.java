package com.min01.minsenchantments.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.menu.MEnchantmentMenu;
import com.min01.minsenchantments.misc.MEnchantmentCategory;
import com.min01.minsenchantments.misc.MEnchantmentHelper;

import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;

@Mixin(EnchantmentMenu.class)
public class MixinEnchantmentMenu 
{
	@Shadow
	@Final
	private Container enchantSlots;
	
	@WrapOperation(method = "lambda$slotsChanged$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentCost(Lnet/minecraft/util/RandomSource;IILnet/minecraft/world/item/ItemStack;)I"))
	private int minsenchantments$getEnchantmentCost(RandomSource pRandom, int pEnchantNum, int pPower, ItemStack pStack, Operation<Integer> original)
	{
		EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
		if(menu instanceof MEnchantmentMenu)
		{
			if(MEnchantmentCategory.SHIELD.canEnchant(pStack.getItem()))
			{
				//since shield use iron ingot for crafting recipe;
				//for now, we don't care about modded shields;
				return Tiers.IRON.getEnchantmentValue();
			}
		}
		return original.call(pRandom, pEnchantNum, pPower, pStack);
	}
	
	@WrapOperation(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;", ordinal = 1))
	private Slot minsenchantments$init(EnchantmentMenu instance, Slot slot, Operation<Slot> original)
	{
		if(instance instanceof MEnchantmentMenu menu)
		{
			return original.call(instance, new Slot(this.enchantSlots, 1, 35, 47)
			{
				@Override
				public boolean mayPlace(ItemStack pStack)
				{
					return pStack.is(menu.type.getFuelItem().get());
				}
			});
		}
		return original.call(instance, slot);
	}
	
	@WrapOperation(method = "quickMoveStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean minsenchantments$quickMoveStack(ItemStack instance, TagKey<Item> pTag, Operation<Boolean> original)
	{
		EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
		if(menu instanceof MEnchantmentMenu meMenu)
		{
			return instance.is(meMenu.type.getFuelItem().get());
		}
		return original.call(instance, pTag);
	}
	
	@WrapOperation(method = "getEnchantmentList", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;selectEnchantment(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;IZ)Ljava/util/List;"))
	private List<EnchantmentInstance> minsenchantments$getEnchantmentList(RandomSource pRandom, ItemStack pItemStack, int pLevel, boolean pAllowTreasure, Operation<List<EnchantmentInstance>> original)
	{
		EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
		if(menu instanceof MEnchantmentMenu meMenu)
		{
			return MEnchantmentHelper.selectEnchantment(meMenu.type, pRandom, pItemStack, pLevel);
		}
		return original.call(pRandom, pItemStack, pLevel, pAllowTreasure);
	}
	
	@WrapOperation(method = "stillValid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/EnchantmentMenu;stillValid(Lnet/minecraft/world/inventory/ContainerLevelAccess;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/Block;)Z"))
	private boolean minsenchantments$stillValid(ContainerLevelAccess pAccess, Player pPlayer, Block pTargetBlock, Operation<Boolean> original)
	{
		EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
		if(menu instanceof MEnchantmentMenu meMenu)
		{
			return original.call(pAccess, pPlayer, meMenu.type.getTableBlock().get());
		}
		return original.call(pAccess, pPlayer, pTargetBlock);
	}
}