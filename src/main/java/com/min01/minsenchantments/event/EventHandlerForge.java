package com.min01.minsenchantments.event;

import java.util.Map;
import java.util.Map.Entry;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.IMinsEnchantment;
import com.min01.minsenchantments.api.IProjectileEnchantment;
import com.min01.minsenchantments.config.EnchantmentConfig;
import com.min01.minsenchantments.init.CustomEnchantments;
import com.min01.minsenchantments.network.EnchantmentNetwork;
import com.min01.minsenchantments.network.PlayerLeftClickPacket;
import com.min01.minsenchantments.util.EnchantmentUtil;
import com.min01.tickrateapi.util.TickrateUtil;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Bus.FORGE)
public class EventHandlerForge
{
	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent event)
	{
		Iterable<Entity> all = EnchantmentUtil.getAllEntities(event.level);
		for(Entity entity : all)
		{
			if(!(entity instanceof LivingEntity) || !entity.getPersistentData().contains("ForceTickCount"))
			{
				continue;
			}
			int time = entity.getPersistentData().getInt("ForceTickCount");
			entity.getPersistentData().putInt("ForceTickCount", time - 1);
			TickrateUtil.changeSubEntities(entity, false);
			TickrateUtil.setTickrate(entity, entity.getPersistentData().getInt("TickrateME"));
			if(time <= 0 || !entity.isAlive())
			{
				entity.getPersistentData().remove("ForceTickCount");
				entity.getPersistentData().remove("TickrateME");
				TickrateUtil.changeSubEntities(entity, true);
				TickrateUtil.resetTickrate(entity);
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event)
	{
		//this event only fire in client side, so no need to check isClientSide
		EnchantmentNetwork.CHANNEL.sendToServer(new PlayerLeftClickPacket());
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onPlayerRightClickItem(event.getEntity(), event.getItemStack(), event.getHand(), event.getPos(), event.getFace());
			}
			
			if(t instanceof IProjectileEnchantment enchantment)
			{
				enchantment.onRightClick(event.getEntity(), event.getItemStack(), event.getHand(), event.getPos(), event.getFace());
			}
		});
	}
	
	@SubscribeEvent
	public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onPlayerRightClickBlock(event.getEntity(), event.getItemStack(), event.getHand(), event.getPos(), event.getHitVec());
			}
		});
	}
	
	@SubscribeEvent
	public static void onPlaySoundAtEntity(PlayLevelSoundEvent.AtEntity event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				if(enchantment.onPlaySoundAtEntity(event.getEntity(), event.getSound(), event.getSource(), event.getOriginalVolume(), event.getOriginalPitch()))
				{
					event.setCanceled(true);
				}
			}
		});
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				int exp = enchantment.onBlockBreak(event.getPos(), event.getState(), event.getPlayer(), event.getExpToDrop());
				event.setExpToDrop(exp);
			}
		});
	}
	
	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				float speed = enchantment.onBreakSpeed(event.getEntity(), event.getState(), event.getOriginalSpeed(), event.getPosition().get());
				event.setNewSpeed(speed);
			}
		});
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onPlayerClone(event.getEntity(), event.getOriginal(), event.isWasDeath());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onLivingDeath(event.getEntity(), event.getSource());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingEntityStartUseItem(LivingEntityUseItemEvent.Start event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				int duration = enchantment.onLivingEntityStartUseItem(event.getEntity(), event.getItem(), event.getDuration());
				event.setDuration(duration);
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingEntityStopUseItem(LivingEntityUseItemEvent.Stop event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				int duration = enchantment.onLivingEntityStopUseItem(event.getEntity(), event.getItem(), event.getDuration());
				event.setDuration(duration);
			}
			
			if(t instanceof IProjectileEnchantment enchantment)
			{
				enchantment.onStopUse(event.getEntity(), event.getItem(), event.getDuration());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingEntityUseItemTick(LivingEntityUseItemEvent.Tick event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				int duration = enchantment.onLivingEntityUseItemTick(event.getEntity(), event.getItem(), event.getDuration());
				event.setDuration(duration);
			}
			
			if(t instanceof IProjectileEnchantment enchantment)
			{
				enchantment.onUseTick(event.getEntity(), event.getItem(), event.getDuration());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onLivingAttack(event.getEntity(), event.getSource(), event.getAmount());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				Pair<Float, Float> pair = enchantment.onLivingFall(event.getEntity(), event.getDistance(), event.getDamageMultiplier());
				event.setDistance(pair.left());
				event.setDamageMultiplier(pair.right());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingBreath(LivingBreatheEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				if(!enchantment.onLivingBreath(event.getEntity(), event.canBreathe(), event.getConsumeAirAmount(), event.getRefillAirAmount(), event.canRefillAir()))
				{
					event.setCanBreathe(false);
				}
			}
		});
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onPlayerTick(event.phase, event.player);
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onLivingTick(event.getEntity());
			}
		});
	}
	
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				if(enchantment.onEntityJoin(event.getEntity(), event.getLevel()))
				{
					event.setCanceled(true);
				}
			}
			
			if(t instanceof IProjectileEnchantment enchantment)
			{
				enchantment.onJoinLevel(event.getEntity(), event.getLevel());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{ 
				Pair<Boolean, Float> pair = enchantment.onLivingDamage(event.getEntity(), event.getSource(), event.getAmount());
				if(pair.left())
				{
					event.setCanceled(true);
				}
				event.setAmount(pair.right());
			}
		});
	}
	
	@SubscribeEvent
	public static void onProjectileImpact(ProjectileImpactEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onProjectileImpact(event.getProjectile(), event.getRayTraceResult());
			}
			
			if(t instanceof IProjectileEnchantment enchantment)
			{
				enchantment.onProjImpact(event.getProjectile(), event.getRayTraceResult());
			}
		});
	}
	
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				enchantment.onEntityTick(event.getEntity());
			}
		});
	}
	
	@SubscribeEvent
	public static void onLivingHeal(LivingHealEvent event)
	{
		CustomEnchantments.LIST.forEach(t -> 
		{
			if(t instanceof IMinsEnchantment enchantment)
			{
				float amount = enchantment.onLivingHeal(event.getEntity(), event.getAmount());
				event.setAmount(amount);
			}
		});
	}
	
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		
    	if(EnchantmentConfig.noIncreasingRepairCost.get())
    	{
    		resetRepairValue(event.getOutput());
    		resetRepairValue(event.getLeft());
    		resetRepairValue(event.getRight());
    	}
		
		if(!left.isEmpty() || !right.isEmpty())
		{
            if(Items.BOOK == right.getItem() && EnchantmentConfig.disenchanting.get()) 
            {
                boolean isEnchantedBook = false;
                ListTag enchTags;
                if(left.getItem() instanceof EnchantedBookItem)
                {
                    enchTags = EnchantedBookItem.getEnchantments(left);
                    isEnchantedBook = true;
                }
                else
                {
                    enchTags = left.getEnchantmentTags();
                }
                
                if(!(enchTags.isEmpty() || (isEnchantedBook && enchTags.size() <= 1))) 
                {
                    ItemStack out = left.copy();
                    ListTag newTags = enchTags.copy();
                    newTags.remove(0);
                    out.addTagElement(isEnchantedBook ? "StoredEnchantments" : "Enchantments", newTags);

                    CompoundTag ench = enchTags.getCompound(0);
                    int cost = getApplyCost(getEnchantmentFromStringId(ench.getString("id")), 1);

                    event.setOutput(out);
                    event.setCost(cost);
                    event.setMaterialCost(1);
                }
            }
            
            if(EnchantmentConfig.anvilOverlevelBooks.get() || EnchantmentConfig.anvilAlwaysAllowBooks.get())
            {
                int cost = 0;
                Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(left);

                for(Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(right).entrySet())
                {
                    Enchantment ench = entry.getKey();
                    int level = entry.getValue();
                    if(null == ench || 0 == level) { continue; }
                    
                    if(canApplyEnchant(left, ench) && right.is(Items.ENCHANTED_BOOK)) 
                    {
                        int currentLevel = 0;
                        if(currentEnchants.containsKey(ench))
                        {
                            currentLevel = currentEnchants.get(ench);
                        }

                        if(currentLevel > level) { continue; }

                        int outLevel = (currentLevel == level) ? level + 1 : level;
                        
                        if(!EnchantmentConfig.anvilOverlevelBooks.get()) 
                        {
                            outLevel = Math.min(outLevel, ench.getMaxLevel());
                        }

                        if(outLevel > currentLevel) 
                        {
                            currentEnchants.put(ench, outLevel);
                            cost += getApplyCost(ench, outLevel);
                        }
                    }
                    
                    if(cost > 0)
                    {
                        ItemStack out = left.copy();
                        EnchantmentHelper.setEnchantments(currentEnchants, out);
                        String name = event.getName();
                        if(name != null && !name.isEmpty() && name != out.getDisplayName().getString())
                        {
                            out.setHoverName(Component.literal(name));
                            cost++;
                        }
                        event.setOutput(out);
                        event.setCost(cost);
                    }
                    else 
                    {
                        event.setCanceled(true);
                    }
                }
            }
		}
    }
    
    public static boolean canApplyEnchant(ItemStack i, Enchantment e)
    {
    	if(EnchantmentConfig.anvilAlwaysAllowBooks.get())
    	{ 
    		return true;
    	}
    	 
        if(Items.ENCHANTED_BOOK == i.getItem()) 
        { 
        	return true; 
        }
        
        if(!e.canEnchant(i)) 
        {
        	return false;
        }
        
        for(Enchantment enchCompare : EnchantmentHelper.getEnchantments(i).keySet()) 
        {
            if(enchCompare != null && enchCompare != e && !enchCompare.isCompatibleWith(e))
            {
            	return false;
            }
        }
        return true;
    }
    
	public static void resetRepairValue(ItemStack stack) 
	{
		if(!stack.isEmpty() && stack.hasTag())
		{
			stack.getTag().remove("RepairCost");
		}
	}
    
    @SubscribeEvent
    public static void onAnvilRepair(AnvilRepairEvent event)  
    {
    	if(EnchantmentConfig.disenchanting.get())
    	{
            if(Items.BOOK == event.getRight().getItem())
            {
                ItemStack left = event.getLeft();
                boolean isEnchantedBook = false;
                ListTag enchTags;
                if(left.getItem() instanceof EnchantedBookItem) 
                {
                    enchTags = EnchantedBookItem.getEnchantments(left);
                    isEnchantedBook = true;
                }
                else 
                {
                    enchTags = left.getEnchantmentTags();
                }
                if(!(enchTags.isEmpty() || (isEnchantedBook && enchTags.size() <= 1))) 
                {
                    CompoundTag ench = enchTags.getCompound(0);

                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    ListTag newTags = new ListTag();
                    newTags.add(ench);
                    book.getOrCreateTag().put("StoredEnchantments", newTags);

                    Player player = event.getEntity();
                    Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), book);
                }
            }
    	}
    }
    
    private static int getApplyCost(Enchantment e, int lvl)
    {
        int rarityFactor =  10; // 10 for unknown rarity - should NEVER happen
                            
        Enchantment.Rarity r = e.getRarity();
        if(Enchantment.Rarity.COMMON == r)         { rarityFactor = 1; }
        else if(Enchantment.Rarity.UNCOMMON == r)   { rarityFactor = 2; }
        else if(Enchantment.Rarity.RARE == r)       { rarityFactor = 4; }
        else if(Enchantment.Rarity.VERY_RARE == r)  { rarityFactor = 8; }
        
        return rarityFactor * lvl;
    }

    static private Enchantment getEnchantmentFromStringId(String id)
    {
        return ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(id));
    }
}
