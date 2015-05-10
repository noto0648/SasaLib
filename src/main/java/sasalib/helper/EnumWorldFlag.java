package sasalib.helper;

/**
 * Created by Noto on 2015/05/06.
 */
public enum EnumWorldFlag
{
    COMPARATOR_UPDATE(1),
    MARK_UPDATE(2),
    NONE(4);

    private int value;
    private EnumWorldFlag(int id)
    {
        this.value = id;
    }

    public int getValue()
    {
        return value;
    }

}
