package fr.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.growinghack.ui.Button;
import fr.growinghack.ui.TextField;

public class WebBrowser extends Application
{
	public TextField url;
	public Texture background = new Texture(Gdx.files.internal("ui/white.png"));
	public fr.growinghack.ui.WebBrowser webBrowser;
	
	private Stage stage;
	
	public WebBrowser()
	{
		this.setDimension((int) (Gdx.graphics.getWidth() / 1.5f), (int) (Gdx.graphics.getHeight() / 1.5f));
		this.url = new TextField(0, 0, this.width - 12, 32, "growinghack.fr");
		this.webBrowser = new fr.growinghack.ui.WebBrowser(0, 0, this.width, this.height - 64, this);
		this.x = 200;
		this.y = 200;
		this.x2 = this.x;
		this.y2 = this.y;
		this.webBrowser.navigate(this.url.text);
		
		this.stage = new Stage();
		this.stage.addListener(new InputListener()
		{
			public boolean keyTyped (InputEvent event, char character) 
			{
				if(url.selected)
				{
					url.keyTyped(character);
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
		this.icon = new Texture(Gdx.files.internal("apps/internet.png"));
	}
	
	public void render(Batch batch, int mouseX, int mouseY) 
	{
		batch.draw(this.background, this.x + 3, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width - 6, this.height - 66);
		
		this.url.width = this.width - 12;
		this.url.x = this.x + 6;
		this.url.y = Gdx.graphics.getHeight() - this.y - 58;
		
		this.webBrowser.x = this.x;
		this.webBrowser.y = Gdx.graphics.getHeight() - this.y - 64;
		this.webBrowser.width = this.width;
		this.webBrowser.height = this.height - 64;
		
		this.url.draw(batch, mouseX, mouseY);
		this.webBrowser.draw(batch, mouseX, mouseY);
		
		batch.draw(Button.inside, this.x, Gdx.graphics.getHeight() - this.y - this.height + this.webBrowser.height, this.width, 2);
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) 
		{	
			this.webBrowser.navigate(this.url.text);
		}
	}

	public String getAppName() 
	{
		return "Navigateur Internet";
	}

	public String getAppID() 
	{
		return "webbrowser";
	}
}
