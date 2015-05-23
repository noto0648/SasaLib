package sasalib.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Noto on 2015/05/18.
 */
@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class ModelBlockWrapper
{
    protected final List elements;
    private final boolean gui3d;
    private final boolean ambientOcclusion;
    private ItemCameraTransforms cameraTransforms;
    public String name;
    public final Map textures;
    public ModelBlockWrapper parent;
    protected ResourceLocation parentLocation;

    public ModelBlockWrapper(List p_i46225_1_, Map p_i46225_2_, boolean p_i46225_3_, boolean p_i46225_4_, ItemCameraTransforms p_i46225_5_)
    {
        this(null, p_i46225_1_, p_i46225_2_, p_i46225_3_, p_i46225_4_, p_i46225_5_);
    }

    public ModelBlockWrapper(ResourceLocation p_i46226_1_, Map p_i46226_2_, boolean p_i46226_3_, boolean p_i46226_4_, ItemCameraTransforms p_i46226_5_)
    {
        this(p_i46226_1_, Collections.emptyList(), p_i46226_2_, p_i46226_3_, p_i46226_4_, p_i46226_5_);
    }

    public ModelBlockWrapper(ResourceLocation p_i46227_1_, List p_i46227_2_, Map p_i46227_3_, boolean p_i46227_4_, boolean p_i46227_5_)
    {
        this(p_i46227_1_, p_i46227_2_, p_i46227_3_, p_i46227_4_, p_i46227_5_, null);
    }

    public ModelBlockWrapper(List p_i46227_2_, Map p_i46227_3_, boolean p_i46227_4_, boolean p_i46227_5_)
    {
        this(null, p_i46227_2_, p_i46227_3_, p_i46227_4_, p_i46227_5_, null);
    }

    protected ModelBlockWrapper(ResourceLocation p_i46227_1_, List p_i46227_2_, Map p_i46227_3_, boolean p_i46227_4_, boolean p_i46227_5_, ItemCameraTransforms p_i46227_6_)
    {
        this.name = "";
        this.elements = p_i46227_2_;
        this.ambientOcclusion = p_i46227_4_;
        this.gui3d = p_i46227_5_;
        this.textures = p_i46227_3_;
        this.parentLocation = p_i46227_1_;
        this.cameraTransforms = p_i46227_6_;
    }

    public List getElements()
    {
        return this.hasParent() ? this.parent.getElements() : this.elements;
    }

    private boolean hasParent()
    {
        return this.parent != null;
    }

    public boolean isAmbientOcclusion()
    {
        return this.hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
    }

    public boolean isGui3d()
    {
        return this.gui3d;
    }

    public boolean isResolved()
    {
        return this.parentLocation == null || this.parent != null && this.parent.isResolved();
    }

    public void getParentFromMap(Map p_178299_1_)
    {
        if (this.parentLocation != null)
        {
            this.parent = (ModelBlockWrapper) p_178299_1_.get(this.parentLocation);
        }
    }


    public boolean isTexturePresent(String p_178300_1_)
    {
        return !"missingno".equals(this.resolveTextureName(p_178300_1_));
    }

    public String resolveTextureName(String p_178308_1_)
    {
        if (!this.startsWithHash(p_178308_1_))
        {
            p_178308_1_ = '#' + p_178308_1_;
        }

        return this.resolveTextureName(p_178308_1_, new Bookkeep(null));
    }

    private String resolveTextureName(String p_178302_1_, Bookkeep p_178302_2_)
    {
        if (this.startsWithHash(p_178302_1_))
        {
            if (this == p_178302_2_.modelExt)
            {
                return "missingno";
            }
            else
            {
                String s1 = (String)this.textures.get(p_178302_1_.substring(1));

                if (s1 == null && this.hasParent())
                {
                    s1 = this.parent.resolveTextureName(p_178302_1_, p_178302_2_);
                }

                p_178302_2_.modelExt = this;

                if (s1 != null && this.startsWithHash(s1))
                {
                    s1 = p_178302_2_.model.resolveTextureName(s1, p_178302_2_);
                }

                return s1 != null && !this.startsWithHash(s1) ? s1 : "missingno";
            }
        }
        else
        {
            return p_178302_1_;
        }
    }

    private boolean startsWithHash(String p_178304_1_)
    {
        return p_178304_1_.charAt(0) == 35;
    }

    public ResourceLocation getParentLocation()
    {
        return this.parentLocation;
    }

    public ModelBlockWrapper getRootModel()
    {
        return this.hasParent() ? this.parent.getRootModel() : this;
    }

    public ItemTransformVec3f getThirdPersonTransform()
    {
        return this.parent != null && this.cameraTransforms.thirdPerson == ItemTransformVec3f.DEFAULT ? this.parent.getThirdPersonTransform() : this.cameraTransforms.thirdPerson;
    }

    public ItemTransformVec3f getFirstPersonTransform()
    {
        return this.parent != null && this.cameraTransforms.firstPerson == ItemTransformVec3f.DEFAULT ? this.parent.getFirstPersonTransform() : this.cameraTransforms.firstPerson;
    }

    public ItemTransformVec3f getHeadTransform()
    {
        return this.parent != null && this.cameraTransforms.head == ItemTransformVec3f.DEFAULT ? this.parent.getHeadTransform() : this.cameraTransforms.head;
    }

    public ItemTransformVec3f getInGuiTransform()
    {
        return this.parent != null && this.cameraTransforms.gui == ItemTransformVec3f.DEFAULT ? this.parent.getInGuiTransform() : this.cameraTransforms.gui;
    }

    public static void checkModelHierarchy(Map p_178312_0_)
    {
        Iterator iterator = p_178312_0_.values().iterator();

        while (iterator.hasNext())
        {
            ModelBlockWrapper modelblock = (ModelBlockWrapper) iterator.next();

            try
            {
                ModelBlockWrapper modelblock1 = modelblock.parent;

                for (ModelBlockWrapper modelblock2 = modelblock1.parent; modelblock1 != modelblock2; modelblock2 = modelblock2.parent.parent)
                {
                    modelblock1 = modelblock1.parent;
                }

            }
            catch (NullPointerException nullpointerexception)
            {

            }
        }

    }

    @SideOnly(Side.CLIENT)
    final class Bookkeep
    {
        public final ModelBlockWrapper model;
        public ModelBlockWrapper modelExt;

        private Bookkeep()
        {
            this.model = ModelBlockWrapper.this;
        }

        Bookkeep(Object p_i46224_2_)
        {
            this();
        }
    }
}
