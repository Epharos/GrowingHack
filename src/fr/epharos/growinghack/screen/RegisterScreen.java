package fr.epharos.growinghack.screen;

import javax.swing.JFileChooser;

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
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.ui.TextField;
import fr.epharos.growinghack.util.Font;

public class RegisterScreen implements Screen
{
	public GrowingHack growingHack;
	private SpriteBatch batch;
	private Texture wallpaper;
	private Sprite sprite;
	private OrthographicCamera camera;
	private Stage stage;
	
	private TextField username, password, confirmPassword, mail, confirmMail, imageURL;
	private Button send, back, openImage;
	
	public static String errorMessage = "";
	
	public boolean enterDone = false;
	
	public RegisterScreen(GrowingHack gh)
	{
		this.growingHack = gh;
		
		this.batch = new SpriteBatch();
		this.wallpaper = new Texture(Gdx.files.internal("wallpaper.png"));
		this.sprite = new Sprite(this.wallpaper);
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.translate(this.camera.viewportWidth / 2, this.camera.viewportHeight / 2);
		
		this.username = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3, 280, 32, "Pseudonyme");
		this.password = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3 - 42, 280, 32, "Mot de passe");
		this.confirmPassword = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3 - 84, 280, 32, "Mot de passe");
		this.mail = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3 - 126, 280, 32, "Adresse mail");
		this.confirmMail = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3 - 168, 280, 32, "Confirmer mail");
		this.imageURL = new TextField(Gdx.graphics.getWidth() / 2 - 140, 2 * Gdx.graphics.getHeight() / 3 - 210, 280, 32, "Avatar");
		
		this.password.password = true;
		this.confirmPassword.password = true;
		this.imageURL.canWrite = false;
		
		this.send = new Button(Gdx.graphics.getWidth() / 2 - 105, 2 * Gdx.graphics.getHeight() / 3 - 277, 210, 32, "Envoyer");
		this.back = new Button(32, 24, 160, 32, "Se connecter");
		this.openImage = new Button(Gdx.graphics.getWidth() / 2 + 150, 2 * Gdx.graphics.getHeight() / 3 - 210, 70, 32, "Ouvrir");
		
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
				
				if(confirmPassword.selected)
				{
					confirmPassword.keyTyped(character);
				}
				
				if(mail.selected)
				{
					mail.keyTyped(character);
				}
				
				if(confirmMail.selected)
				{
					confirmMail.keyTyped(character);
				}
				
				return false;
			}
		});
		Gdx.input.setInputProcessor(this.stage);
		
		RegisterScreen.errorMessage = "";
	}
	
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
		
		Font.title.draw(this.batch, "Inscription", Gdx.graphics.getWidth() / 2 - Font.getWidth("Inscription", Font.title) / 2, 2 * Gdx.graphics.getHeight() / 3 + 97);
		
		this.send.draw(batch, mouseX, mouseY);
		this.username.draw(batch, mouseX, mouseY);
		this.password.draw(batch, mouseX, mouseY);
		this.back.draw(batch, mouseX, mouseY);
		this.confirmPassword.draw(batch, mouseX, mouseY);
		this.mail.draw(batch, mouseX, mouseY);
		this.confirmMail.draw(batch, mouseX, mouseY);
		this.imageURL.draw(batch, mouseX, mouseY);
		this.openImage.draw(batch, mouseX, mouseY);
		
		Font.note.draw(batch, errorMessage, Gdx.graphics.getWidth() / 2 - Font.getWidth(errorMessage, Font.note) / 2 , 2 * Gdx.graphics.getHeight() / 3 - 331);
		
		batch.end();
		
		if(Gdx.input.isKeyJustPressed(Keys.TAB))
		{
			if(this.mail.selected)
			{
				this.mail.selected = false;
				this.confirmMail.selected = true;
			}
			
			if(this.confirmPassword.selected)
			{
				this.confirmPassword.selected = false;
				this.mail.selected = true;
			}
			
			if(this.password.selected)
			{
				this.password.selected = false;
				this.confirmPassword.selected = true;
			}
			
			if(this.username.selected)
			{
				this.username.selected = false;
				this.password.selected = true;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.send.isButtonOver(mouseX, mouseY)) && !this.enterDone)
		{
			this.growingHack.client.createAccount(this.username.text, this.password.text, this.confirmPassword.text, this.mail.text, this.confirmMail.text, this.imageURL.text);
			this.enterDone = true;
		}
		else
		{
			this.enterDone = false;
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.openImage.isButtonOver(mouseX, mouseY) && !this.enterDone)
		{
			JFileChooser openDialog = new JFileChooser();
			int state = openDialog.showOpenDialog(null);
			
			if(state == JFileChooser.APPROVE_OPTION)
			{
				this.imageURL.text = openDialog.getSelectedFile().getAbsolutePath();
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.back.isButtonOver(mouseX, mouseY))
		{
			this.growingHack.setScreen(new ConnexionScreen(this.growingHack));
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
	
	public void show() {}
}
