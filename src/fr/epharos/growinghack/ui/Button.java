package fr.epharos.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.util.Font;

public class Button 
{
	public int width, height, x, y;
	public String text;
	
	public boolean pressed = false;
	
	public static final Texture border = new Texture(Gdx.files.internal("ui/border.png"));
	public static final Texture inside = new Texture(Gdx.files.internal("ui/inside.png"));
	
	public Button(int x, int y, int w, int h, String text)
	{
		this.width = w;
		this.height = h;
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		if(this.isButtonOver(mouseX, mouseY))
		{
			batch.draw(inside, this.x, this.y, this.width, this.height);
			batch.draw(border, this.x + 2, this.y + 2, this.width - 4, this.height - 4);
		}
		else
		{
			batch.draw(border, this.x, this.y, this.width, this.height);
			batch.draw(inside, this.x + 2, this.y + 2, this.width - 4, this.height - 4);
		}
		
		Font.getFont(Font.usual, 24).draw(batch, text, x + width / 2 - (Font.getFont(Font.usual, 24).getBounds(text).width / 2), y + height / 2 + (Font.getFont(Font.usual, 24).getBounds(text).height / 2));
	}
	
	public boolean isButtonOver(int mouseX, int mouseY)
	{
		mouseY = Gdx.graphics.getHeight() - mouseY;
		return (mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height);
	}
}
