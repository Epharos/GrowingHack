package fr.growinghack.icon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Icon
{
	private final Texture icon;
	
	public Icon(String url)
	{
		this.icon = new Texture(Gdx.files.internal(url));
	}
	
	public Texture getTexture()
	{
		return this.icon;
	}
}
