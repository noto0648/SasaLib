package sasalib.utility;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

/**
 * Created by Noto on 2015/05/07.
 */
public class ItemUtil
{
    private static Random rand = new Random();

    public static boolean equals(ItemStack itemStack1, ItemStack itemStack2)
    {
        return (itemStack1 != null) && (itemStack1.getItem() == itemStack2.getItem()) && (!itemStack2.getHasSubtypes() || itemStack2.getItemDamage() == itemStack1.getItemDamage()) && (ItemStack.areItemStacksEqual(itemStack2, itemStack1));
    }

    public static boolean equalsIgnoreSize(ItemStack itemStack1, ItemStack itemStack2)
    {
        ItemStack par1ItemStack = null, par2ItemStack = null;
        if(itemStack1 != null)
        {
            par1ItemStack = itemStack1.copy();
            par1ItemStack.stackSize = 1;
        }
        if(itemStack2 != null)
        {
            par2ItemStack = itemStack2.copy();
            par2ItemStack.stackSize = 1;
        }
        return (par1ItemStack != null) && (par1ItemStack.getItem() == par2ItemStack.getItem()) && (!par2ItemStack.getHasSubtypes() || par2ItemStack.getItemDamage() == par1ItemStack.getItemDamage()) && (ItemStack.areItemStacksEqual(par2ItemStack, par1ItemStack));
    }

    public static boolean equalsSimple(ItemStack itemStack1, ItemStack itemStack2)
    {
        return (itemStack1 != null) && (itemStack1.getItem() == itemStack2.getItem()) && (!itemStack2.getHasSubtypes() || (itemStack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) || (itemStack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack1.getItemDamage() == OreDictionary.WILDCARD_VALUE)|| itemStack2.getItemDamage() == itemStack1.getItemDamage());
    }

    public static boolean isOreDictionary(ItemStack itemStack, String name)
    {
        itemStack = itemStack.copy();
        itemStack.stackSize = 1;

        List<ItemStack> stacks = OreDictionary.getOres(name);
        if(stacks.size() == 0)
            return false;

        for(int i = 0; i < stacks.size(); i++)
        {
            if(equalsSimple(itemStack, stacks.get(i)))
                return true;
        }
        return false;
    }

    public static ItemStack mergeItemStack(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        if (equals(par1ItemStack, par2ItemStack))
        {
            int stackSize = par1ItemStack.stackSize + par2ItemStack.stackSize;

            if (stackSize <= par2ItemStack.getMaxStackSize())
            {
                par2ItemStack.stackSize = 0;
                par1ItemStack.stackSize = stackSize;
            }
            else if (par1ItemStack.stackSize < par2ItemStack.getMaxStackSize())
            {
                par2ItemStack.stackSize -= par2ItemStack.getMaxStackSize() - par1ItemStack.stackSize;
                par1ItemStack.stackSize = par2ItemStack.getMaxStackSize();
            }
        }
        return par2ItemStack;
    }

    public static void dropItem(ItemStack stack, World world, int x, int y, int z)
    {
        ItemStack itemstack = stack;

        if (itemstack != null)
        {
            float f = rand.nextFloat() * 0.8F + 0.1F;
            float f1 = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityitem;

            for (float f2 = rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
            {
                int j1 = rand.nextInt(21) + 10;

                if (j1 > itemstack.stackSize)
                {
                    j1 = itemstack.stackSize;
                }

                itemstack.stackSize -= j1;
                entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (double)((float)rand.nextGaussian() * f3);
                entityitem.motionY = (double)((float)rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double)((float)rand.nextGaussian() * f3);

                if (itemstack.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                }
            }
        }
    }
}
