package com.min01.minsenchantments.enchantment.nether;

import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

public class EnchantmentAutoSmelt extends AbstractNetherEnchantment
{
	public EnchantmentAutoSmelt()
	{
		super(Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxCost(int p_44691_) 
	{
		return EnchantmentConfig.autoSmeltMaxCost.get();
	}
	
	@Override
	public int getMinCost(int p_44679_) 
	{
		return EnchantmentConfig.autoSmeltMinCost.get();
	}
	
	public static class EnchantmentAutoSmeltModifier extends LootModifier 
	{
		public static final Supplier<Codec<EnchantmentAutoSmeltModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, EnchantmentAutoSmeltModifier::new)));
		
		public EnchantmentAutoSmeltModifier(LootItemCondition[] conditionsIn) 
		{
			super(conditionsIn);
		}

		@Override
		protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> originalLoot, LootContext context) 
		{
			ObjectArrayList<ItemStack> newLoot = new ObjectArrayList<>();
			originalLoot.forEach((stack) -> 
			{
				Optional<SmeltingRecipe> optional = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), context.getLevel());
				if (optional.isPresent()) 
				{
					ItemStack smeltedItemStack = optional.get().getResultItem(context.getLevel().registryAccess());
					if (!smeltedItemStack.isEmpty()) 
					{
						ItemStack copy = ItemHandlerHelper.copyStackWithSize(smeltedItemStack, stack.getCount() * smeltedItemStack.getCount());
						newLoot.add(copy);
					}
					else 
					{
						newLoot.add(stack);
					}
				}
				else 
				{
					newLoot.add(stack);
				}
			});
			return newLoot;
		}

		@Override
		public Codec<? extends IGlobalLootModifier> codec() 
		{
			return CODEC.get();
		}
	}
}
