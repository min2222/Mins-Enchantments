package com.min01.minsenchantments.event;

import com.min01.minsenchantments.MinsEnchantments;
import com.min01.minsenchantments.item.MEItems;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MinsEnchantments.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler 
{
	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent event)
	{
		event.enqueueWork(() -> 
		{
			
		});
	}
	
    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event)
    {
    	MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
    	ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if(tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) 
        {
        	entries.putAfter(Items.ENCHANTING_TABLE.getDefaultInstance(), MEItems.BLESSMENT_TABLE.get().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
        	entries.putAfter(Items.ENCHANTING_TABLE.getDefaultInstance(), MEItems.SCULK_ENCHANTMENT_TABLE.get().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
        	entries.putAfter(Items.ENCHANTING_TABLE.getDefaultInstance(), MEItems.END_ENCHANTMENT_TABLE.get().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
        	entries.putAfter(Items.ENCHANTING_TABLE.getDefaultInstance(), MEItems.NETHER_ENCHANTMENT_TABLE.get().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
        	entries.putAfter(Items.ENCHANTING_TABLE.getDefaultInstance(), MEItems.OCEAN_ENCHANTMENT_TABLE.get().getDefaultInstance(), TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
