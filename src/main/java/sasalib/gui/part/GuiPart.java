package sasalib.gui.part;

import com.google.common.base.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sasalib.gui.GuiActionObject;
import sasalib.gui.IGui;
import sasalib.gui.IGuiPanel;

/**
 * Created by Noto on 2015/05/10.
 */
@SideOnly(Side.CLIENT)
public abstract class GuiPart
{
    protected FontRenderer fontRenderer;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected boolean visible;
    protected boolean enabled;

    protected IGui parentGui;
    protected IGuiPanel component;

    public GuiPart() {}

    public GuiPart(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(int mouseX, int mouseY);

    public void drawFront(int mouseX, int mouseY) {}

    public void keyTyped(char typedChar, int keyCode) {}

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}

    public void mouseReleased(int mouseX, int mouseY, int state) {}

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    protected boolean doAction(GuiActionObject actionObject)
    {
        if(parentGui != null)
        {
            parentGui.actionPerformed(actionObject);
            return true;
        }
        return false;
    }

    public void setParent(IGui gui)
    {
        this.parentGui = gui;
    }

    public void setGuiPanel(IGuiPanel gui)
    {
        this.component = gui;
    }

    public void setFontRenderer(FontRenderer renderer)
    {
        fontRenderer = renderer;
    }

    public boolean getVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    protected void bindTexture(ResourceLocation resourceLocation)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    public boolean isHover(int mouseX, int mouseY)
    {
        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
    }

    @Override
    public int hashCode()
    {
        return x ^ y ^ width ^ height;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("x", x).add("y", y).add("width", width).add("height", height).toString();
    }
}
