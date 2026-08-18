package com.min01.minsenchantments.misc;

import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ToolActions;

public class MEnchantmentCategory 
{
	public static final EnchantmentCategory PROJECTILE_WEAPON = EnchantmentCategory.create("PROJECTILE_WEAPON", t -> t instanceof ProjectileWeaponItem || t instanceof TridentItem);
	public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("SHIELD", t -> t.canPerformAction(t.getDefaultInstance(), ToolActions.SHIELD_BLOCK));
	public static final EnchantmentCategory ALL = EnchantmentCategory.create("ALL", t -> true);
}
