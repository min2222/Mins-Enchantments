package com.min01.minsenchantments.enchantment.nether;

import java.util.ArrayList;
import java.util.Collection;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ItemAttributeModifyEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.misc.MEnchantmentNbtTagKeys;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HellfireEnchantment extends MEnchantment
{
	public HellfireEnchantment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.NETHER);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 10;
	}
	
	@Override
	public int getMaxCost(int pLevel) 
	{
		return this.getMinCost(pLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 4;
	}
	
	@Override
	public void onItemAttributeModify(ItemAttributeModifyEvent event)
	{
		ItemStack stack = event.getStack();
		Attribute attribute = event.getAttribute();
		Collection<AttributeModifier> modifiers = event.getModifiers();
		EnchantmentData data = MEUtil.getEnchantmentData(stack, this);
		if(data != null)
		{
    		CompoundTag tag = data.tag();
			float damage = tag.getFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE);
			if(attribute == Attributes.ATTACK_DAMAGE)
			{
				Collection<AttributeModifier> newModifiers = new ArrayList<>();
				modifiers.forEach(t ->
				{
					AttributeModifier copy = new AttributeModifier(t.getId(), t.getName(), t.getAmount() + damage, t.getOperation());
					newModifiers.add(copy);
				});
				event.setNewModifiers(newModifiers);
			}
		}
	}
	
	@Override
	public void onItemInventoryTick(LivingEntity entity, ItemStack stack) 
	{
		EnchantmentData data = MEUtil.getEnchantmentData(stack, this);
		if(data != null)
		{
			EnchantmentInstance instance = data.instance();
    		CompoundTag tag = data.tag();
    		int interval = tag.getInt(MEnchantmentNbtTagKeys.HELLFIRE_INTERVAL);
    		if(interval > 0)
    		{
    			tag.putInt(MEnchantmentNbtTagKeys.HELLFIRE_INTERVAL, interval - 1);
    		}
    		else
    		{
    			float damage = tag.getFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE);
    			tag.putFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE, Math.max(damage - 1, 0));
    			tag.putInt(MEnchantmentNbtTagKeys.HELLFIRE_INTERVAL, instance.level * MEConfig.hellfireIntervalPerLevel.get());
    		}
		}
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event)
	{
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();
		if(sourceEntity instanceof LivingEntity attacker)
		{
			Iterable<ItemStack> iterable = this.getSlotItems(attacker).values();
	        for(ItemStack stack : iterable) 
	        {
				int level = stack.getEnchantmentLevel(this);
	        	if(level > 0)
	        	{
	        		EnchantmentData data = MEUtil.getEnchantmentData(stack, this);
	        		float amount = level * MEConfig.hellfireAmountPerLevel.get().floatValue();
	        		float maxAmount = level * MEConfig.hellfireMaxAmountPerLevel.get().floatValue();
	        		int interval = level * MEConfig.hellfireIntervalPerLevel.get();
	        		if(data == null)
	        		{
	        			CompoundTag tag = new CompoundTag();
	        			tag.putFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE, amount);
	        			tag.putInt(MEnchantmentNbtTagKeys.HELLFIRE_INTERVAL, interval);
	        			MEUtil.addEnchantmentData(attacker, stack, tag, this);
	        		}
	        		else
	        		{
	            		CompoundTag tag = data.tag();
	        			float damage = tag.getFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE);
	        			tag.putInt(MEnchantmentNbtTagKeys.HELLFIRE_INTERVAL, interval);
	        			tag.putFloat(MEnchantmentNbtTagKeys.HELLFIRE_ATTACK_DAMAGE, Math.min(damage + amount, maxAmount));
	        		}
	        	}
	        }
		}
	}
}
