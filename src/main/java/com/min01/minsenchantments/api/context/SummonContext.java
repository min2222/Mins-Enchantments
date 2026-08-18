package com.min01.minsenchantments.api.context;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.min01.minsenchantments.enchantment.MEnchantment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class SummonContext
{
	public static final Deque<SummonStack> STACK = new ArrayDeque<>();

    public static void push(ItemStack stack)
    {
    	SummonStack toPush = new SummonStack(ItemStack.EMPTY, null);
    	if(stack != null && !stack.isEmpty())
    	{
        	for(Enchantment enchantment : stack.getAllEnchantments().keySet())
        	{
        		if(enchantment instanceof MEnchantment mEnchantment && mEnchantment.requiredSummonContext())
                {
            		toPush = new SummonStack(stack.copy(), mEnchantment);
                    break;
                }
        	}
    	}
		STACK.push(toPush);
    }

    public static Optional<SummonStack> peek()
    {
    	SummonStack stack = STACK.peek();
        if(stack != null && !stack.stack.isEmpty())
        {
        	return Optional.of(stack);
        }
        return Optional.empty();
    }

    public static void pop()
    {
    	//it sometimes throw java.util.NoSuchElementException;
    	try
    	{
            if(!STACK.isEmpty())
            {
                STACK.pop();
            }
    	}
    	catch(NoSuchElementException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public static record SummonStack(ItemStack stack, Enchantment enchantment)
    {
    	
    }
}
