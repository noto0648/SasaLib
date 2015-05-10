package sasalib.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Noto on 2015/05/10.
 */
@SideOnly(Side.CLIENT)
public interface IGuiPanel
{
    void renderToolTip(ItemStack stack, int x, int y);

    void drawHoveringText(List textLines, int x, int y);

    void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor);

    void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height);

    void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV);

    void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int p_175175_4_, int p_175175_5_);

}
