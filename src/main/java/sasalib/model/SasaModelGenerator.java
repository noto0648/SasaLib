package sasalib.model;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Noto on 2015/05/18.
 */
@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class SasaModelGenerator
{
    public static final SasaModelGenerator INSTANCE = new SasaModelGenerator();

    public ModelBlockWrapper makeItemModel(Function<ResourceLocation, TextureAtlasSprite> textureMapIn, ModelBlockWrapper blockModel)
    {
        HashMap hashmap = Maps.newHashMap();
        ArrayList arraylist = Lists.newArrayList();

        for (int i = 0; i < ItemModelGenerator.LAYERS.size(); ++i)
        {
            String s = (String)ItemModelGenerator.LAYERS.get(i);

            if (!blockModel.isTexturePresent(s))
            {
                break;
            }

            String s1 = blockModel.resolveTextureName(s);
            hashmap.put(s, s1);
            TextureAtlasSprite textureatlassprite = textureMapIn.apply(new ResourceLocation(s1));
            arraylist.addAll(this.makeFace(i, s, textureatlassprite));
        }


        if (arraylist.isEmpty())
        {
            return null;
        }
        else
        {
            hashmap.put("particle", blockModel.isTexturePresent("particle") ? blockModel.resolveTextureName("particle") : (String)hashmap.get("layer0"));
            return new ModelBlockWrapper(arraylist, hashmap, false, false, new ItemCameraTransforms(blockModel.getThirdPersonTransform(), blockModel.getFirstPersonTransform(), blockModel.getHeadTransform(), blockModel.getInGuiTransform()));
        }
    }

    private List<Span> makeFace(int layerNo, String layer, TextureAtlasSprite sprite)
    {
        HashMap hashmap = Maps.newHashMap();
        hashmap.put(EnumFacing.SOUTH, new BlockPartFace(null, layerNo, layer, new BlockFaceUV(new float[] {0.0F, 0.0F, 16.0F, 16.0F}, 0)));
        hashmap.put(EnumFacing.NORTH, new BlockPartFace(null, layerNo, layer, new BlockFaceUV(new float[] {16.0F, 0.0F, 0.0F, 16.0F}, 0)));
        //hashmap.put(EnumFacing.NORTH, new BlockPartFace(null, layerNo, layer, new BlockFaceUV(new float[] {0.0F, 16.0F, 16.0F, 0.0F}, 0)));

        ArrayList arraylist = Lists.newArrayList();
        arraylist.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), hashmap, null, true));
        arraylist.addAll(this.makeSideFaces(sprite, layer, layerNo));
        return arraylist;
    }

    private List<Span> makeSideFaces(TextureAtlasSprite sprite, String layer, int layerNo)
    {
        float f = (float)sprite.getIconWidth();
        float f1 = (float)sprite.getIconHeight();
        ArrayList arraylist = Lists.newArrayList();

        for(Span span : this.getPixelModels(sprite))
        {
            float f2 = 0.0F;
            float f3 = 0.0F;
            float f4 = 0.0F;
            float f5 = 0.0F;
            float f6 = 0.0F;
            float f7 = 0.0F;
            float f8 = 0.0F;
            float f9 = 0.0F;
            float f10 = 0.0F;
            float f11 = 0.0F;
            float f12 = (float)span.func_178385_b();
            float f13 = (float)span.func_178384_c();
            float f14 = (float)span.getYLevel();
            SpanFacing spanfacing = span.getSpanFacing();

            switch (SwitchSpanFacing.values[spanfacing.ordinal()])
            {
                case 1:
                    f6 = f12;
                    f2 = f12;
                    f4 = f7 = f13 + 1.0F;
                    f8 = f14;
                    f3 = f14;
                    f9 = f14;
                    f5 = f14;
                    f10 = 16.0F / f;
                    f11 = 16.0F / (f1 - 1.0F);
                    break;
                case 2:
                    f9 = f14;
                    f8 = f14;
                    f6 = f12;
                    f2 = f12;
                    f4 = f7 = f13 + 1.0F;
                    f3 = f14 + 1.0F;
                    f5 = f14 + 1.0F;
                    f10 = 16.0F / f;
                    f11 = 16.0F / (f1 - 1.0F);
                    break;
                case 3:
                    f6 = f14;
                    f2 = f14;
                    f7 = f14;
                    f4 = f14;
                    f9 = f12;
                    f3 = f12;
                    f5 = f8 = f13 + 1.0F;
                    f10 = 16.0F / (f - 1.0F);
                    f11 = 16.0F / f1;
                    break;
                case 4:
                    f7 = f14;
                    f6 = f14;
                    f2 = f14 + 1.0F;
                    f4 = f14 + 1.0F;
                    f9 = f12;
                    f3 = f12;
                    f5 = f8 = f13 + 1.0F;
                    f10 = 16.0F / (f - 1.0F);
                    f11 = 16.0F / f1;
            }

            float f15 = 16.0F / f;
            float f16 = 16.0F / f1;
            f2 *= f15;
            f4 *= f15;
            f3 *= f16;
            f5 *= f16;
            f3 = 16.0F - f3;
            f5 = 16.0F - f5;
            f6 *= f10;
            f7 *= f10;
            f8 *= f11;
            f9 *= f11;
            HashMap hashmap = Maps.newHashMap();
            hashmap.put(spanfacing.getFacing(), new BlockPartFace(null, layerNo, layer, new BlockFaceUV(new float[] {f6, f8, f7, f9}, 0)));

            switch (SwitchSpanFacing.values[spanfacing.ordinal()])
            {
                case 1:
                    arraylist.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f4, f3, 8.5F), hashmap, null, true));
                    break;
                case 2:
                    arraylist.add(new BlockPart(new Vector3f(f2, f5, 7.5F), new Vector3f(f4, f5, 8.5F), hashmap, null, true));
                    break;
                case 3:
                    arraylist.add(new BlockPart(new Vector3f(f2, f3, 7.5F), new Vector3f(f2, f5, 8.5F), hashmap, null, true));
                    break;
                case 4:
                    arraylist.add(new BlockPart(new Vector3f(f4, f3, 7.5F), new Vector3f(f4, f5, 8.5F), hashmap, null, true));
            }
        }

        return arraylist;
    }

    private List<Span> getPixelModels(TextureAtlasSprite sprite)
    {
        int w = sprite.getIconWidth();
        int h = sprite.getIconHeight();
        ArrayList<Span> spans = Lists.newArrayList();

        for (int frame = 0; frame < sprite.getFrameCount(); ++frame)
        {
            int[] argb = sprite.getFrameTextureData(frame)[0];

            for (int y = 0; y < h; ++y)
            {
                for (int x = 0; x < w; ++x)
                {
                    boolean solid = !this.isOpacity(argb, x, y, w, h);
                    this.genFace(SpanFacing.UP, spans, argb, x, y, w, h, solid);
                    this.genFace(SpanFacing.DOWN, spans, argb, x, y, w, h, solid);
                    this.genFace(SpanFacing.LEFT, spans, argb, x, y, w, h, solid);
                    this.genFace(SpanFacing.RIGHT, spans, argb, x, y, w, h, solid);
                }
            }
        }

        return spans;
    }

    private void genFace(SpanFacing spanFacing, List<Span> spans, int[] argb, int x, int y, int width, int height, boolean solid)
    {
        boolean flag1 = this.isOpacity(argb, x + spanFacing.getOffsetX(), y + spanFacing.getOffsetY(), width, height) && solid;

        if (flag1)
        {
            this.makePixelFace(spans, spanFacing, x, y);
        }
    }

    private void makePixelFace(List<Span> array, SpanFacing spanFacing, int x, int y)
    {
        Span span = null;

        for(Span span1 : array)
        {
            if (span1.getSpanFacing() == spanFacing)
            {
                int k = spanFacing.isUpOrDown() ? y : x;

                if (span1.getYLevel() == k)
                {
                    span = span1;
                    break;
                }
            }
        }

        int l = spanFacing.isUpOrDown() ? y : x;
        int i1 = spanFacing.isUpOrDown() ? x : y;

        if (span == null)
        {
            array.add(new Span(spanFacing, i1, l));
        }
        else
        {
            span.func_178382_a(i1);
        }
    }

    private boolean isOpacity(int[] argb, int x, int y, int width, int height)
    {
        return x >= 0 && y >= 0 && x < width && y < height ? (argb[y * width + x] >> 24 & 255) == 0 : true;
    }

    @SideOnly(Side.CLIENT)
    static class Span
    {
        private final SpanFacing spanFacing;
        private int field_178387_b;
        private int field_178388_c;
        private final int field_178386_d;

        public Span(SpanFacing p_i46216_1_, int p_i46216_2_, int p_i46216_3_)
        {
            this.spanFacing = p_i46216_1_;
            this.field_178387_b = p_i46216_2_;
            this.field_178388_c = p_i46216_2_;
            this.field_178386_d = p_i46216_3_;
        }

        public void func_178382_a(int p_178382_1_)
        {
            if (p_178382_1_ < this.field_178387_b)
            {
                this.field_178387_b = p_178382_1_;
            }
            else if (p_178382_1_ > this.field_178388_c)
            {
                this.field_178388_c = p_178382_1_;
            }
        }

        public SpanFacing getSpanFacing()
        {
            return this.spanFacing;
        }

        public int func_178385_b()
        {
            return this.field_178387_b;
        }

        public int func_178384_c()
        {
            return this.field_178388_c;
        }

        public int getYLevel()
        {
            return this.field_178386_d;
        }
    }

    @SideOnly(Side.CLIENT)
    static enum SpanFacing
    {
        UP(EnumFacing.UP, 0, -1),
        DOWN(EnumFacing.DOWN, 0, 1),
        LEFT(EnumFacing.EAST, -1, 0),
        RIGHT(EnumFacing.WEST, 1, 0);
        private final EnumFacing facing;
        private final int offsetX;
        private final int offsetY;

        private SpanFacing(EnumFacing facing, int normalX, int normalY)
        {
            this.facing = facing;
            this.offsetX = normalX;
            this.offsetY = normalY;
        }

        public EnumFacing getFacing()
        {
            return this.facing;
        }

        public int getOffsetX()
        {
            return this.offsetX;
        }

        public int getOffsetY()
        {
            return this.offsetY;
        }

        private boolean isUpOrDown()
        {
            return this == DOWN || this == UP;
        }
    }

    @SideOnly(Side.CLIENT)
    static final class SwitchSpanFacing
    {
        static final int[] values = new int[SpanFacing.values().length];

        static
        {
            values[SpanFacing.UP.ordinal()] = 1;
            values[SpanFacing.DOWN.ordinal()] = 2;
            values[SpanFacing.LEFT.ordinal()] = 3;
            values[SpanFacing.RIGHT.ordinal()] = 4;
        }
    }

}
