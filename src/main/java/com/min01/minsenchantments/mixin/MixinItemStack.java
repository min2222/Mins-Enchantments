package com.min01.minsenchantments.mixin;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@Mixin(ItemStack.class)
public class MixinItemStack
{
	@Redirect(method = "getAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap;"))
	private Multimap<Attribute, AttributeModifier> getAttributeModifiers(Item item, EquipmentSlot slot, ItemStack stack) 
	{
	    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
	    Multimap<Attribute, AttributeModifier> map = item.getAttributeModifiers(slot, stack);
	    
		if(slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET)
		{
			if(item instanceof ArmorItem armorItem)
			{
			    for(Entry<Attribute, AttributeModifier> entry : map.entries())
			    {
			    	if(entry.getKey() != Attributes.ARMOR)
			    	{
					    builder.put(entry.getKey(), entry.getValue());
			    	}
			    	else if(entry.getKey() == Attributes.ARMOR)
			    	{
						float amount = 0;
						if(stack.getEnchantmentLevel(CustomEnchantments.HARDENING.get()) > 0)
						{
							amount += stack.getOrCreateTag().getFloat(EnchantmentTags.HARDENING);
						}
						
						EnumMap<ArmorItem.Type, UUID> enumMap = ObfuscationReflectionHelper.getPrivateValue(ArmorItem.class, armorItem, "f_265987_");
						UUID uuid = enumMap.get(armorItem.getType());
						AttributeModifier modifier = new AttributeModifier(uuid, "Hardening Modifier", entry.getValue().getAmount() + amount, Operation.ADDITION);
					    builder.put(Attributes.ARMOR, modifier);
			    	}
			    }
			}
		}
		
		if(slot == EquipmentSlot.MAINHAND)
		{
		    for(Entry<Attribute, AttributeModifier> entry : map.entries())
		    {
		    	if(entry.getKey() != Attributes.ATTACK_DAMAGE && entry.getKey() != Attributes.ATTACK_SPEED)
		    	{
				    builder.put(entry.getKey(), entry.getValue());
		    	}
		    	else if(entry.getKey() == Attributes.ATTACK_DAMAGE)
		    	{
		    		float amount = 0;
		    		float malice = 0;
					if(stack.getEnchantmentLevel(CustomEnchantments.MALICE.get()) > 0)
					{
			    		int level = stack.getEnchantmentLevel(CustomEnchantments.MALICE.get());
			    		malice = level * EnchantmentConfig.maliceDamagePerLevel.get();
			    		if(entry.getValue().getAmount() - malice <= 0)
			    		{
			    			malice = (float) entry.getValue().getAmount();
			    		}
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.RAGE.get()) > 0)
					{
						amount += stack.getOrCreateTag().getFloat(EnchantmentTags.RAGE);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
					{
						int damage = stack.getDamageValue();
						int level = stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get());
						int count = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL);
						int currentCount = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL_DMG_COUNT);
						stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL, damage / (EnchantmentConfig.skillfulDurabilityPerLevel.get() / level));
						if(currentCount < count)
						{
							float dmg = stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_DMG);
							
							if(dmg < level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
							{
								stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_DMG, dmg + (level * EnchantmentConfig.skillfulDamagePerLevel.get()));
							}
							
							if(dmg > level * EnchantmentConfig.skillfulMaxDamagePerLevel.get())
							{
								stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_DMG, level * EnchantmentConfig.skillfulMaxDamagePerLevel.get());
							}
							stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL_DMG_COUNT, currentCount + 1);
						}
						
						amount += stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_DMG);
					}

		    		UUID uuid = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41374_");
		    		AttributeModifier modifier = new AttributeModifier(uuid, "Enchantment Modifier", (entry.getValue().getAmount() - malice) + amount, Operation.ADDITION);
		    		builder.put(Attributes.ATTACK_DAMAGE, modifier);
		    	}
		    	else if(entry.getKey() == Attributes.ATTACK_SPEED)
		    	{
		    		float amount = 0;
					if(stack.getEnchantmentLevel(CustomEnchantments.ACCELERATE.get()) > 0)
					{
						amount += stack.getOrCreateTag().getFloat(EnchantmentTags.ACCELERATE);
					}
					
					if(stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get()) > 0)
					{
						int damage = stack.getDamageValue();
						int level = stack.getEnchantmentLevel(CustomEnchantments.SKILLFUL.get());
						int count = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL);
						int currentCount = stack.getOrCreateTag().getInt(EnchantmentTags.SKILLFUL_SPEED_COUNT);
						stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL, damage / (EnchantmentConfig.skillfulDurabilityPerLevel.get() / level));
						if(currentCount < count)
						{
							float speed = stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_SPEED);
							if(speed < level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
							{
								stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_SPEED, speed + (level * EnchantmentConfig.skillfulSpeedPerLevel.get()));
							}
							
							if(speed > level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get())
							{
								stack.getOrCreateTag().putFloat(EnchantmentTags.SKILLFUL_SPEED, level * EnchantmentConfig.skillfulMaxSpeedPerLevel.get());
							}
							stack.getOrCreateTag().putInt(EnchantmentTags.SKILLFUL_SPEED_COUNT, currentCount + 1);
						}
						
						amount += stack.getOrCreateTag().getFloat(EnchantmentTags.SKILLFUL_SPEED);
					}
					
		    		UUID uuid = ObfuscationReflectionHelper.getPrivateValue(Item.class, item, "f_41375_");
					AttributeModifier modifier = new AttributeModifier(uuid, "Enchantment Modifier", entry.getValue().getAmount() + amount, Operation.ADDITION);
				    builder.put(Attributes.ATTACK_SPEED, modifier);
		    	}
		    }
			
    		return builder.build();
		}
		return item.getAttributeModifiers(slot, stack);
	}
}
