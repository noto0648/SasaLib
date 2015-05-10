package sasalib.gui.part;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import sasalib.gui.GuiActionObject;

/**
 * Created by Noto on 2015/05/10.
 */
public class Button extends GuiPart
{
    public static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    private String text;
    private boolean hovered;
    private int packedFGColour;

    public Button()
    {
        super();
    }

    public Button(String text)
    {
        super();
        this.text = text;
        this.width = 200;
        this.height = 20;
    }

    public Button(int x, int y, int width, int height, String text)
    {
        super(x, y, width, height);
        this.text = text;
    }

    @Override
    public void draw(int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = fontRenderer;

            bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            component.drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.width / 2, this.height);
            component.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            //this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.hovered)
                {
                    l = 16777120;
                }

            drawCenteredString(fontrenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, l);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(isHover(mouseX, mouseY))
        {
            GuiActionObject gao = new GuiActionObject();
            gao.addProperty("MouseButton", mouseButton);
            doAction(gao);
        }
    }

    protected int getHoverState(boolean mouseOver)
    {
        byte b0 = 1;

        if (!this.enabled)
        {
            b0 = 0;
        }
        else if (mouseOver)
        {
            b0 = 2;
        }

        return b0;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
