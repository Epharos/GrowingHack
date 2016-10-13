package fr.growinghack.files;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.icon.Icon;

public abstract class File 
{
	public ArrayList<String> content = new ArrayList<String>();
	public String content2, name;
	public Icon icon;
	public int i, j;
	
	public abstract String getExtention();
	
	public abstract void open();
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		int textureWidth = this.icon.getTexture().getWidth();
		int textureHeight = this.icon.getTexture().getHeight();
		float k = 64.0f / Math.max(textureWidth, textureHeight);
		float dy = Gdx.graphics.getHeight() - 85 - this.j;
		
		batch.draw(this.icon.getTexture(), i, dy, k * textureWidth, k * textureHeight);
	}
}
