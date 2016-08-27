package fr.epharos.growinghack.application;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.util.Font;

public class Identity extends Application 
{
	private File file = new File("cache/" + GrowingHack.currentUser.username + ".jpg");
	private Texture userImage = new Texture(Gdx.files.absolute(file.getAbsolutePath()));
	private Texture type = new Texture(Gdx.files.internal(GrowingHack.currentUser.type == 0 ? "ui/hacker.png" : "ui/police.png"));
	
	public Identity()
	{
		super();
		this.setDimension(500, 220 + 30 * Font.getMultiline(Font.hackItalic, GrowingHack.currentUser.description, this.width - 16).length);
		this.minHeight = 0;
		this.minWidth = 0;
	}
	
	public void render(Batch batch, int mouseX, int mouseY) 
	{
		try
		{
			batch.draw(this.userImage, this.x + 8, Gdx.graphics.getHeight() - this.y - 180, 150, 150);
		}
		catch(Exception e)
		{
			
		}
		
		Font.getFont(Font.growing, 27).draw(batch, GrowingHack.currentUser.username, this.x + 206, Gdx.graphics.getHeight() - this.y - 30);
		Font.hackItalic.draw(batch, GrowingHack.currentUser.title, this.x + 170, Gdx.graphics.getHeight() - this.y - 60);
		
		if(GrowingHack.currentUser.type == 0)
		{
			batch.draw(this.type, this.x + 160, Gdx.graphics.getHeight() - this.y - 62);
		}
		else
		{
			batch.draw(this.type, this.x + 166, Gdx.graphics.getHeight() - this.y - 58);
		}
		
		Font.getFont(Font.hack, 27).draw(batch, "Rang ", this.x + 170, Gdx.graphics.getHeight() - this.y - 100);
		Font.getFont(Font.growing, 27).draw(batch, String.valueOf(GrowingHack.currentUser.level), this.x + 170 + Font.getWidth("Rang ", Font.getFont(Font.hack, 27)), Gdx.graphics.getHeight() - this.y - 100);
		
		Font.getFont(Font.hack, 27).draw(batch, "Cash ", this.x + 170, Gdx.graphics.getHeight() - this.y - 130);
		Font.getFont(Font.growing, 27).draw(batch, GrowingHack.currentUser.money + "$", this.x + 170 + Font.getWidth("Cash ", Font.getFont(Font.hack, 27)), Gdx.graphics.getHeight() - this.y - 130);
		
		Font.growingItalic.draw(batch, "Description", this.x + 8, Gdx.graphics.getHeight() - this.y - 190);
		
		String[] desc = Font.getMultiline(Font.hackItalic, GrowingHack.currentUser.description, this.width - 16);
		
		for(int i = 0 ; i < desc.length ; i++)
		{
			Font.hackItalic.drawMultiLine(batch, desc[i], this.x + 8, Gdx.graphics.getHeight() - this.y - 220 - 30 * i);
		}
	}

	public String getAppName() 
	{
		return "Carte d'identité - " + GrowingHack.currentUser.username;
	}
	
	public boolean resizable()
	{
		return false;
	}
	
	public int getAppID() 
	{
		return 1;
	}
}
