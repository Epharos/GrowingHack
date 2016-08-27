package fr.epharos.growinghack.os.growos;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.application.Application;
import fr.epharos.growinghack.icon.Identity;
import fr.epharos.growinghack.icon.Terminal;
import fr.epharos.growinghack.icon.WebBrowser;
import fr.epharos.growinghack.os.OS;
import fr.epharos.growinghack.packets.PacketPing;
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.ui.ButtonLabel;
import fr.epharos.growinghack.util.Font;
import fr.epharos.growinghack.util.Timer;

public class GrowOS extends OS 
{		
	public ButtonLabel off = new ButtonLabel(Gdx.graphics.getWidth() - (int) Font.getWidth("OFF", Font.getFont(Font.growing, 27)) - 60, Gdx.graphics.getHeight() - 7, "OFF", Font.getFont(Font.hack, 27), Font.getFont(Font.growing, 27));
	public ButtonLabel settings = new ButtonLabel(Gdx.graphics.getWidth() - (int) Font.getWidth("OFF", Font.getFont(Font.growing, 27)) - 60 - (int) Font.getWidth("PARAMETRES", Font.getFont(Font.growing, 27)) - 20, Gdx.graphics.getHeight() - 7, "PARAMETRES", Font.getFont(Font.hack, 27), Font.getFont(Font.growing, 27));
	public ButtonLabel ping = new ButtonLabel(Gdx.graphics.getWidth() - (int) Font.getWidth("OFF", Font.getFont(Font.growing, 27)) - 60 - (int) Font.getWidth("PARAMETRES", Font.getFont(Font.growing, 27)) - (int) Font.getWidth("000 MS", Font.getFont(Font.growing, 27)) - 40, Gdx.graphics.getHeight() - 7, "PING", Font.getFont(Font.hack, 27), Font.getFont(Font.growing, 27));
	
    private File file = new File("cache/" + GrowingHack.currentUser.username + ".jpg");
    private Texture userImage = new Texture(Gdx.files.absolute(file.getAbsolutePath()));
	
    private int timerPing = Integer.MAX_VALUE;    
    
	public Stage stage;
    
	public GrowOS()
	{
		GrowingHack.currentOS = this;
		
		this.icons.add(new Terminal());
		this.icons.add(new Identity());
		this.icons.add(new WebBrowser());
		
		this.stage = new Stage();
		
		this.stage.addListener(new InputListener()
		{
			public boolean keyTyped (InputEvent event, char key) 
			{
				if(!fr.epharos.growinghack.application.Terminal.freeze)
				{
					fr.epharos.growinghack.application.Terminal.addChar(key);
				}
				
				return true;
			}
			
			public boolean scrolled(InputEvent event, float x, float y, int amount)
			{
				if(GrowingHack.currentOS.applications.get(GrowingHack.currentOS.currentApplication) instanceof fr.epharos.growinghack.application.Terminal)
				{
					fr.epharos.growinghack.application.Terminal.setScrolling(amount);
				}
				
				return false;
			}
		});
		
		Gdx.input.setInputProcessor(this.stage);
	}
	
	public void render(Batch batch, int mouseX, int mouseY) 
	{				
		Timer.update(Gdx.graphics.getDeltaTime());
		
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.40f);
		
		for(int i = 0 ; i < Gdx.graphics.getWidth() ; i++)
		{
			for(int j = 0 ; j < 36 ; j++)
			{
				batch.draw(Button.inside, i, Gdx.graphics.getHeight() - j);
			}
		}
		
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
		
		Font.getFont(Font.hack, 27).draw(batch, "RANG ", 4, Gdx.graphics.getHeight() - 7);
		Font.getFont(Font.growing, 27).draw(batch, String.valueOf(GrowingHack.currentUser.level), Font.getWidth("RANG ", Font.getFont(Font.hack, 27)) + 4, Gdx.graphics.getHeight() - 7);
		
		Font.getFont(Font.hack, 27).draw(batch, "CASH", 100, Gdx.graphics.getHeight() - 7);
		Font.getFont(Font.growing, 27).draw(batch, GrowingHack.currentUser.money + "$", 100 + Font.getWidth("CASH ", Font.getFont(Font.hack, 27)), Gdx.graphics.getHeight() - 7);
		
		this.off.draw(batch, mouseX, mouseY);
		this.settings.draw(batch, mouseX, mouseY);
		this.ping.draw(batch, mouseX, mouseY);
		
		try
		{
			batch.draw(this.userImage, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 33, 30, 30);
		}
		catch(Exception e)
		{
			
		}
		
		int appInALine = Gdx.graphics.getWidth() / 134 - 1;
		int sizeBetweenAppAndBorderLeft = (Gdx.graphics.getWidth() - 134 * appInALine) / 2;
		
		for(int i = 0 ; i < this.icons.size() ; i++)
		{
			batch.draw(this.icons.get(i).icon, 134 * (i % appInALine) + sizeBetweenAppAndBorderLeft, Gdx.graphics.getHeight() - 94 * (i / appInALine) - 120);
			Font.appName.draw(batch, getAppName(this.icons.get(i).getAppName()), 134 * (i % appInALine) + sizeBetweenAppAndBorderLeft + 32 - Font.getWidth(getAppName(this.icons.get(i).getAppName()), Font.appName) / 2, Gdx.graphics.getHeight() - 94 * (i / appInALine) - 124);
			
			if(mouseX >= 134 * (i % appInALine) + sizeBetweenAppAndBorderLeft - 25 && mouseX <= 134 * (i % appInALine) + sizeBetweenAppAndBorderLeft + 89)
			{
				if(mouseY >= 94 * (i / appInALine) + 46 && mouseY <= 94 * (i / appInALine) + 140)
				{
					batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.4f);
					
					for(int a = 0 ; a < 114 ; a++)
					{
						for(int b = 0 ; b < 94 ; b++)
						{
							batch.draw(Button.border, 134 * (i % appInALine) + sizeBetweenAppAndBorderLeft - 25 + a, Gdx.graphics.getHeight() - 94 * (i / appInALine) - 46 - b);
						}
					}
					
					batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
					
					boolean canOpen = true;
					
					if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
					{
						if(!this.applications.isEmpty())
						{
							for(Application app : this.applications)
							{
								if(app.getAppID() == this.icons.get(i).getAppID())
								{
									canOpen = false;
								}
								
								if(!app.canInteractWithOther(mouseX, mouseY))
								{
									canOpen = false;
								}
							}
						}
						
						if(canOpen)
						{
							this.applications.add(this.icons.get(i).openApp());
						}
					}
				}
			}
		}
		
		if(!this.applications.isEmpty())
		{
			for(int i = 0 ; i < this.applications.size() ; i++)
			{
				if(i != this.currentApplication)
				{
					this.applications.get(i).renderApp(batch, mouseX, mouseY, this);
					
					if(!this.applications.get(this.currentApplication).isMouseInside(mouseX, mouseY) && this.applications.get(this.currentApplication).canInteractWithOther(mouseX, mouseY) && this.applications.get(i).isMouseInside(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
					{
						this.currentApplication = i;
					}
				}
			}
			
			this.applications.get(this.currentApplication).renderApp(batch, mouseX, mouseY, this);
			this.applications.get(this.currentApplication).getMouseAction(mouseX, mouseY, this);
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.off.isButtonOver(mouseX, mouseY))
		{
			Gdx.app.exit();
		}
		
		this.timerPing++;
		
		if(this.timerPing >= 180)
		{
			this.timerPing = 0;
			GrowingHack.instance.client.client.sendTCP(new PacketPing());
		}
		
		this.ping.text = GrowingHack.currentUser.ping + " MS";
		this.ping.x = Gdx.graphics.getWidth() - (int) Font.getWidth("OFF", Font.getFont(Font.growing, 27)) - 60 - (int) Font.getWidth("PARAMETRES", Font.getFont(Font.growing, 27)) - (int) Font.getWidth(GrowingHack.currentUser.ping + " MS", Font.getFont(Font.growing, 27)) - 40;
	}
	
	public static String getAppName(String name)
	{
		if(Font.getWidth(name, Font.appName) <= 81)
		{
			return name;
		}
		
		String name1 = "";
		
		for(int i = 0 ; i < name.length() ; i++)
		{
			name1 = name1.concat(String.valueOf(name.charAt(i)));
			
			if(Font.getWidth(name1, Font.appName) >= 81)
			{
				break;
			}
		}
		
		name1 = name1.concat("...");
		
		return name1;
	}

	public String getName() 
	{
		return "GrowOS";
	}
}
