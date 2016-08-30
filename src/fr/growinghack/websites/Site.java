package fr.growinghack.websites;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Site {

	private String url;

	public Site() 
	{
		
	}
	
	public abstract void render(Batch batch, int x, int y, int width, int height);
	
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}
}
