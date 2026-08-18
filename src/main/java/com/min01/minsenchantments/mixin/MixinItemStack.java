package com.min01.minsenchantments.mixin;

import java.util.Collection;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.minsenchantments.api.event.ItemAttributeModifyEvent;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

@Mixin(ItemStack.class)
public class MixinItemStack
{
	@WrapOperation(method = "getAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap;"))
	private Multimap<Attribute, AttributeModifier> minsenchantments$getAttributeModifiers(Item instance, EquipmentSlot equipmentSlot, ItemStack itemStack, Operation<Multimap<Attribute, AttributeModifier>> original)
	{
		Multimap<Attribute, AttributeModifier> multiMap = original.call(instance, equipmentSlot, itemStack);
		Multimap<Attribute, AttributeModifier> mutable = HashMultimap.create(multiMap);
		Map<Attribute, Collection<AttributeModifier>> map = mutable.asMap();
		for(Map.Entry<Attribute, Collection<AttributeModifier>> entry : map.entrySet())
		{
			Attribute attribute = entry.getKey();
			Collection<AttributeModifier> modifiers = entry.getValue();
			ItemAttributeModifyEvent event = new ItemAttributeModifyEvent(itemStack, attribute, modifiers);
			MinecraftForge.EVENT_BUS.post(event);
			Collection<AttributeModifier> newModifiers = event.getNewModifiers();
			if(!newModifiers.isEmpty())
			{
				mutable.replaceValues(attribute, newModifiers);
			}
		}
		return mutable;
	}
	
	@WrapMethod(method = "useOn")
	private InteractionResult minsenchantments$useOn(UseOnContext pContext, Operation<InteractionResult> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		InteractionResult result = original.call(pContext);
		MEUtil.popContext();
		return result;
	}
	
	@WrapMethod(method = "onItemUseFirst", remap = false)
	private InteractionResult minsenchantments$onItemUseFirst(UseOnContext pContext, Operation<InteractionResult> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		InteractionResult result = original.call(pContext);
		MEUtil.popContext();
		return result;
	}

	@WrapMethod(method = "use")
	private InteractionResultHolder<ItemStack> minsenchantments$use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, Operation<InteractionResultHolder<ItemStack>> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		InteractionResultHolder<ItemStack> holder = original.call(pLevel, pPlayer, pUsedHand);
		MEUtil.popContext();
		return holder;
	}
	
	@WrapMethod(method = "finishUsingItem")
	private ItemStack minsenchantments$finishUsingItem(Level pLevel, LivingEntity pLivingEntity, Operation<ItemStack> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		ItemStack origin = original.call(pLevel, pLivingEntity);
		MEUtil.popContext();
		return origin;
	}
	
	@WrapMethod(method = "interactLivingEntity")
	private InteractionResult minsenchantments$interactLivingEntity(Player pPlayer, LivingEntity pEntity, InteractionHand pUsedHand, Operation<InteractionResult> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		InteractionResult result = original.call(pPlayer, pEntity, pUsedHand);
		MEUtil.popContext();
		return result;
	}
	
	@WrapMethod(method = "releaseUsing")
	private void minsenchantments$releaseUsing(Level pLevel, LivingEntity pLivingEntity, int pTimeLeft, Operation<Void> original)
	{
		ItemStack stack = (ItemStack) (Object) this;
		MEUtil.pushContext(stack);
		original.call(pLevel, pLivingEntity, pTimeLeft);
		MEUtil.popContext();
	}
}
