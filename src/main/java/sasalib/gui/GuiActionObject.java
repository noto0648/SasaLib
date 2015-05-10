package sasalib.gui;

import sasalib.gui.part.GuiPart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Noto on 2015/05/10.
 */
public class GuiActionObject
{
    private GuiPart partObj;
    private EnumGuiActionType actionType;
    private Map<String, Object> actionProperties = new HashMap<String, Object>();

    public GuiActionObject() {}
    public GuiActionObject(GuiPart part, EnumGuiActionType action)
    {
        partObj = part;
        actionType = action;
    }

    public void addProperty(String label, Object value)
    {
        actionProperties.put(label, value);
    }

    public GuiPart getGuiPart()
    {
        return partObj;
    }

    public EnumGuiActionType getActionType()
    {
        return actionType;
    }

    public Object getProperty(String label)
    {
        if(!actionProperties.containsKey(label))
            return null;
        return actionProperties.get(label);
    }

    @Override
    public String toString()
    {
        return "GuiActionObject";
    }
}
