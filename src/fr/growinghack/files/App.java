package fr.growinghack.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Application;
import fr.growinghack.util.Font;

public class App extends File 
{
	public String toOpen;
	
	public String getExtention() 
	{
		return null;
	}

	public void open() 
	{
		for(Class<?> c : Application.apps)
		{
			try 
			{
				Application app = (Application) c.newInstance();
				
				if(app.getAppID().equals(this.toOpen))
				{
					GrowingHack.currentOS.applications.add(app);
					return;
				}
			} 
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{		
		Application app = null;
		
		application : for(Class<?> c : Application.apps)
		{
			try 
			{
				app = (Application) c.newInstance();
				
				if(app.getAppID().equals(this.toOpen))
				{
					break application;
				}
			} 
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
		
		if(app != null)
		{
			int textureWidth = app.icon.getWidth();
			int textureHeight = app.icon.getWidth();
			float k = 64.0f / Math.max(textureWidth, textureHeight);
			float dy = Gdx.graphics.getHeight() - 85 - this.j;
			
			try
			{
				textureWidth = app.icon.getWidth();
				textureHeight = app.icon.getHeight();
				k = 64.0f / Math.max(textureWidth, textureHeight);
				dy = Gdx.graphics.getHeight() - 85 - this.j;
					
				batch.draw(app.icon, i + (64.0f / textureWidth) * 64, dy + (64.0f / textureHeight) * 64, k * textureWidth, k * textureHeight);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			Rectangle r1 = new Rectangle();
			Rectangle toDrawIn = new Rectangle(this.i, 0, 104, Gdx.graphics.getHeight());
			Stage stage = new Stage();
			ScissorStack.calculateScissors(stage.getCamera(), batch.getTransformMatrix(), toDrawIn, r1);
			ScissorStack.pushScissors(r1);
			Font.appName.draw(batch, this.name, i + 52 - (Font.getWidth(this.name, Font.appName) <= 104 ? (Font.getWidth(this.name, Font.appName) / 2) : 52), dy + 16);
			batch.flush();
			ScissorStack.popScissors();
		}
	}
}
