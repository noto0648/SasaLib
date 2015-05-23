package sasalib.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;

import java.util.Collection;

/**
 * Created by Noto on 2015/05/17.
 */
public class SimpleIconModel implements IModel
{

    public SimpleIconModel()
    {
    }

    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        return Lists.newArrayList(new ResourceLocation("builtin/generated"));
    }

    @Override
    public Collection<ResourceLocation> getTextures()
    {
        return SasaModelManager.INSTANCE.getTextureList();
    }

    @Override
    public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        if(!Attributes.moreSpecific(format, Attributes.DEFAULT_BAKED_FORMAT))
        {
            throw new IllegalArgumentException("can't bake vanilla models to the format that doesn't fit into the default one: " + format);
        }

        ItemIconModel test = new ItemIconModel(this, state, bakedTextureGetter);

        return test;
    }

    @Override
    public IModelState getDefaultState()
    {
        return ModelRotation.X0_Y0;
    }


}
