package com.min01.minsenchantments.enchantment.bless;

import java.util.ArrayList;
import java.util.Collection;

import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.api.event.ItemAttributeModifyEvent;
import com.min01.minsenchantments.config.MEConfig;
import com.min01.minsenchantments.enchantment.MEnchantment;
import com.min01.minsenchantments.util.MEUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MaliceBlessment extends MEnchantment
{
	public MaliceBlessment()
	{
		super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, EnchantmentType.BLESS);
	}
	
	@Override
	public int getMinCost(int pLevel)
	{
		return pLevel * 17;
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
		int level = stack.getEnchantmentLevel(this);
		if(level > 0)
		{
			float percent = level * MEConfig.maliceReductionPerLevel.get().floatValue();
			if(attribute == Attributes.ATTACK_DAMAGE)
			{
				Collection<AttributeModifier> newModifiers = new ArrayList<>();
				modifiers.forEach(t ->
				{
					float reduced = MEUtil.percent((float) t.getAmount(), percent);
					AttributeModifier copy = new AttributeModifier(t.getId(), t.getName(), Math.max(t.getAmount() - reduced, 1), t.getOperation());
					newModifiers.add(copy);
				});
				event.setNewModifiers(newModifiers);
			}
		}
	}
	
	@Override
	public void onLivingHurt(LivingHurtEvent event) 
	{
		LivingEntity living = event.getEntity();
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();
		if(sourceEntity instanceof LivingEntity attacker)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
			if(level > 0)
			{
				double healthPoint = living.getAttributeValue(Attributes.MAX_HEALTH);
				float damage = MEUtil.percent((float) healthPoint, level * MEConfig.malicePercentPerLevel.get().floatValue());
				event.setAmount(event.getAmount() + damage);
			}
		}
	}
}
