package com.min01.minsenchantments.util;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import com.min01.minsenchantments.api.EnchantmentData;
import com.min01.minsenchantments.api.IMEnchantment;
import com.min01.minsenchantments.api.context.CooldownContext;
import com.min01.minsenchantments.api.context.SummonContext;
import com.min01.minsenchantments.capabilities.IMEnchantmentCapability;
import com.min01.minsenchantments.capabilities.MEnchantmentCapabilityImpl;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistries;

public class MEUtil 
{
	public static void getClientLevel(Consumer<Level> consumer)
	{
		LogicalSidedProvider.CLIENTWORLD.get(LogicalSide.CLIENT).filter(ClientLevel.class::isInstance).ifPresent(level -> 
		{
			consumer.accept(level);
		});
	}
	
	public static Iterable<Entity> getAllEntities(Level level)
	{
		return level.getEntities().getAll();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntityByUUID(Level level, UUID uuid)
	{
		return (T) level.getEntities().get(uuid);
	}
	
	public static void getMEnchantment(Consumer<IMEnchantment> consumer)
	{
		for(Enchantment t : ForgeRegistries.ENCHANTMENTS)
		{
			if(!(t instanceof IMEnchantment enchantment))
			{
				continue;
			}
			consumer.accept(enchantment);
		}
	}
	
	public static void addEnchantmentData(Entity entity, ItemStack stack, Enchantment enchantment)
	{
		addEnchantmentData(entity, stack, new CompoundTag(), enchantment);
	}
	
	public static void addEnchantmentData(Entity entity, CompoundTag tag, Enchantment enchantment)
	{
		addEnchantmentData(entity, ItemStack.EMPTY, tag, enchantment);
	}
	
	public static void addEnchantmentData(Entity entity, ItemStack stack, CompoundTag tag, Enchantment enchantment)
	{
		if(entity instanceof LivingEntity living)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(enchantment, living);
			if(!stack.isEmpty())
			{
				level = stack.getEnchantmentLevel(enchantment);
			}
			addEnchantmentData(entity, stack, level, tag, enchantment);
		}
	}
	
	public static void addEnchantmentData(Entity entity, ItemStack stack, int level, CompoundTag tag, Enchantment enchantment)
	{
		EnchantmentInstance instance = new EnchantmentInstance(enchantment, level);
		boolean flag = !hasEnchantmentData(entity, enchantment);
		if(!stack.isEmpty())
		{
			flag = !hasEnchantmentData(stack, enchantment);
		}
		if(flag)
		{
			addEnchantmentData(entity, stack, new EnchantmentData(instance, tag));
		}
	}
	
	public static void addEnchantmentData(Entity entity, ItemStack stack, EnchantmentData data)
	{
		IMEnchantmentCapability cap = entity.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		if(!stack.isEmpty())
		{
			cap = stack.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		}
		cap.addEnchantmentData(entity, stack, data);
	}
	
	public static void removeEnchantmentData(Entity entity, Enchantment enchantment)
	{
		removeEnchantmentData(entity, ItemStack.EMPTY, enchantment);
	}
	
	public static void removeEnchantmentData(Entity entity, ItemStack stack, Enchantment enchantment)
	{
		IMEnchantmentCapability cap = entity.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		if(!stack.isEmpty())
		{
			cap = stack.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		}
		cap.removeEnchantmentData(entity, stack, enchantment);
	}
	
	public static EnchantmentData getEnchantmentData(Entity entity, Enchantment enchantment)
	{
		IMEnchantmentCapability cap = entity.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		return cap.getEnchantmentData(enchantment);
	}
	
	public static EnchantmentData getEnchantmentData(ItemStack stack, Enchantment enchantment)
	{
		IMEnchantmentCapability cap = stack.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		return cap.getEnchantmentData(enchantment);
	}
	
	public static boolean hasEnchantmentData(Entity entity, Enchantment enchantment)
	{
		IMEnchantmentCapability cap = entity.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		return cap.hasEnchantmentData(enchantment);
	}
	
	public static boolean hasEnchantmentData(ItemStack stack, Enchantment enchantment)
	{
		IMEnchantmentCapability cap = stack.getCapability(MEnchantmentCapabilityImpl.MENCHANTMENTS).orElse(new MEnchantmentCapabilityImpl());
		return cap.hasEnchantmentData(enchantment);
	}
	
	public static void pushContext(ItemStack stack)
	{
		CooldownContext.push(stack);
		SummonContext.push(stack);
	}
	
	public static void popContext()
	{
		CooldownContext.pop();
		SummonContext.pop();
	}
	
	public static boolean canHitEntity(Entity target, Projectile projectile)
	{
		if(!target.canBeHitByProjectile())
		{
			return false;
		}
		//Projectile#getOwner is only return owner on server side, which cause visual desync sometimes;
		UUID ownerUUID = projectile.ownerUUID;
		if(ownerUUID != null)
		{
			Entity owner = MEUtil.getEntityByUUID(projectile.level, ownerUUID);
			if(owner != null)
			{
				return owner != target && !target.isAlliedTo(owner);
			}
		}
		return true;
	}
	
	//copied from MobEffect;
	public static boolean isDurationEffectTickWither(int pDuration, int pAmplifier, int interval)
	{
		int i = interval >> pAmplifier;
		if(i > 0)
		{
			return pDuration % i == 0;
		}
		else 
		{
			return true;
		}
	}
	
	//copied from ExperienceOrb;
	public static int repairPlayerItems(Player pPlayer, int pRepairAmount, int value) 
	{
		Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, pPlayer, ItemStack::isDamaged);
		if(entry != null) 
		{
			ItemStack itemstack = entry.getValue();
			int i = Math.min((int) (value * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
			itemstack.setDamageValue(itemstack.getDamageValue() - i);
			int j = pRepairAmount - durabilityToXp(i);
			return j > 0 ? repairPlayerItems(pPlayer, j, value) : 0;
		}
		else
		{
			return pRepairAmount;
		}
	}
	
	public static int durabilityToXp(int pDurability) 
	{
		return pDurability / 2;
	}
	
	public static Vec3 randomPointAroundBox(AABB box, RandomSource rand, float radius) 
	{
		double x = Mth.lerp(rand.nextDouble(), box.minX, box.maxX) + (rand.nextDouble() - 0.5) * radius;
		double y = Mth.lerp(rand.nextDouble(), box.minY, box.maxY) + (rand.nextDouble() - 0.5) * radius;
		double z = Mth.lerp(rand.nextDouble(), box.minZ, box.maxZ) + (rand.nextDouble() - 0.5) * radius;
		return new Vec3(x, y, z);
	}
	
	public static Vec3 getVelocityTowards(Vec3 from, Vec3 to, float speed)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(speed);
	}
	
	public static float percent(float baseValue, float percent)
	{
		return baseValue * percent / 100.0F;
	}
}
