package fr.epharos.growinghack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import fr.epharos.growinghack.GrowingHack;
import fr.epharos.growinghack.packets.PacketConnexionScreenInfos;
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.util.Font;
import fr.epharos.growinghack.ui.TextField;

public class ConnexionScreen implements Screen
{
	private GrowingHack growingHack;
	private SpriteBatch batch;
	private Texture wallpaper;
	private Sprite sprite;
	private OrthographicCamera camera;
	
	private Button connect, register;
	private TextField username, password;
	
	private Stage stage;
	
	public static String errorMessage = "";
	public static int connected, registered;
	
	public static boolean userConnected = false;
	
	public ConnexionScreen(GrowingHack gh)
	{
		this.growingHack = gh;
	
		this.batch = new SpriteBatch();
		this.wallpaper = new Texture(Gdx.files.internal("wallpaper.png"));
		this.sprite = new Sprite(this.wallpaper);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.translate(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2);
		
		this.connect = new Button(Gdx.graphics.getWidth() / 2 - 90,  2 * Gdx.graphics.getHeight() / 3 - 166, 180, 32, "Se connecter");
		this.register = new Button(Gdx.graphics.getWidth() / 2 - 90, 2 * Gdx.graphics.getHeight() / 3 - 208, 180, 32, "S'inscrire");
		this.username = new TextField(Gdx.graphics.getWidth() / 2 - 110, 2 * Gdx.graphics.getHeight() / 3 - 67, 220, 32, "Pseudonyme");
		this.password = new TextField(Gdx.graphics.getWidth() / 2 - 110, 2 * Gdx.graphics.getHeight() / 3 - 111, 220, 32, "Mot de passe");
		this.password.password = true;
		
		this.stage = new Stage();
		this.stage.addListener(new InputListener()
		{
			public boolean keyTyped (InputEvent event, char character) 
			{
				if(username.selected)
				{
					username.keyTyped(character);
				}
				
				if(password.selected)
				{
					password.keyTyped(character);
				}
				
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
		
		ConnexionScreen.errorMessage = "";
		
		PacketConnexionScreenInfos packet = new PacketConnexionScreenInfos();
		this.growingHack.client.client.sendTCP(packet);
		ConnexionScreen.userConnected = false;
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
		
		Font.title.draw(this.batch, "Connexion", Gdx.graphics.getWidth() / 2 - Font.getWidth("Connexion", Font.title) / 2, 2 * Gdx.graphics.getHeight() / 3 + 30);
		
		this.connect.draw(batch, mouseX, mouseY);
		this.username.draw(batch, mouseX, mouseY);
		this.password.draw(batch, mouseX, mouseY);
		this.register.draw(batch, mouseX, mouseY);
		
		Font.note.draw(batch, errorMessage, Gdx.graphics.getWidth() / 2 - Font.getWidth(errorMessage, Font.note) / 2 , 2 * Gdx.graphics.getHeight() / 3 - 211);
		Font.getFont(Font.usualOrange, 24).draw(batch, String.valueOf(registered), 8, 32);
		Font.getFont(Font.usual, 24).draw(batch, " inscrit" + (registered > 1 ? "s" : "") + " - ", 8 + Font.getWidth(String.valueOf(registered), Font.getFont(Font.usual, 24)), 32);
		Font.getFont(Font.usualOrange, 24).draw(batch, String.valueOf(connected), 8 + Font.getWidth(registered + " inscrit" + (registered > 1 ? "s" : "") + " - ", Font.getFont(Font.usual, 24)), 32);
		Font.getFont(Font.usual, 24).draw(batch, " connecté" + (connected > 1 ? "s" : ""), 8 + Font.getWidth(registered + " inscrit" + (registered > 1 ? "s" : "") + " - " + connected, Font.getFont(Font.usual, 24)), 32);
		
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.TAB))
		{
			if(this.username.selected)
			{
				this.username.selected = false;
				this.password.selected = true;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.connect.isButtonOver(mouseX, mouseY)))
		{
			this.growingHack.client.connectClient(this.username.text, this.password.text);
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.register.isButtonOver(mouseX, mouseY))
		{
			this.growingHack.setScreen(new RegisterScreen(this.growingHack));
		}
		
		if(ConnexionScreen.userConnected)
		{
			this.growingHack.setScreen(new ConnexionLoadingScreen(this.growingHack));
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
