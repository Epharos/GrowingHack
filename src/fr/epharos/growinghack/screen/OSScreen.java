package fr.epharos.growinghack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.os.growos.GrowOS;
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.ui.TextField;

public class OSScreen implements Screen 
{
	private GrowOS growOS = new GrowOS();
	
	@SuppressWarnings("unused")
	private GrowingHack growingHack;
	private SpriteBatch batch;
	private Texture wallpaper;
	private Sprite sprite;
	private OrthographicCamera camera;
	
	public OSScreen(GrowingHack gh)
	{
		this.growingHack = gh;
		
		this.batch = new SpriteBatch();
		this.wallpaper = new Texture(Gdx.files.internal("wallpaper.png"));
		this.sprite = new Sprite(this.wallpaper);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.translate(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2);
	}
	
	public void show() {}

	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    int mouseX = Gdx.input.getX(), mouseY = Gdx.input.getY();
		
	    this.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    this.camera.update();
	    this.batch.setProjectionMatrix(this.camera.combined);
	    this.batch.begin();
	    this.sprite.draw(this.batch);
	    this.growOS.render(batch, mouseX, mouseY);
	    
	    this.batch.end();
	}

	public void resize(int width, int height) {}

	public void pause() {}

	public void resume() {}

	public void hide() {}
	
	public void dispose() 
	{
		this.wallpaper.dispose();
		Button.border.dispose();
		Button.inside.dispose();
		TextField.border.dispose();
		TextField.inside.dispose();
	}
}
