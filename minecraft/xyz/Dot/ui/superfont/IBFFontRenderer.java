package xyz.Dot.ui.superfont;

public interface IBFFontRenderer
{
    StringCache getStringCache();

    void setStringCache(StringCache value);

    boolean isDropShadowEnabled();

    void setDropShadowEnabled(boolean value);

    boolean isEnabled();

    void setEnabled(boolean value);
}
