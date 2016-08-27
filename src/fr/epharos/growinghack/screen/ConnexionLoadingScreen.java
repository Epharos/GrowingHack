package fr.epharos.growinghack.screen;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.packets.PacketClientInfos;
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.ui.TextField;
import fr.epharos.growinghack.util.BooleanConnexion;
import fr.epharos.growinghack.util.Font;

public class ConnexionLoadingScreen implements Screen
{
	private GrowingHack growingHack;
	private SpriteBatch batch;
	private Texture wallpaper;
	private Texture fontavatar;
	private Sprite sprite;
	private OrthographicCamera camera;
	
	private Texture userAvatar = null, noAvatar;
	
    File file = new File("cache/" + GrowingHack.currentUser.username + ".jpg");
	
	public ConnexionLoadingScreen(GrowingHack gh)
	{
		this.growingHack = gh;
	
		this.batch = new SpriteBatch();
		this.wallpaper = new Texture(Gdx.files.internal("wallpaper.png"));
		this.fontavatar = new Texture(Gdx.files.internal("user/fontavatar.png"));
		this.sprite = new Sprite(wallpaper);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.translate(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2);
		this.noAvatar = new Texture(Gdx.files.internal("user/noavatar.png"));
		
		PacketClientInfos packet = new PacketClientInfos();
		packet.username = GrowingHack.currentUser.username;
		this.growingHack.client.client.sendTCP(packet);
	}
	
	public void show() {}

	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    this.sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    this.camera.update();
	    this.batch.setProjectionMatrix(this.camera.combined);
	    this.batch.begin();
	    this.sprite.draw(this.batch);
	    
	    if(this.file.exists() && this.userAvatar == null)
	    {
	    	try
	    	{
	    		this.userAvatar = new Texture(Gdx.files.absolute(this.file.getAbsolutePath()));
	    	}
	    	catch(Exception e)
	    	{
	    		
	    	}
	    }
	    
	    this.batch.draw(this.fontavatar, Gdx.graphics.getWidth() / 6 - 8, Gdx.graphics.getHeight() / 2 - 74.5f);
	    this.batch.draw((this.userAvatar != null ? this.userAvatar : this.noAvatar), Gdx.graphics.getWidth() / 6, Gdx.graphics.getHeight() / 2 - 66.5f, 128, 128);
	    
	    Font.getFont(Font.usual, 24).draw(batch, "Bienvenue ", Gdx.graphics.getWidth() / 6 + 154, Gdx.graphics.getHeight() / 2 + 20f);
	    Font.getFont(Font.usualOrange, 24).draw(batch, GrowingHack.currentUser.username, Gdx.graphics.getWidth() / 6 + 154 + Font.getWidth("Bienvenue ", Font.getFont(Font.usual, 24)), Gdx.graphics.getHeight() / 2 + 20f);
	    Font.getFont(Font.usual, 24).draw(batch, "Nous chargeons vos paramètres ...", Gdx.graphics.getWidth() / 6 + 154, Gdx.graphics.getHeight() / 2);
		
		this.batch.end();
		
		if(BooleanConnexion.playerApp && BooleanConnexion.playerInfos)
		{
			this.growingHack.setScreen(new OSScreen(this.growingHack));
		}
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
