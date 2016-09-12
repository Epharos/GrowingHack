package fr.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.growinghack.ui.TextField;

public class Messagerie extends Application {

	public Texture background = new Texture(Gdx.files.internal("ui/white.png"));
	public Texture logo = new Texture(Gdx.files.internal("apps/messagerie.png"));
	public Texture blue = new Texture(Gdx.files.internal("ui/backgroundmessagerie.png"));
	
	private TextField search;
	
	private Stage stage;
	
	public Messagerie() {
		this.setDimension((int) (Gdx.graphics.getWidth() / 1.5f), (int) (Gdx.graphics.getHeight() / 1.5f));
		this.search = new TextField(0, 0, this.width - 12, 32, "");
		this.stage = new Stage();
		this.stage.addListener(new InputListener()
		{
			public boolean keyTyped (InputEvent event, char character) 
			{
				if(search.selected)
				{
					search.keyTyped(character);
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
	}
	
	public void render(Batch batch, int mouseX, int mouseY) {
		this.search.width = 150;
		this.search.x = this.x + 32;
		this.search.y = Gdx.graphics.getHeight() - this.y - 58;
		
		batch.draw(this.background, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
		batch.draw(this.blue, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width / 4, this.height - 24);
		//batch.draw(this.logo, this.x + 85, Gdx.graphics.getHeight() - this.y - this.height + 460, 18, 18);
		this.search.draw(batch, mouseX, mouseY);
	}

	public String getAppName() {
		return "Messagerie";
	}

	@Override
	public int getAppID() {
		return 0;
	}
}
