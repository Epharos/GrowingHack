package fr.epharos.growinghack.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Font 
{
	public static BitmapFont title;
	public static BitmapFont note;
	public static BitmapFont appName;
	
	public static BitmapFont buttonWindow;
	public static BitmapFont buttonWindowOver;
	
	public static BitmapFont terminalWhite;
	public static BitmapFont terminalGreen;
	
	public static BitmapFont growingTitle;
	public static BitmapFont hackTitle;
	public static BitmapFont growingItalic;
	public static BitmapFont hackItalic;
	
	public static BitmapFont[] usual = new BitmapFont[64];
	public static BitmapFont[] usualOrange = new BitmapFont[64];
	public static BitmapFont[] usualGray = new BitmapFont[64];
	public static BitmapFont[] usualGreen = new BitmapFont[64];
	public static BitmapFont[] usualBlue = new BitmapFont[64];
	public static BitmapFont[] growing = new BitmapFont[64];
	public static BitmapFont[] hack = new BitmapFont[64];
	
	public Font()
	{	
		Font.title = Font.createFont("CeLb", 23, Color.WHITE);
		Font.note = Font.createFont("CeLi", 22, Color.WHITE);
		Font.appName = Font.createFont("CeLb", 15, Color.WHITE);
		
		Font.buttonWindow = Font.createFont("CeLb", 18, Color.WHITE);
		Font.buttonWindowOver = Font.createFont("CeLb", 18, new Color(0.75f, 0.75f, 0.75f, 1f));
		
		Font.terminalWhite = Font.createFont("CeLb", 19, Color.WHITE);
		Font.terminalGreen = Font.createFont("CeLb", 19, new Color(0.7f, 1f, 0.25f, 1f));
		
		Font.growingTitle = Font.createFont("CeLb", 48, new Color(1f, 0.73f, 0.32f, 1f));
		Font.hackTitle = Font.createFont("CeLb", 48, new Color(0.75f, 0.75f, 0.75f, 1f));
		Font.growingItalic = Font.createFont("CeLib", 24, new Color(1f, 0.73f, 0.32f, 1f));
		Font.hackItalic = Font.createFont("CeLib", 24, new Color(0.75f, 0.75f, 0.75f, 1f));
	}
	
	public static BitmapFont createFont(String fontName, int size, Color c)
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/" + fontName + ".ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		font.setColor(c);
		generator.dispose();
		return font;
	}
	
	public static float getWidth(String s, BitmapFont bm)
	{
		return bm.getBounds(s).width;
	}
	
	public static float getHeight(String s, BitmapFont bm)
	{
		return bm.getBounds(s).height;
	}
	
	public static String[] getMultiline(BitmapFont bm, String s, int width)
	{
		List<String> list = new ArrayList<String>();
		int temp = 0;
		
		for(int i = 0 ; i < s.length() ; i++)
		{
			if(s.charAt(i) == ' ')
			{
				temp = i;
			}
			
			if(Font.getWidth(s.substring(0, i), bm) > width)
			{
				list.add(s.substring(0, temp));
				i = 0;
				s = s.substring(temp + 1, s.length());
			}
			
			if(Font.getWidth(s, bm) <= width)
			{
				list.add(s);
				break;
			}
		}
		
		String[] strings = new String[list.size()];
		
		for(int i = 0 ; i < list.size() ; i++)
		{
			strings[i] = list.get(i);
		}
		
		return strings;
	}
	
	public static BitmapFont getFont(BitmapFont[] font, int size)
	{
		if(font[size - 8] == null)
		{
			if(font == Font.usual)
			{
				font[size - 8] = Font.createFont("CeL", size, Color.WHITE);
			}
			
			if(font == Font.usualOrange)
			{
				font[size - 8] = Font.createFont("CeL", size, Color.ORANGE);
			}
			
			if(font == Font.usualGray)
			{
				font[size - 8] = Font.createFont("CeL", size, Color.GRAY);
			}
			
			if(font == Font.usualGreen)
			{
				font[size - 8] = Font.createFont("CeL", size, Color.GREEN);
			}
			
			if(font == Font.usualBlue)
			{
				font[size - 8] = Font.createFont("CeL", size, Color.BLUE);
			}
			
			if(font == Font.growing)
			{
				font[size - 8] = Font.createFont("CeLb", size, new Color(1f, 0.73f, 0.32f, 1f));
			}
			
			if(font == Font.hack)
			{
				font[size - 8] = Font.createFont("CeLb", size, new Color(0.75f, 0.75f, 0.75f, 1f));
			}
		}
		
		return font[size - 8];
	}
	
	public static void draw(Batch batch, BitmapFont font, int i, int j, String text)
	{
		font.draw(batch, text, i, j);
	}
	
	public static void draw(Batch batch, BitmapFont font, int i, int j, String text, int width)
	{
		if(Font.getWidth(text, font) < width)
		{
			Font.draw(batch, font, i, j, text);
			return;
		}
		
		for(int a = 0 ; a < text.length() ; a++)
		{
			if(Font.getWidth(text.substring(0, a + 1), font) >= width)
			{
				Font.draw(batch, font, i, j, text.substring(0, a - 1));
				return;
			}
		}
		
		Font.draw(batch, font, i, j, text);
	}
}
