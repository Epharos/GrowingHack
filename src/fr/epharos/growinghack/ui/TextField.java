package fr.epharos.growinghack.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.client.GrowingHackClient;
import fr.epharos.growinghack.util.Font;

public class TextField
{
	public int x, y, width, height;
	public String text, showedText;
	
	public boolean selected = false;
	
	public boolean password = false;
	
	public int cursor = 0;
	public int timer = 0;
	
	public boolean drawCursor = true;
	
	public static final Texture border = new Texture(Gdx.files.internal("ui/border.png"));
	public static final Texture inside = new Texture(Gdx.files.internal("ui/inside.png"));
	
	public boolean canWrite = true;
	
	public TextField(int x, int y, int w, int h, String t)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.text = t;
		this.cursor = text.length();
	}
	
	public void draw(Batch batch, int mouseX, int mouseY)
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextFieldOver(mouseX, mouseY) && !this.selected)
		{
			this.selected = true;
		}
		else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !this.isTextFieldOver(mouseX, mouseY))
		{
			this.selected = false;
		}
		
		this.timer++;
		
		if(this.timer == 35)
		{
			this.timer = 0;
			this.drawCursor = !this.drawCursor;
		}
		
		this.updateShowedText();
		
		batch.draw(border, this.x, this.y, this.width, this.height);
		batch.draw(inside, this.x + 2, this.y + 2, this.width - 4, this.height - 4);
		
		if(this.selected)
		{
			if(this.drawCursor)
			{
				for(int i = 0 ; i < Font.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", Font.getFont(Font.usual, 24)) + 5 ; i++)
				{
					batch.draw(border, x + 4 + Font.getWidth(this.showedText.substring(0, this.cursor), Font.getFont(Font.usual, 24)), this.y + 5 + i);
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.DEL))
			{
				if(this.text.length() > 0)
				{
					if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
					{
						this.text = "";
						this.cursor = 0;
					}
					else if(this.cursor > 0)
					{
						this.text = this.text.substring(0, this.cursor - 1).concat(this.text.substring(this.cursor, this.text.length()));
						this.cursor--;
					}
				}
			}
			
			if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
			{
				if(Gdx.input.isKeyJustPressed(Keys.V))
				{
					this.text = this.text.substring(0, this.cursor).concat(GrowingHackClient.clipboard).concat(this.text.substring(this.cursor, this.text.length()));
				}
				
				if(Gdx.input.isKeyJustPressed(Keys.C))
				{
					GrowingHackClient.clipboard = this.text;
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.LEFT))
			{
				if(this.cursor > 0)
				{
					this.cursor--;
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Keys.RIGHT))
			{
				if(this.cursor < this.showedText.length())
				{
					this.cursor++;
				}
			}
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.isTextFieldOver(Gdx.input.getX(), Gdx.input.getY()))
		{
			int mouseXOffSet = Gdx.input.getX() - this.x;
			
			for(int i = 0 ; i < this.showedText.length() ; i++)
			{
				if(Font.getWidth(this.showedText.substring(0, i), Font.getFont(Font.usual, 24)) >= mouseXOffSet)
				{
					this.cursor = i - 1;
					
					if(this.cursor < 0)
					{
						this.cursor = 0;
					}
					
					break;
				}
			}
		}
		
		Font.getFont(Font.usual, 24).draw(batch, this.showedText, this.x + 4, this.y + this.height / 2 + (Font.getFont(Font.usual, 24).getBounds(this.text).height / 2));
	}
	
	public void updateShowedText()
	{
		float fullSize = Font.getWidth(this.text, Font.getFont(Font.usual, 24));
		
		String temp = "";
		int n = 0;
		
		if(fullSize > this.width - 8)
		{
			test : for(int i = this.text.length() - 1 ; i > 0 ; i--)
			{
				if(Font.getWidth(temp, Font.getFont(Font.usual, 24)) + Font.getWidth(String.valueOf(this.text.charAt(i)), Font.getFont(Font.usual, 24)) < this.width - 8)
				{
					n = i + 1;
					temp = temp.concat(String.valueOf(this.text.charAt(i)));
				}
				else
				{
					break test;
				}
			}
		
			temp = "";
		
			for(int a = n ; a < this.text.length() ; a++)
			{
				temp = temp.concat(String.valueOf(this.text.charAt(a)));
			}
			
			this.showedText = temp;
		}
		else
		{
			this.showedText = this.text;
		}
		
		if(password)
		{
			int a = this.showedText.length();
			this.showedText = "";
			
			for(int i = 0 ; i < a ; i++)
			{
				this.showedText = this.showedText.concat("*");
			}
		}
	}
	
	public boolean isTextFieldOver(int mouseX, int mouseY)
	{
		mouseY = Gdx.graphics.getHeight() - mouseY;
		return (mouseX >= this.x && mouseX <= this.x + this.width) && (mouseY >= this.y && mouseY <= this.y + this.height);
	}

	public void keyTyped(char key) 
	{		
		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.+-&é(-è_çà)=^$ù*!:;,~#{[|`^@]}¤ù*¨£%µ§/?\"\'";
		
		for(int i = 0 ; i < allowedChars.length() ; i++)
		{
			if(key == allowedChars.charAt(i))
			{
				if(this.selected && this.canWrite)
				{
					if(this.text != null && !this.text.equals(""))
					{
						this.text = this.text.substring(0, this.cursor).concat(String.valueOf(key)).concat(this.text.substring(this.cursor, this.text.length()));
						this.cursor++;
					}
					else
					{
						this.text += key;
						this.cursor++;
					}
				}
			}
		}
	}
}
