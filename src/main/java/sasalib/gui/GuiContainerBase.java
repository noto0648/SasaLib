package sasalib.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sasalib.gui.part.GuiPart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noto on 2015/05/10.
 */
public abstract class GuiContainerBase extends GuiContainer implements IGuiPanel, IGui
{
    private ResourceLocation backGroundTexture;

    protected List<GuiPart> components = new ArrayList<GuiPart>();

    public GuiContainerBase(ResourceLocation backGroundTexture, Container container)
    {
        super(container);
        this.backGroundTexture = backGroundTexture;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        components.clear();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(backGroundTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).draw(mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).drawFront(mouseX, mouseY);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).mouseClicked(mouseX - guiTop, mouseY - guiLeft, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).mouseReleased(mouseX - guiTop, mouseY - guiLeft, state);
        }
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).mouseClickMove(mouseX - guiTop, mouseY - guiLeft, clickedMouseButton, timeSinceLastClick);
        }
    }

    public void setSize(int width, int height)
    {
        xSize = width;
        ySize = height;
    }

    @Override
    public void renderToolTip(ItemStack stack, int x, int y)
    {
        super.renderToolTip(stack, x, y);
    }

    @Override
    public void drawHoveringText(List textLines, int x, int y)
    {
        super.drawHoveringText(textLines, x, y);
    }

    @Override
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        super.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }

    @Override
    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        super.drawTexturedModalRect(xCoord, yCoord, minU, minV, maxU, maxV);
    }

    @Override
    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int p_175175_4_, int p_175175_5_)
    {
        super.drawTexturedModalRect(xCoord, yCoord, textureSprite, p_175175_4_, p_175175_5_);
    }
}
