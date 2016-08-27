package fr.epharos.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.util.Font;

public class ProgressBar 
{
	public int value = 0;
	
	public static final Texture border = new Texture(Gdx.files.internal("ui/border.png"));
	public static final Texture inside = new Texture(Gdx.files.internal("ui/inside.png"));
	public static final Texture empty = new Texture(Gdx.files.internal("ui/background.png"));
	
	public int width, height, x, y;
	
	public ProgressBar(int x, int y, int w, int h)
	{
		this.width = w;
		this.height = h;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{		
		batch.draw(border, this.x, this.y, this.width, this.height);
		batch.draw(empty, this.x + 2, this.y + 2, this.width - 4, this.height - 4);
		batch.draw(inside, this.x + 2, this.y + 2, this.width - 4 * (value / 100), this.height - 4);
		
		Font.getFont(Font.usual, 24).draw(batch, value + "%", this.x + this.width / 2 - Font.getWidth("100%", Font.getFont(Font.usual, 24)) / 2, this.height / 2 - Font.getHeight("100%", Font.getFont(Font.usual, 24)));
	}
	
	public void updateValue(int i)
	{
		this.value = i;
	}
}
