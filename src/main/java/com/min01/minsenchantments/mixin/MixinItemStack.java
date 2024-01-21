package com.min01.minsenchantments.mixin;

import java.util.Map.Entry;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EventHandlerForge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
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
		    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		    Multimap<Attribute, AttributeModifier> map = item.getAttributeModifiers(slot, stack);
			if(stack.getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
			{
			    for(Entry<Attribute, AttributeModifier> entry : map.entries())
			    {
			    	if(entry.getKey() != Attributes.ATTACK_SPEED)
			    	{
					    builder.put(entry.getKey(), entry.getValue());
			    	}
			    	else
			    	{
						float speed = stack.getOrCreateTag().getFloat(EventHandlerForge.ACCELERATE);
			    		UUID uuid = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41375_");
						AttributeModifier modifier = new AttributeModifier(uuid, "Accelerate Modifier", entry.getValue().getAmount() + speed, Operation.ADDITION);
					    builder.put(Attributes.ATTACK_SPEED, modifier);
			    	}
			    }
				return builder.build();
			}
			
			if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
			{
				int damage = stack.getDamageValue();
				int level = stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get());
				int count = stack.getOrCreateTag().getInt(EventHandlerForge.SKILLFUL);
				int currentCount = stack.getOrCreateTag().getInt(EventHandlerForge.SKILLFUL_COUNT);
				stack.getOrCreateTag().putInt(EventHandlerForge.SKILLFUL, damage / (EnchantmentConfig.skillfulDurabilityPerLevel.get() / level));
				if(currentCount < count)
				{
					float speed = stack.getOrCreateTag().getFloat(EventHandlerForge.SKILLFUL_SPEED);
					float dmg = stack.getOrCreateTag().getFloat(EventHandlerForge.SKILLFUL_DMG);
					
					if(dmg < level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
					{
						stack.getOrCreateTag().putFloat(EventHandlerForge.SKILLFUL_DMG, dmg + (level * EnchantmentConfig.skillfulDamagePerLevel.get()));
					}
					
					if(dmg > level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
					{
						stack.getOrCreateTag().putFloat(EventHandlerForge.SKILLFUL_DMG, level * EnchantmentConfig.skillfulMaxDamagePerLevel.get());
					}

					if(stack.getItem() instanceof DiggerItem)
					{
						if(speed < level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
						{
							stack.getOrCreateTag().putFloat(EventHandlerForge.SKILLFUL_SPEED, speed + (level * EnchantmentConfig.skillfulSpeedPerLevel.get()));
						}
						
						if(speed > level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
						{
							stack.getOrCreateTag().putFloat(EventHandlerForge.SKILLFUL_SPEED, level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get());
						}
					}
					stack.getOrCreateTag().putInt(EventHandlerForge.SKILLFUL_COUNT, currentCount + 1);
				}
				
			    for(Entry<Attribute, AttributeModifier> entry : map.entries())
			    {
			    	if(entry.getKey() != Attributes.ATTACK_DAMAGE)
			    	{
					    builder.put(entry.getKey(), entry.getValue());
			    	}
			    	else
			    	{
						float amount = stack.getOrCreateTag().getFloat(EventHandlerForge.SKILLFUL_DMG);
			    		UUID uuid = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41374_");
			    		AttributeModifier modifier = new AttributeModifier(uuid, "Skillful Modifier", entry.getValue().getAmount() + amount, Operation.ADDITION);
			    		builder.put(Attributes.ATTACK_DAMAGE, modifier);
			    	}
			    }
	    		return builder.build();
			}
		}
		return item.getAttributeModifiers(slot, stack);
	}
}
