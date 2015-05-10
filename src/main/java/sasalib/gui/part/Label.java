package sasalib.gui.part;

/**
 * Created by Noto on 2015/05/10.
 */
public class Label extends GuiPart
{
    private int color;
    private String text;

    public Label()
    {
        super();
        this.color = 0xFFFFFFFF;
    }

    public Label(String text, int color)
    {
        super();
        this.text = text;
        this.color = color;
    }

    public Label(int x, int y, int width, int height, String text, int color)
    {
        super(x, y, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw(int mouseX, int mouseY) {}

    @Override
    public void drawFront(int mouseX, int mouseY)
    {
        if(text == null)
            return;

        fontRenderer.drawString(text, x, y, color);
    }
}
