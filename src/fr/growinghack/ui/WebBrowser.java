package fr.growinghack.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.util.Font;
import fr.growinghack.websites.GrowingHackSite;
import fr.growinghack.websites.Site;

public class WebBrowser 
{
	public int width, height, x, y;
	public boolean error404 = false;
	private fr.growinghack.application.WebBrowser webBrowser;

	public static ArrayList<Site> websites = new ArrayList<Site>();
	
	public WebBrowser(int x, int y, int w, int h, fr.growinghack.application.WebBrowser webBrowser)
	{
		this.width = w;
		this.height = h;
		this.x = x;
		this.y = y;
		this.webBrowser = webBrowser;
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		for (int s = 0; s < websites.size(); s++) 
		{
			Site site = websites.get(s);
			site.render(batch, x + 4, y - 4, webBrowser.width, webBrowser.height);
		}
			
		if (error404) draw404(batch, x, y);
	}
	
	
	private void draw404(Batch batch, int x, int y) 
	{
		Font.drawMulticolor(Font.getFont(Font.usual, 70), batch, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, new Color[] {Color.RED}, new String[] {"ERROR 404"});
	}

	public void navigate(String url)
	{
		if (url.equals("growinghack.fr")) 
		{
			open(new GrowingHackSite());
		} 
		else 
		{
			websites.clear();
			error404 = true;
		}
	}

	private void open(Site site) 
	{
		error404 = false;
		websites.clear();
		websites.add(site);	
	}
}
