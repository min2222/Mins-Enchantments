package com.min01.minsenchantments.enchantment.normal;

import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentTakeoff extends AbstractMinsEnchantment
{
	public EnchantmentTakeoff()
	{
		super(Rarity.RARE, CustomEnchantments.ELYTRA, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.takeoffMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.takeoffMinCost.get();
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		if(player.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(CustomEnchantments.TAKEOFF.get()) > 0)
		{
			if(player.getItemBySlot(EquipmentSlot.CHEST).canElytraFly(player))
			{
				if(player.getDeltaMovement().y > 0)
				{
					player.startFallFlying();
				}
			}
		}
	}
}
