package fr.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Messagerie extends Application {

	public Texture background = new Texture(Gdx.files.internal("ui/inside.png"));
	
	public Messagerie() {
		this.setDimension((int) (Gdx.graphics.getWidth() / 1.5f), (int) (Gdx.graphics.getHeight() / 1.5f));
	}
	
	public void render(Batch batch, int mouseX, int mouseY) {
		batch.draw(this.background, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
	}

	public String getAppName() {
		return "Messagerie";
	}

	@Override
	public int getAppID() {
		return 0;
	}

}
