package com.min01.minsenchantments.enchantment.ocean;

import com.min01.minsenchantments.capabilities.EnchantmentCapabilities;
import com.min01.minsenchantments.capabilities.EnchantmentCapabilityHandler.EnchantmentData;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.misc.EnchantmentTags;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.Phase;

public class EnchantmentTide extends AbstractOceanEnchantment
{
	public EnchantmentTide()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return this.getMinCost(p_44691_) + EnchantmentConfig.tideMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.tideMinCost.get() + (p_44679_ - 1) * EnchantmentConfig.tideMaxCost.get();
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 5;
	}
	
	@Override
	public void onPlayerTick(Phase phase, Player player) 
	{
		int level = EnchantmentHelper.getEnchantmentLevel(this, player);
		if(level > 0)
		{
			player.getCapability(EnchantmentCapabilities.ENCHANTMENT).ifPresent(t -> 
			{
				if(t.hasEnchantment(this))
				{
					EnchantmentData data = t.getEnchantmentData(this);
					CompoundTag tag = data.getData();
					float tide = tag.getFloat(EnchantmentTags.TIDE);
					double swimSpeed = player.getAttributeBaseValue(ForgeMod.SWIM_SPEED.get());
					float maxSpeed = (float) (swimSpeed + (level * EnchantmentConfig.tideMaxSpeedPerLevel.get()));
					float speed = (level * EnchantmentConfig.tideSpeedPerLevel.get()) / 20;
					if(player.isInWater() && player.isSwimming())
					{
						if(swimSpeed + tide < maxSpeed)
						{
							 tag.putFloat(EnchantmentTags.TIDE, tide + speed);
						}
						player.setDeltaMovement(player.getDeltaMovement().add(tide, 0, tide));
						if(player instanceof ServerPlayer serverPlayer)
						{
							serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
						}
					}
					else
					{
						tag.putFloat(EnchantmentTags.TIDE, 0);
					}
				}
				else
				{
					t.setEnchantmentData(this, new EnchantmentData(level, new CompoundTag()));
				}
			});
		}
	}
}
