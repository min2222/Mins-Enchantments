package com.min01.minsenchantments.menu;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.min01.minsenchantments.config.EnchantmentConfig;

import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public abstract class AbstractCustomEnchantmentMenu extends AbstractContainerMenu 
{
	private final Container enchantSlots = new SimpleContainer(2)
	{
		public void setChanged()
		{
			super.setChanged();
			AbstractCustomEnchantmentMenu.this.slotsChanged(this);
		}
	};
	private final ContainerLevelAccess access;
	private final RandomSource random = RandomSource.create();
	private final DataSlot enchantmentSeed = DataSlot.standalone();
	public final int[] costs = new int[3];
	public final int[] enchantClue = new int[] { -1, -1, -1 };
	public final int[] levelClue = new int[] { -1, -1, -1 };

	public AbstractCustomEnchantmentMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory pPlayerInventory) 
	{
		this(pMenuType, pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
	}

	public AbstractCustomEnchantmentMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) 
	{
		super(pMenuType, pContainerId);
		this.access = pAccess;
		this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) 
		{
			@Override
			public boolean mayPlace(ItemStack pStack) 
			{
				return true;
			}

			@Override
			public int getMaxStackSize()
			{
				return 1;
			}
		});
		this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) 
		{
			@Override
			public boolean mayPlace(ItemStack pStack)
			{
				return AbstractCustomEnchantmentMenu.this.is(pStack);
			}
		});

		for(int i = 0; i < 3; ++i) 
		{
			for(int j = 0; j < 9; ++j) 
			{
				this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) 
		{
			this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
		}

		this.addDataSlot(DataSlot.shared(this.costs, 0));
		this.addDataSlot(DataSlot.shared(this.costs, 1));
		this.addDataSlot(DataSlot.shared(this.costs, 2));
		this.addDataSlot(this.enchantmentSeed).set(pPlayerInventory.player.getEnchantmentSeed());
		this.addDataSlot(DataSlot.shared(this.enchantClue, 0));
		this.addDataSlot(DataSlot.shared(this.enchantClue, 1));
		this.addDataSlot(DataSlot.shared(this.enchantClue, 2));
		this.addDataSlot(DataSlot.shared(this.levelClue, 0));
		this.addDataSlot(DataSlot.shared(this.levelClue, 1));
		this.addDataSlot(DataSlot.shared(this.levelClue, 2));
	}

	public void slotsChanged(Container pInventory)
	{
		if(pInventory == this.enchantSlots) 
		{
			ItemStack itemstack = pInventory.getItem(0);
			if(!itemstack.isEmpty() && itemstack.isEnchantable()) 
			{
				this.access.execute((pLevel, pPos) -> 
				{
					float j = 0;
					for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS)
					{
						if(EnchantmentTableBlock.isValidBookShelf(pLevel, pPos, blockpos))
						{
							j += pLevel.getBlockState(pPos.offset(blockpos)).getEnchantPowerBonus(pLevel, pPos.offset(blockpos));
						}
					}

					this.random.setSeed((long) this.enchantmentSeed.get());

					for(int k = 0; k < 3; ++k) 
					{
						this.costs[k] = EnchantmentHelper.getEnchantmentCost(this.random, k, (int) j, itemstack);
						this.enchantClue[k] = -1;
						this.levelClue[k] = -1;
						if(this.costs[k] < k + 1)
						{
							this.costs[k] = 0;
						}
						this.costs[k] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(pLevel, pPos, k, (int) j, itemstack, costs[k]);
					}

					for(int l = 0; l < 3; ++l) 
					{
						if(this.costs[l] > 0)
						{
							List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, l, this.costs[l]);
							if(list != null && !list.isEmpty())
							{
								EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
								this.enchantClue[l] = ((ForgeRegistry<Enchantment>) ForgeRegistries.ENCHANTMENTS).getID(enchantmentinstance.enchantment);
								this.levelClue[l] = enchantmentinstance.level;
							}
						}
					}
					this.broadcastChanges();
				});
			} 
			else 
			{
				for(int i = 0; i < 3; ++i) 
				{
					this.costs[i] = 0;
					this.enchantClue[i] = -1;
					this.levelClue[i] = -1;
				}
			}
		}

	}

	@Override
	public boolean clickMenuButton(Player pPlayer, int pId)
	{
		if(pId >= 0 && pId < this.costs.length)
		{
			ItemStack itemstack = this.enchantSlots.getItem(0);
			ItemStack itemstack1 = this.enchantSlots.getItem(1);
			int i = pId + 1;
			if((itemstack1.isEmpty() || itemstack1.getCount() < i) && !pPlayer.getAbilities().instabuild) 
			{
				return false;
			} 
			else if(this.costs[pId] <= 0 || itemstack.isEmpty() || (pPlayer.experienceLevel < i || pPlayer.experienceLevel < this.costs[pId]) && !pPlayer.getAbilities().instabuild) 
			{
				return false;
			}
			else
			{
				this.access.execute((pLevel, pPos) ->
				{
					ItemStack itemstack2 = itemstack;
					List<EnchantmentInstance> list = this.getEnchantmentList(itemstack, pId, this.costs[pId]);
					if(!list.isEmpty()) 
					{
						pPlayer.onEnchantmentPerformed(itemstack, i);
						boolean flag = itemstack.is(Items.BOOK);
						if(flag) 
						{
							itemstack2 = new ItemStack(Items.ENCHANTED_BOOK);
							CompoundTag compoundtag = itemstack.getTag();
							if(compoundtag != null) 
							{
								itemstack2.setTag(compoundtag.copy());
							}

							this.enchantSlots.setItem(0, itemstack2);
						}

						for(int j = 0; j < list.size(); ++j) 
						{
							EnchantmentInstance enchantmentinstance = list.get(j);
							if(flag) 
							{
								EnchantedBookItem.addEnchantment(itemstack2, enchantmentinstance);
							} 
							else
							{
								itemstack2.enchant(enchantmentinstance.enchantment, enchantmentinstance.level);
							}
						}

						if(!pPlayer.getAbilities().instabuild)
						{
							itemstack1.shrink(i);
							if(itemstack1.isEmpty())
							{
								this.enchantSlots.setItem(1, ItemStack.EMPTY);
							}
						}

						pPlayer.awardStat(Stats.ENCHANT_ITEM);
						if(pPlayer instanceof ServerPlayer)
						{
							CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer) pPlayer, itemstack2, i);
						}

						this.enchantSlots.setChanged();
						this.enchantmentSeed.set(pPlayer.getEnchantmentSeed());
						this.slotsChanged(this.enchantSlots);
						pLevel.playSound((Player) null, pPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, pLevel.random.nextFloat() * 0.1F + 0.9F);
					}

				});
				return true;
			}
		}
		else 
		{
			Util.logAndPauseIfInIde(pPlayer.getName() + " pressed invalid button id: " + pId);
			return false;
		}
	}

	private List<EnchantmentInstance> getEnchantmentList(ItemStack pStack, int pEnchantSlot, int pLevel)
	{
		this.random.setSeed((long)(this.enchantmentSeed.get() + pEnchantSlot));
		List<EnchantmentInstance> list = this.selectEnchantment(this.random, pStack, pLevel, false);
		if(pStack.is(Items.BOOK) && list.size() > 1) 
		{
			list.remove(this.random.nextInt(list.size()));
		}
		return list;
	}
	
	public List<EnchantmentInstance> selectEnchantment(RandomSource pRandom, ItemStack pItemStack, int pLevel, boolean pAllowTreasure) 
	{
		List<EnchantmentInstance> list = Lists.newArrayList();
		int i = pItemStack.getEnchantmentValue();
		if(i <= 0) 
		{
			return list;
		}
		else 
		{
			pLevel += 1 + pRandom.nextInt(i / 4 + 1) + pRandom.nextInt(i / 4 + 1);
			float f = (pRandom.nextFloat() + pRandom.nextFloat() - 1.0F) * 0.15F;
			pLevel = Mth.clamp(Math.round((float)pLevel + (float)pLevel * f), 1, Integer.MAX_VALUE);
			List<EnchantmentInstance> list1 = this.getAvailableEnchantmentResults(pLevel, pItemStack, pAllowTreasure);
			if(!list1.isEmpty()) 
			{
				WeightedRandom.getRandomItem(pRandom, list1).ifPresent(list::add);
				while(pRandom.nextInt(50) <= pLevel) 
				{
					if(!list.isEmpty() && !EnchantmentConfig.noEnchantCap.get()) 
					{
						EnchantmentHelper.filterCompatibleEnchantments(list1, Util.lastOf(list));
					}

					if(list1.isEmpty()) 
					{
						break;
					}
					WeightedRandom.getRandomItem(pRandom, list1).ifPresent(list::add);
					pLevel /= 2;
				}
			}
			return list;
		}
	}
	
	public List<EnchantmentInstance> getAvailableEnchantmentResults(int pLevel, ItemStack pStack, boolean pAllowTreasure)
	{
		List<EnchantmentInstance> list = Lists.newArrayList();
		boolean flag = pStack.is(Items.BOOK);
		for(Enchantment enchantment : ForgeRegistries.ENCHANTMENTS)
		{
			if(this.isValidType(enchantment))
			{
				if(enchantment.canApplyAtEnchantingTable(pStack) || (flag && enchantment.isAllowedOnBooks())) 
				{
					for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i)
					{
						if(pLevel >= enchantment.getMinCost(i) && pLevel <= enchantment.getMaxCost(i)) 
						{
							list.add(new EnchantmentInstance(enchantment, i));
							break;
						}
					}
				}
			}
		}
		return list;
	}

	public int getGoldCount()
	{
		ItemStack itemstack = this.enchantSlots.getItem(1);
		return itemstack.isEmpty() ? 0 : itemstack.getCount();
	}

	public int getEnchantmentSeed() 
	{
		return this.enchantmentSeed.get();
	}

	@Override
	public void removed(Player pPlayer)
	{
		super.removed(pPlayer);
		this.access.execute((pLevel, pPos) ->
		{
			this.clearContainer(pPlayer, this.enchantSlots);
		});
	}

	@Override
	public boolean stillValid(Player pPlayer) 
	{
		return stillValid(this.access, pPlayer, this.getBlock());
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pId)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(pId);
		if(slot != null && slot.hasItem()) 
		{
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if(pId == 0) 
			{
				if(!this.moveItemStackTo(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}
			} 
			else if(pId == 1) 
			{
				if(!this.moveItemStackTo(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if(this.is(itemstack1)) 
			{
				if(!this.moveItemStackTo(itemstack1, 1, 2, true))
				{
					return ItemStack.EMPTY;
				}
			} 
			else
			{
				if(this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1))
				{
					return ItemStack.EMPTY;
				}

				ItemStack itemstack2 = itemstack1.copy();
				itemstack2.setCount(1);
				itemstack1.shrink(1);
				this.slots.get(0).set(itemstack2);
			}

			if(itemstack1.isEmpty())
			{
				slot.set(ItemStack.EMPTY);
			}
			else
			{
				slot.setChanged();
			}

			if(itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(pPlayer, itemstack1);
		}

		return itemstack;
	}
	
	public abstract boolean isValidType(Enchantment enchantment);
	
	public abstract Block getBlock();
	
	public abstract boolean is(ItemStack stack);
}
