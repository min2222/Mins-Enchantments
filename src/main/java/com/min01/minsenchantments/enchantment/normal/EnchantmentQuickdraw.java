package com.min01.minsenchantments.enchantment.normal;

import java.lang.reflect.Method;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EnchantmentQuickdraw extends AbstractMinsEnchantment
{
	public EnchantmentQuickdraw()
	{
		super(Rarity.VERY_RARE, CustomEnchantments.PROJECTILE_WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.quickdrawMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.quickdrawMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.quickdrawMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		if(player.isUsingItem())
		{
			ItemStack stack = player.getUseItem();
			int level = stack.getEnchantmentLevel(CustomEnchantments.QUICKDRAW.get());
			if(level > 0)
			{
   				for(int i = 0; i < level; i++) 
				{
					Method m = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_21329_");
					try 
					{
						m.invoke(player);
					}
					catch (Exception e) 
					{
						
					}
				}
			}
		}
	}
}
