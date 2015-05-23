package sasalib.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.SasaLib;

import javax.vecmath.Vector3f;

/**
 * Created by Noto on 2015/05/17.
 */
@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class SimpleIconLoader implements ICustomModelLoader
{
    public static final String DOMAIN = SasaLib.MOD_ID.toLowerCase();
    public static final String SIMPLE_ICON = "simple_icon".toLowerCase();

    public static final ItemTransformVec3f ITEM_THIRD_PERSON_ROTATE = parseVec3(new ItemTransformVec3f(new Vector3f(-90F, 0F, 0F), new Vector3f(0F, 1F, -3F), new Vector3f(0.55F, 0.55F, 0.55F)));
    public static final ItemTransformVec3f ITEM_FIRST_PERSON_ROTATE = parseVec3(new ItemTransformVec3f(new Vector3f(0F, -135F, 25F), new Vector3f(0F, 4F, 2F), new Vector3f(1.7F, 1.7F, 1.7F)));

    public static final ItemCameraTransforms DEFAULT = new ItemCameraTransforms(ITEM_THIRD_PERSON_ROTATE, ITEM_FIRST_PERSON_ROTATE, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);

    @Override
    public boolean accepts(ResourceLocation modelLocation)
    {
        if(modelLocation.getResourceDomain().equalsIgnoreCase(DOMAIN) && modelLocation.getResourcePath().endsWith(SIMPLE_ICON))
        {
            return true;
        }
        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation)
    {
        return new SimpleIconModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {

    }

    public static ItemTransformVec3f parseVec3(ItemTransformVec3f itemTransformVec3f)
    {
        Vector3f vector3f = itemTransformVec3f.rotation;
        Vector3f vector3f1 = itemTransformVec3f.translation;
        vector3f1.scale(0.0625F);
        MathHelper.clamp_double((double) vector3f1.x, -1.5D, 1.5D);
        MathHelper.clamp_double((double)vector3f1.y, -1.5D, 1.5D);
        MathHelper.clamp_double((double)vector3f1.z, -1.5D, 1.5D);
        Vector3f vector3f2 = itemTransformVec3f.scale;
        MathHelper.clamp_double((double)vector3f2.x, -1.5D, 1.5D);
        MathHelper.clamp_double((double)vector3f2.y, -1.5D, 1.5D);
        MathHelper.clamp_double((double)vector3f2.z, -1.5D, 1.5D);
        return new ItemTransformVec3f(vector3f, vector3f1, vector3f2);
    }
}
