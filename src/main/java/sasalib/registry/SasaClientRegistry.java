package sasalib.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.render.IItemSpecialRenderer;
import sasalib.render.TileRenderAdvanced;

/**
 * Created by Noto on 2015/05/09.
 */
@SideOnly(Side.CLIENT)
public class SasaClientRegistry
{
    protected static final SasaClientRegistry INSTANCE = new SasaClientRegistry();

    public static void registerItemSpecialRenderer(IItemSpecialRenderer iItemSpecialRenderer)
    {
        TileRenderAdvanced.INSTANCE.addRendererList(iItemSpecialRenderer);
    }

}
