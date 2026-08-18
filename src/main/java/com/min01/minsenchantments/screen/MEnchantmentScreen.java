package com.min01.minsenchantments.screen;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.api.EnchantmentType;
import com.min01.minsenchantments.menu.MEnchantmentMenu;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class MEnchantmentScreen extends EnchantmentScreen
{
	public final RandomSource random = RandomSource.create();
	
	public int tickCount;
	public int time;
	public float activeRotation;
	public boolean isActive;
	
	public MEnchantmentScreen(EnchantmentMenu pMenu, Inventory pPlayerInventory, Component pTitle)
	{
		super(pMenu, pPlayerInventory, pTitle);
		this.time = this.random.nextInt(100000);
	}
	
	@Override
	public void containerTick() 
	{
		super.containerTick();
		++this.time;
		++this.tickCount;
		
		ItemStack stack = this.menu.getSlot(0).getItem();
		ItemStack fuel = this.menu.getSlot(1).getItem();
		
		if(this.menu instanceof MEnchantmentMenu menu)
		{
			this.isActive = !stack.isEmpty() && fuel.is(menu.type.getFuelItem().get());
		}
		
		if(this.isActive)
		{
			++this.activeRotation;
		}
	}
	
	public void render(PoseStack pPoseStack, float pPartialTick, MultiBufferSource pBuffer)
	{
		
	}
	
	public float getY(float pPartialTick, float factor)
	{
		float f = this.time + pPartialTick;
		float f1 = Mth.sin(f * factor) / 2.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.2F;
		return f1 - 1.4F;
	}
	
	public float getActiveRotation(float pPartialTick) 
	{
		return (this.activeRotation + pPartialTick) * -0.0375F;
	}
	
	public ResourceLocation getTableLocation()
	{
		if(this.menu instanceof MEnchantmentMenu menu)
		{
			EnchantmentType type = menu.type;
			switch(type)
			{
			case OCEAN:
				return ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/gui/container/ocean_enchanting_table.png");
			case NETHER:
				return ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/gui/container/nether_enchanting_table.png");
			case END:
				return ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/gui/container/end_enchanting_table.png");
			case SCULK:
				return ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/gui/container/sculk_enchanting_table.png");
			case BLESS:
				return ResourceLocation.fromNamespaceAndPath(MinsEnchantments.MODID, "textures/gui/container/blessing_table.png");
			}
		}
		return ResourceLocation.parse("textures/gui/container/enchanting_table.png");
	}
	
	public String getTranslationKey(boolean isMany)
	{
		String prefix = isMany ? ".many" : ".one";
		if(this.menu instanceof MEnchantmentMenu menu)
		{
			EnchantmentType type = menu.type;
			Item item = type.getFuelItem().get();
			String id = ForgeRegistries.ITEMS.getKey(item).getPath();
			return "container.enchant." + id + prefix;
		}
		return "container.enchant.lapis" + prefix;
	}
}
