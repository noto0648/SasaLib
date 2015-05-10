package sasalib.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Noto on 2015/05/10.
 */
@SideOnly(Side.CLIENT)
public interface IGui
{
    void actionPerformed(GuiActionObject actionObject);
}
