package sasalib.model;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ITransformation;

import java.util.*;

/**
 * Created by Noto on 2015/05/18.
 */
@SuppressWarnings("deprecation")
public class ItemIconModel extends SimpleItemModelBase
{
    public FaceBakery faceBakery = new FaceBakery();

    public ResourceLocation textureLocation = null;

    private IModelState state;
    private IModel iModel;
    private IBakedModel resultModel;

    private Map<String, TextureAtlasSprite> textures = Maps.newHashMap();
    private Map<String, ModelBlockWrapper> models = Maps.newHashMap();
    private Map<String, ModelBlockWrapper> bakedModels = Maps.newHashMap();

    public ItemIconModel(IModel modelCls, IModelState state, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        super(false, false, null, Attributes.DEFAULT_BAKED_FORMAT, bakedTextureGetter);
        this.textureLocation = null;

        this.state = state;
        this.iModel = modelCls;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBakedModel handleItemState(ItemStack stack)
    {
        if(!textures.containsKey(stack.toString()))
        {
            textureLocation = SasaModelManager.INSTANCE.getResourceLocation(stack);
            textures.put(stack.toString(), textureGetter.apply(textureLocation));
        }

        if(!models.containsKey(stack.toString()))
        {
            Map map = new HashMap();
            map.put("layer0", textureLocation.getResourcePath());
            models.put(stack.toString(), new ModelBlockWrapper(new ResourceLocation("builtin/generated"), map, false, false, SimpleIconLoader.DEFAULT));
        }

        if(!bakedModels.containsKey(stack.toString()))
        {
            bakedModels.put(stack.toString(), SasaModelGenerator.INSTANCE.makeItemModel(textureGetter, models.get(stack.toString())));
        }
        resultModel = bakeModel(bakedModels.get(stack.toString()), state.apply(iModel), true, textures.get(stack.toString()));
        return resultModel;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms()
    {
        return SimpleIconLoader.DEFAULT;
    }

    @Override
    public List<BakedQuad> getFaceQuads(EnumFacing side)
    {
        return resultModel == null ? Collections.EMPTY_LIST : resultModel.getFaceQuads(side);
    }

    @Override
    public List<BakedQuad> getGeneralQuads()
    {
        return resultModel == null ? Collections.EMPTY_LIST : resultModel.getGeneralQuads();
    }

    protected IBakedModel bakeModel(ModelBlockWrapper modelBlockIn, ITransformation modelRotationIn, boolean uvLocked, TextureAtlasSprite texture)
    {
        TextureAtlasSprite textureatlassprite = texture;

        //TextureAtlasSprite textureatlassprite = textureGetter.apply(textureLocation);
        SimpleBakedModel.Builder builder = (new SimpleBakedModel.Builder(this, textureatlassprite)).setTexture(textureatlassprite);
        Iterator iterator = modelBlockIn.getElements().iterator();

        for(BlockPart blockpart : (List<BlockPart>)modelBlockIn.getElements())
        {
            for(EnumFacing enumfacing : ((Map<EnumFacing, BlockPartFace>)blockpart.mapFaces).keySet())
            {
                BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
                TextureAtlasSprite textureatlassprite1 = texture;

                if (blockpartface.cullFace == null || !net.minecraftforge.client.model.TRSRTransformation.isInteger(modelRotationIn.getMatrix()))
                {
                    builder.addGeneralQuad(this.makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
                }
                else
                {
                    builder.addFaceQuad(modelRotationIn.rotate(blockpartface.cullFace), this.makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelRotationIn, uvLocked));
                }
            }
        }

        return builder.makeBakedModel();
    }

    private BakedQuad makeBakedQuad(BlockPart p_177589_1_, BlockPartFace p_177589_2_, TextureAtlasSprite p_177589_3_, EnumFacing p_177589_4_, net.minecraftforge.client.model.ITransformation p_177589_5_, boolean p_177589_6_)
    {
        return this.faceBakery.makeBakedQuad(p_177589_1_.positionFrom, p_177589_1_.positionTo, p_177589_2_, p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.partRotation, p_177589_6_, p_177589_1_.shade);
    }

}
