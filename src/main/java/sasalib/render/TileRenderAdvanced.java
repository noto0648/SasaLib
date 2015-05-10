package sasalib.render;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 2015/05/09.
 */
@SideOnly(Side.CLIENT)
public class TileRenderAdvanced
{
    public static final TileRenderAdvanced INSTANCE = new TileRenderAdvanced();
    private List<IItemSpecialRenderer> rendererList = new ArrayList<IItemSpecialRenderer>();

    public static boolean renderByItem(TileEntityItemStackRenderer renderer, ItemStack stack)
    {
        return INSTANCE.render(renderer, stack);
    }

    public boolean render(TileEntityItemStackRenderer renderer, ItemStack stack)
    {
        for(int i = 0; i < rendererList.size(); i++)
        {
            boolean result = rendererList.get(i).render(stack);
            if(result)
                return true;
        }
        return false;
    }

    public void addRendererList(IItemSpecialRenderer specialRenderer)
    {
        rendererList.add(specialRenderer);
    }
}
