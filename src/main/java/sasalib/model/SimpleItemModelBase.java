package sasalib.model;

import com.google.common.base.Function;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

/**
 * Created by Noto on 2015/05/18.
 */
@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public abstract class SimpleItemModelBase implements IFlexibleBakedModel, ISmartItemModel
{
    private boolean isAmbientOcclusion;
    private boolean isGui3d;
    protected TextureAtlasSprite texture;
    private VertexFormat format;
    protected Function<ResourceLocation, TextureAtlasSprite> textureGetter;

    public SimpleItemModelBase(boolean ambientOcclusion, boolean gui3d, TextureAtlasSprite texture, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        this.isAmbientOcclusion = ambientOcclusion;
        this.isGui3d = gui3d;
        this.texture = texture;
        this.format = format;
        this.textureGetter = bakedTextureGetter;
    }

    @Override
    public List<BakedQuad> getFaceQuads(EnumFacing side)
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<BakedQuad> getGeneralQuads()
    {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return isAmbientOcclusion;
    }

    @Override
    public boolean isGui3d()
    {
        return isGui3d;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getTexture()
    {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms()
    {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public VertexFormat getFormat()
    {
        return format;
    }

}
