package sasalib.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import sasalib.SasaLib;

/**
 * Created by Noto on 2015/05/09.
 */
public class SasaLibBaseEvent extends Event
{
    public final SasaLib modContainer;

    public SasaLibBaseEvent(SasaLib baseObj)
    {
        this.modContainer = baseObj;
    }
}
