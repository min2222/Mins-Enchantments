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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ObsidianAegisEnchantment extends MEnchantment
{
	public ObsidianAegisEnchantment()
	{
		super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}, EnchantmentType.NETHER);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 15;
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
			float armorPoint = tag.getFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT);
			if(attribute == Attributes.ARMOR)
			{
				Collection<AttributeModifier> newModifiers = new ArrayList<>();
				modifiers.forEach(t ->
				{
					AttributeModifier copy = new AttributeModifier(t.getId(), t.getName(), t.getAmount() + armorPoint, t.getOperation());
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
    		int interval = tag.getInt(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_INTERVAL);
    		if(interval > 0)
    		{
    			tag.putInt(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_INTERVAL, interval - 1);
    		}
    		else
    		{
    			float armorPoint = tag.getFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT);
    			tag.putFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT, Math.max(armorPoint - 1, 0));
    			tag.putInt(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_INTERVAL, instance.level * MEConfig.obsidianAegisIntervalPerLevel.get());
    		}
		}
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event)
	{
		LivingEntity entity = event.getEntity();
		Iterable<ItemStack> iterable = this.getSlotItems(entity).values();
        for(ItemStack stack : iterable) 
        {
        	int level = stack.getEnchantmentLevel(this);
        	if(level > 0)
        	{
        		EnchantmentData data = MEUtil.getEnchantmentData(stack, this);
        		float amount = level * MEConfig.obsidianAegisAmountPerLevel.get().floatValue();
        		float maxAmount = level * MEConfig.obsidianAegisMaxAmountPerLevel.get().floatValue();
        		int interval = level * MEConfig.obsidianAegisIntervalPerLevel.get();
        		if(data == null)
        		{
        			CompoundTag tag = new CompoundTag();
        			tag.putFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT, amount);
        			tag.putInt(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_INTERVAL, interval);
        			MEUtil.addEnchantmentData(entity, stack, tag, this);
        		}
        		else
        		{
            		CompoundTag tag = data.tag();
        			float armorPoint = tag.getFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT);
        			tag.putInt(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_INTERVAL, interval);
        			tag.putFloat(MEnchantmentNbtTagKeys.OBSIDIAN_AEGIS_ARMOR_POINT, Math.min(armorPoint + amount, maxAmount));
        		}
        	}
        }
	}
}
