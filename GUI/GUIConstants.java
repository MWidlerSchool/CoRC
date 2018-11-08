package GUI;

import java.awt.*;


public class GUIConstants
{
    public static final int[] TILES_PER_SCREEN = {96, 54}; // 16x9
    public static final int DEFAULT_TILE_SIZE = 18;
    public static final int AT_OFFSET = DEFAULT_TILE_SIZE / 4;
    public static final String TITLE_STRING = "Champions of Rogue City v0.0.0";
    
    public static final int MAP_DISPLAY_WIDTH = 39;
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
    
    
    // colors
    //////////////////////////////////////////////////////////
    public static final Color BLACK = new Color(0, 0, 0);
	public static final Color VERY_DARK_GRAY = new Color(64, 64, 64);
	public static final Color DARK_GRAY = new Color(139, 137, 112);
	public static final Color GRAY = new Color(170, 170, 170);
	public static final Color LIGHT_GRAY = new Color(205, 201, 165);
	public static final Color WHITE = new Color(255, 255, 255);
	
	public static final Color DARK_BLUE = Color.BLUE;
	public static final Color BLUE = new Color(30, 144, 255);
	public static final Color LIGHT_BLUE = new Color(0, 205, 205);
	
	public static final Color DARK_GREEN = new Color(0, 100, 0);
	public static final Color GREEN = new Color(0, 205, 102);
	public static final Color LIGHT_GREEN = new Color(144, 238, 144);
	
	public static final Color BROWN = new Color(205, 133, 0);
	public static final Color DARK_BROWN = new Color(139, 90, 0);
	
	public static final Color RED = new Color(238, 0, 0);
	public static final Color DARK_RED = new Color(138, 0, 0);
	
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color DARK_YELLOW = new Color(238, 238, 0);
	
	public static final Color ORANGE = new Color(255, 128, 0);
	
	public static final Color YELLOW_GREEN = new Color(154, 205, 50);
	
	public static final Color PURPLE = new Color(200, 0, 200);
	
	public static final Color PINK = new Color(255, 192, 203);
	
	// material colors
	public static final Color GOLD = new Color(255, 215, 0);
	public static final Color STEEL = new Color(192, 192, 192);
	public static final Color METAL = new Color(192, 192, 192);
	public static final Color WOOD = new Color(156, 102, 31);
	public static final Color PAPER = new Color(240, 230, 140);
	public static final Color LEATHER = new Color(238, 154, 73);
	public static final Color IVORY = new Color(255, 255, 224);
	public static final Color STONE = DARK_GRAY;
    
    // interface colors
    public static final Color HEALTH_COLOR = DARK_RED;
    public static final Color BLOCK_COLOR = DARK_BLUE;
    public static final Color OOB_COLOR = DARK_GRAY.darker();
    
    
    // chars
    public static final char DIAMOND_CHAR = (char)9830;
	public static final char HEART_CHAR = (char)9829;
	public static final char CLUB_CHAR = (char)9827;
	public static final char SPADE_CHAR = (char)9824;
	public static final char NOTE_CHAR = (char)9834;
	public static final char DOUBLE_NOTE_CHAR = (char)9835;
	public static final char DELTA_CHAR = (char)8710;
	public static final char BULLET_CHAR = (char)183;
	public static final char LARGE_BULLET_CHAR = (char)8226;
	public static final char PLUS_MINUS_CHAR = (char)177;
       
    public static final char BLOCK_100 = (char)9608;
    public static final char BLOCK_75 = (char)9619;
    public static final char BLOCK_50 = (char)9618;
    public static final char BLOCK_25 = (char)9617;
    
    // strings
    public static final String OPEN_DOOR_STRING = "/";
    public static final String CLOSED_DOOR_STRING = "|";
}