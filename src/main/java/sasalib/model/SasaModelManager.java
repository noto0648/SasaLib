package sasalib.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.utility.ItemUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 2015/05/23.
 */
@SideOnly(Side.CLIENT)
public class SasaModelManager
{
    public static final SasaModelManager INSTANCE = new SasaModelManager();

    public static final ResourceLocation MISSING_APPLE = new ResourceLocation("items/apple");
    public static final String SIMPLE_ICON = "sasalib:simple_icon";

    private Map<ItemStack, ResourceLocation> textureMap = Maps.newHashMap();
    private Collection<ResourceLocation> textureList = Lists.newArrayList();

    private SasaModelManager()
    {

    }

    public ResourceLocation getResourceLocation(ItemStack stack)
    {
        for(ItemStack keyStack : textureMap.keySet())
        {
            if(ItemUtil.equalsIgnoreSize(stack, keyStack))
            {
                return textureMap.get(keyStack);
            }
        }

        return MISSING_APPLE;
    }

    public void addSimpleIcon(ItemStack target, ResourceLocation icon)
    {
        textureMap.put(target, icon);
        textureList.add(icon);
    }

    public Collection<ResourceLocation> getTextureList()
    {
        return this.textureList;
    }
}
