package sasalib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import sasalib.utility.ItemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noto on 2015/05/07.
 */
public class FuelRegistry
{
    protected static final FuelRegistry INSTANCE = new FuelRegistry();

    private Map<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
    private FuelHandler fuelHandler;

    private FuelRegistry()
    {
        fuelHandler = new FuelHandler();
    }

    public static void registerFuel(ItemStack itemStack, int burnTime)
    {
        INSTANCE.items.put(itemStack.copy(), burnTime);
    }

    public static void registerFuel(Item item, int burnTime)
    {
        INSTANCE.items.put(new ItemStack(item, 1), burnTime);
    }

    public static void registerFuel(Block block, int burnTime)
    {
        Item item = Item.getItemFromBlock(block);
        if(item != null)
            INSTANCE.items.put(new ItemStack(item, 1), burnTime);
    }

    public FuelHandler getFuelHandler()
    {
        return fuelHandler;
    }

    protected class FuelHandler implements IFuelHandler
    {
        @Override
        public int getBurnTime(ItemStack fuel)
        {
            for(ItemStack stack : items.keySet())
            {
                if(stack != null && fuel != null && ItemUtil.equalsSimple(stack, fuel))
                {
                    return items.get(stack);
                }
            }
            return 0;
        }
    }

}
