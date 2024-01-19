package com.min01.minsenchantments.mixin;

import java.util.Map.Entry;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mixin(ItemStack.class)
public class MixinItemStack
{
	@Redirect(method = "getAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap;"))
	private Multimap<Attribute, AttributeModifier> getAttributeModifiers(Item item, EquipmentSlot slot, ItemStack stack) 
	{
		if(slot == EquipmentSlot.MAINHAND)
		{
			if(stack.getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
			{
				float speed = ItemStack.class.cast(this).getTag().getFloat(EventHandlerForge.ACCELERATE);
			    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			    Multimap<Attribute, AttributeModifier> map = item.getAttributeModifiers(slot, stack);
			    for(Entry<Attribute, AttributeModifier> entry : map.entries())
			    {
			    	if(entry.getKey() != Attributes.ATTACK_SPEED)
			    	{
					    builder.put(entry.getKey(), entry.getValue());
			    	}
			    	else
			    	{
			    		UUID uuid = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41375_");
						AttributeModifier modifier = new AttributeModifier(uuid, "Accelerate Modifier", entry.getValue().getAmount() + speed, Operation.ADDITION);
					    builder.put(Attributes.ATTACK_SPEED, modifier);
			    	}
			    }
				return builder.build();
			}
		}
		return item.getAttributeModifiers(slot, stack);
	}
}
