package fr.growinghack.os.ilak;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Application;
import fr.growinghack.files.FileLoader;
import fr.growinghack.os.OS;
import fr.growinghack.util.Font;
import fr.growinghack.util.Logs;
import fr.growinghack.util.Timer;

public class IlakOS extends OS 
{			
	private Texture offButton = new Texture(Gdx.files.internal("ui/off.png"));
	private Texture backgroundTache = new Texture(Gdx.files.internal("ui/backgroundterminal.png"));
	private Texture volume = new Texture(Gdx.files.internal("ui/volume.png"));
	
    private File avatar = new File("cache/" + GrowingHack.currentUser.username + ".jpg");    
    private Texture userImage;
    
	public Stage stage;
	
	public static java.util.Date date = new java.util.Date();
    
	@SuppressWarnings("deprecation")
	public IlakOS()
	{
		GrowingHack.currentOS = this;
		
		if(this.avatar.exists())
		{
			this.userImage = new Texture(Gdx.files.absolute(this.avatar.getAbsolutePath()));
		}
		else
		{
			this.userImage = new Texture(Gdx.files.internal("user/noavatar.png"));
		}
		
		this.stage = new Stage();
		
		this.stage.addListener(new InputListener()
		{
			public boolean keyTyped (InputEvent event, char key) 
			{
				if(!fr.growinghack.application.Terminal.freeze)
				{
					fr.growinghack.application.Terminal.addChar(key);
				}
				
				if(!fr.growinghack.application.Note.freeze)
				{
					fr.growinghack.application.Note.addChar(key);
				}
				
				return true;
			}
			
			public boolean scrolled(InputEvent event, float x, float y, int amount)
			{
				if(GrowingHack.currentOS.applications.get(GrowingHack.currentOS.currentApplication) instanceof fr.growinghack.application.Terminal)
				{
					fr.growinghack.application.Terminal.setScrolling(amount);
				}
				
				return false;
			}
		});
		
		Gdx.input.setInputProcessor(this.stage);
		
		Application.registerApps();
		FileLoader.load();
		
		Timer time = new Timer("houronscreen", 60f)
		{
			public void execute()
			{
				IlakOS.date = new java.util.Date();		
			}
		};
		
		time.setCurrentTime(IlakOS.date.getSeconds());
	}
	
	@SuppressWarnings("deprecation")
	public void render(Batch batch, int mouseX, int mouseY) 
	{					
		Timer.update(Gdx.graphics.getDeltaTime());
		
		batch.draw(this.backgroundTache, 0, Gdx.graphics.getHeight() - 26, Gdx.graphics.getWidth(), 26);
		
		int nameWidth = (int) Font.getWidth(GrowingHack.currentUser.username, Font.terminal1) + 4;
		
		int xB = Gdx.graphics.getWidth() - 77 - nameWidth;
		int yB =  Gdx.graphics.getHeight() - 22;
		Color origin = batch.getColor();
		
		Font.terminal1.draw(batch, GrowingHack.currentUser.username, xB + 31, Gdx.graphics.getHeight() - 6);
		
		if ((mouseX > xB && mouseX < (xB + offButton.getWidth()) && mouseY < offButton.getHeight() && mouseY > 0))
		{
			batch.setColor(1f, 0.80f, 0.54f, 1f);
			batch.draw(offButton, xB, yB, 20, 20);
			batch.setColor(1f, 1f, 1f, 1f);
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) 
			{
				Gdx.app.exit();
			}
		}
		else
		{
			batch.setColor(origin.r, origin.g, origin.b, 1f);
			batch.draw(offButton, xB, yB, 20, 20);
		}
		
		Font.terminal1.draw(batch, (date.getHours() < 10 ? "0" : "") + date.getHours() + (date.getMinutes() < 10 ? ":0" : ":") + date.getMinutes(), Gdx.graphics.getWidth() - 140 - nameWidth, Gdx.graphics.getHeight() - 6);

		batch.setColor(1f, 1f, 1f, 1f);
		batch.draw(this.volume, Gdx.graphics.getWidth() - 170 - nameWidth, Gdx.graphics.getHeight() - 22, 20, 20);
		
		Font.terminal1.draw(batch, this.getName(), 4, Gdx.graphics.getHeight() - 5);
		
		batch.draw(this.userImage, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 24, 23, 23);
		
		try
		{
			this.drawIcons(batch, mouseX, mouseY);
		}
		catch(Exception e) {}
		
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
					else if(!this.applications.get(this.currentApplication).isMouseInside(mouseX, mouseY) && this.applications.get(this.currentApplication).canInteractWithOther(mouseX, mouseY) && !this.applications.get(i).isMouseInside(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
					{
						for(fr.growinghack.files.File f : this.files)
						{
							boolean x = mouseX >= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).i && mouseX <= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).i + 106;
							boolean y = mouseY >= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).j && mouseY <= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).j + 64;
							
							if(x && y)
							{
								f.open();
							}
						}
					}
				}
			}
			
			this.applications.get(this.currentApplication).renderApp(batch, mouseX, mouseY, this);
			this.applications.get(this.currentApplication).getMouseAction(mouseX, mouseY, this);
		}
		else
		{
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			{
				for(fr.growinghack.files.File f : this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files)
				{
					boolean x = mouseX >= f.i && mouseX <= f.i + 106;
					boolean y = mouseY >= f.j && mouseY <= f.j + 64;
					
					if(x && y)
					{
						f.open();
					}
				}
			}
		}
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
	
	public void drawIcons(Batch batch, int mouseX, int mouseY)
	{
		for(int i = 0 ; i < this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.size() ; i++)
		{
			boolean x = mouseX >= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).i && mouseX <= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).i + 106;
			boolean y = mouseY >= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).j && mouseY <= this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).j + 64;
			
			if(x && y)
			{
				Color origin = batch.getColor();
				batch.setColor(0.8f, 0.8f, 0.8f, 0.9f);
				this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).draw(batch, mouseX, mouseY);
				batch.setColor(origin);
			}
			else
			{
				this.goToFolder(GrowingHack.currentUser.username + ":Desktop").files.get(i).draw(batch, mouseX, mouseY);
			}
		}
	}

	public String getName() 
	{
		return "IlakOS";
	}
}
