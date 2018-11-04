package GUI;



public class GUIConstants
{
    public static final int[] TILES_PER_SCREEN = {112, 63}; // 16x9
    public static final int DEFAULT_TILE_SIZE = 16;
    public static final String TITLE_STRING = "Champions of Rogue City v0.0.0";
    
    public static final int MAP_DISPLAY_WIDTH = 49;
    public static final int PLAYER_INFO_DISPLAY_WIDTH = (TILES_PER_SCREEN[0] - (MAP_DISPLAY_WIDTH + 3)) / 2;
    public static final int HUD_DISPLAY_WIDTH = PLAYER_INFO_DISPLAY_WIDTH;
    public static final int MESSAGE_DISPLAY_WIDTH = MAP_DISPLAY_WIDTH + PLAYER_INFO_DISPLAY_WIDTH + 1;
    
    public static final int MAP_DISPLAY_HEIGHT = MAP_DISPLAY_WIDTH;
    public static final int MESSAGE_DISPLAY_HEIGHT = TILES_PER_SCREEN[1] - (3 + MAP_DISPLAY_HEIGHT);
    public static final int PLAYER_INFO_DISPLAY_HEIGHT = TILES_PER_SCREEN[1] - (3 + MESSAGE_DISPLAY_HEIGHT);
    public static final int HUD_DISPLAY_HEIGHT = TILES_PER_SCREEN[1] - 3;
    
    public static final int[] MESSAGE_DISPLAY_ORIGIN = {1, 1};
    public static final int[] PLAYER_INFO_DISPLAY_ORIGIN = {1, MESSAGE_DISPLAY_HEIGHT + 2};
    public static final int[] MAP_DISPLAY_ORIGIN = {PLAYER_INFO_DISPLAY_WIDTH + 2, MESSAGE_DISPLAY_HEIGHT + 2};
    public static final int[] HUD_DISPLAY_ORIGIN = {MESSAGE_DISPLAY_WIDTH + 2, 1};
    
    public static final int MAX_MESSAGE_SIZE = MESSAGE_DISPLAY_WIDTH;
}