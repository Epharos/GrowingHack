package fr.growinghack.websites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.util.Font;

public class GrowingHackSite extends Site {
	
	public Texture background = new Texture(Gdx.files.internal("ui/background.png"));
	
	public GrowingHackSite()
	{
		setUrl("growinghack.fr");
	}

	@Override
	public void render(Batch batch, int x, int y, int width, int height) {
		batch.draw(background, x - 4, y + 6, width, -height + 64);
		Font.terminalRed.draw(batch, "BIENVENUE SUR LE SITE DE GROWING HACK", x, y);
	}
}
