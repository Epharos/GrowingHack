package fr.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.growinghack.ui.TextField;

public class Note extends Application {

	public Texture background = new Texture(Gdx.files.internal("ui/white.png"));

	private Stage stage;
	public TextField text;

	public Note() {
		this.setDimension((int) (Gdx.graphics.getWidth() / 1.5f), (int) (Gdx.graphics.getHeight() / 1.5f));
		text = new TextField(0, 0, this.width - 12, 32, "");
		text.canEspace = true;
		stage = new Stage();
		stage.addListener(new InputListener() {
			public boolean keyTyped(InputEvent event, char character) {
				if (text.selected) {
					text.keyTyped(character);
				}
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
	}

	public void render(Batch batch, int mouseX, int mouseY) {
		this.text.width = (int) (Gdx.graphics.getWidth() / 1.5f);
		this.text.x = this.x;
		this.text.y = Gdx.graphics.getHeight() - this.y - 54;

		batch.draw(this.background, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
		this.text.draw(batch, mouseX, mouseY);
	}

	public String getAppName() {
		return "Note";
	}

	public int getAppID() {
		return 0;
	}
}
