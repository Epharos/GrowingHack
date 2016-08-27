package fr.epharos.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import fr.epharos.growinghack.util.Font;

public class ButtonLabel 
{
	public int x, y;
	public String text;
	public BitmapFont bitmapFont, bitmapFont2;
	
	public boolean pressed = false;
	
	public ButtonLabel(int x, int y, String text, BitmapFont bitmap, BitmapFont bitmap2)
	{
		this.x = x;
		this.y = y;
		this.text = text;
		this.bitmapFont = bitmap;
		this.bitmapFont2 = bitmap2;
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		if(this.isButtonOver(mouseX, mouseY))
		{
			this.bitmapFont2.draw(batch, this.text, this.x, this.y);
		}
		else
		{
			this.bitmapFont.draw(batch, this.text, this.x, this.y);
		}
	}
	
	public boolean isButtonOver(int mouseX, int mouseY)
	{
		mouseY = Gdx.graphics.getHeight() - mouseY;
		return (mouseX >= x && mouseX <= x + Font.getWidth(this.text, this.bitmapFont)) && (mouseY <= y && mouseY >= y - Font.getHeight(this.text, this.bitmapFont));
	}
}
