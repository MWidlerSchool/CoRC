package GUI;

import Item.*;
import Actor.*;
import java.awt.*;

public class PreferencePanel extends ScreenPanel
{
    public static void paint(Graphics2D g2d, Font stringFont, Font terminalFont)
    {
        g2d.setFont(stringFont);
        g2d.setColor(Color.WHITE);
        writeString(g2d, "Preference Panel", 0, 1);
    }
}