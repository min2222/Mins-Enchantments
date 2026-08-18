package com.min01.minsenchantments.misc;

import com.min01.minsenchantments.MinsEnchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

public class MEnchantmentTags
{
	public static final TagKey<GameEvent> MOVE = createGameEvent("move");
	public static final TagKey<GameEvent> INTERACTIONS = createGameEvent("interactions");
	
	public static TagKey<GameEvent> createGameEvent(String name) 
	{
		return TagKey.create(Registries.GAME_EVENT, ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, name));
	}
}
