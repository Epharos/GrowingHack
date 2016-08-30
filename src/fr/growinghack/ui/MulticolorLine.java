package fr.growinghack.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import fr.growinghack.util.Font;

public class MulticolorLine 
{
	public Color[] colors;
	public String[] strings;
	
	public BitmapFont bf;
	
	public MulticolorLine(BitmapFont bf, Color[] colors, String ... strings)
	{
		this.bf = bf;
		this.colors = colors;
		this.strings = strings;
	}
	
	public MulticolorLine(BitmapFont bf, Color color, String string)
	{
		this(bf, new Color[] {color}, string);
	}
	
	public MulticolorLine(BitmapFont bf, String string)
	{
		this(bf, bf.getColor(), string);
	}
	
	public void drawMulticolor(Batch batch, int x, int y)
	{		
		if(this.strings.length != this.colors.length)
		{
			System.err.println("Les mots à afficher n'ont pas leur équivalent en couleurs");
			return;
		}
		
		for(int i = 0 ; i < this.strings.length ; i++)
		{
			int totalWidth = 0;
			
			for(int a = 0 ; a < i ; a++)
			{
				totalWidth += Font.getWidth(this.strings[a], bf);
			}
			
			bf.setColor(colors[i]);
			
			Font.draw(batch, bf, x + totalWidth, y, this.strings[i]);
		}
	}
}
