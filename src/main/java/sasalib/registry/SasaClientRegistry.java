package sasalib.registry;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.model.SasaModelManager;
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

    public static void registerSimpleItemIcon(ItemStack target, ResourceLocation location)
    {
        SasaModelManager.INSTANCE.addSimpleIcon(target, location);
        ModelBakery.addVariantName(target.getItem(), SasaModelManager.SIMPLE_ICON);
        ModelLoader.setCustomModelResourceLocation(target.getItem(), target.getMetadata(), new ModelResourceLocation(SasaModelManager.SIMPLE_ICON, "inventory"));

    }
}
