package fr.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.GrowingHack;
import fr.growinghack.os.OS;
import fr.growinghack.ui.Button;
import fr.growinghack.ui.ButtonLabel;
import fr.growinghack.util.Font;
import fr.growinghack.util.Timer;

public abstract class Application 
{
	
	/** @TODO retravailler de fond en comble
	 * Aucune documentation
	 */
	
	public int x, y; /** Position affichée à l'écran **/
	public int x2, y2; /** Sauvegarde de la position avant maximisation **/
	public int width, height; /** Largeur et hauteur affichée à l'écran **/
	public int width2, height2; /** Sauvegarde de la largeur et de la hauteur affichée à l'écran **/
	public int minWidth, minHeight; /** Largeur et hauteur minimale **/
	
	public boolean visible = true;
	public boolean isFullscreen = false;
	public boolean clicked = false;
	
	private boolean move = false;
	private boolean resize = false;
	private int resizeType = -1;
	
	public int prevMouseX = -1;
	public int prevMouseY = -1;
	
	/** Textures des boutons de réduction, maximisation et fermeture de l'application **/
	protected ButtonLabel close = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "x", Font.buttonWindow, Font.buttonWindowOver);
	protected ButtonLabel fullscreen = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x +", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "+", Font.buttonWindow, Font.buttonWindowOver);
	protected ButtonLabel reduce = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x + -", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "-", Font.buttonWindow, Font.buttonWindowOver);
	protected Texture closeTextureButton = new Texture(Gdx.files.internal("ui/close.png"));
	protected Texture fullscreenTextureButton = new Texture(Gdx.files.internal("ui/fullscreen.png"));
	protected Texture reduceTextureButton = new Texture(Gdx.files.internal("ui/reduce.png"));
	
	public Application()
	{
		this.setDimension(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
		this.minHeight = Gdx.graphics.getHeight() / 2;
		this.minWidth = Gdx.graphics.getWidth() / 3;
		this.x = Gdx.graphics.getWidth() / 2 - this.width / 2;
		this.y = Gdx.graphics.getHeight() / 2 - this.height / 2;
		this.x2 = this.x;
		this.y2 = this.y;
	}
	
	public abstract void render(Batch batch, int mouseX, int mouseY);
	
	public void pr(Object o)
	{
		System.out.println(o);
	}
	
	public void renderApp(Batch batch, int mouseX, int mouseY, OS os)
	{		
		if(this.visible)
		{	
			this.drawBackground(batch, mouseX, mouseY);
			this.render(batch, mouseX, mouseY);
			this.drawForeground(batch, mouseX, mouseY, os);
			
			if(this.isFullscreen)
			{
				this.width = Gdx.graphics.getWidth();
				this.height = Gdx.graphics.getHeight() - 26;
				this.x = 0;
				this.y = 27;
			}
			else
			{
				this.setWidth(this.width2);
				this.setHeight(this.height2);
				this.x = this.x2;
				this.y = this.y2;
				this.x2 = this.x;
				this.y2 = this.y;
			}
			
			if(this.width < this.minWidth)
			{
				this.setWidth(this.minWidth);
			}
			
			if(this.height < this.minHeight)
			{
				this.setHeight(this.minHeight);
			}
		}
	}
	
	public void getMouseAction(int mouseX, int mouseY, OS os)
	{
		if(this.prevMouseX == -1)
		{
			this.prevMouseX = mouseX;
		}
		
		if(this.prevMouseY == -1)
		{
			this.prevMouseY = mouseY;
		}
		
		if(this.close.isButtonOver(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			os.applications.remove(this);
			os.currentApplication = 0;
		}
		
		if(this.fullscreen.isButtonOver(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !this.clicked)
		{
			this.clicked = true;
			this.isFullscreen = !this.isFullscreen;
			
			new Timer("clicked", 1)
			{
				public void execute()
				{
					GrowingHack.currentOS.applications.get(GrowingHack.currentOS.currentApplication).clicked = false;
					this.kill();
				}
			};
		}
		
		if(this.reduce.isButtonOver(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			this.visible = false;
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (!this.reduce.isButtonOver(mouseX, mouseY) && !this.close.isButtonOver(mouseX, mouseY) && !this.fullscreen.isButtonOver(mouseX, mouseY)) && !this.isFullscreen)
		{
			if((mouseX >= x + 6 && mouseX <= x + width - 6) && (mouseY >= y + 6 && mouseY <= y + 24) && !this.resize)
			{
				this.move = true;
			}
			
			if((mouseX >= x + 6 && mouseX <= x + width - 6) && (mouseY >= y && mouseY <= y + 6) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 0;
			}
			
			if((mouseX >= x && mouseX <= x + 6) && (mouseY >= y + 6 && mouseY <= y + height - 6) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 1;
			}
			
			if((mouseX >= x + 6 && mouseX <= x + width - 6) && (mouseY >= y + height - 6 && mouseY <= y + height) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 2;
			}
			
			if((mouseX >= x + width - 6 && mouseX <= x + width) && (mouseY >= y + 6 && mouseY <= y + height - 6) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 3;
			}
			
			if((mouseX >= x && mouseX <= x + 6) && (mouseY >= y && mouseY <= y + 6) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 4;
			}
			
			if((mouseX >= x && mouseX <= x + 6) && (mouseY >= y + height - 6 && mouseY <= y + height) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 5;
			}
			
			if((mouseX >= x + width - 6 && mouseX <= x + width) && (mouseY >= y && mouseY <= y + 6) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 6;
			}
			
			if((mouseX >= x + width - 6 && mouseX <= x + width) && (mouseY >= y + height - 6 && mouseY <= y + height) && !this.move && !this.resize)
			{
				this.resize = true;
				this.resizeType = 7;
			}
		}
		else
		{
			this.move = false;
			this.resize = false;
			this.resizeType = -1;
		}
		
		if(this.move)
		{
			this.x = this.x - (this.prevMouseX - mouseX);
			this.y = this.y - (this.prevMouseY - mouseY);
			this.x2 = this.x;
			this.y2 = this.y;
		}
		
		if(this.resize && this.resizable())
		{
			switch(this.resizeType)
			{
			case 0:
				this.setHeight(this.height + (this.prevMouseY - mouseY));
				this.y = this.y - (this.prevMouseY - mouseY);
				this.y2 = this.y;
				
				break;
			
			case 1:
				this.setWidth(this.width + (this.prevMouseX - mouseX));
				this.x = this.x - (this.prevMouseX - mouseX);
				this.x2 = this.x;
				
				break;
				
			case 2:
				this.setHeight(this.height + (mouseY - this.prevMouseY));
				this.height2 = this.height;
				
				break;
				
			case 3:
				this.setWidth(this.width + (mouseX - this.prevMouseX));
				this.width2 = this.width;
				
				break;
				
			case 4:
				this.setHeight(this.height + (this.prevMouseY - mouseY));
				this.y = this.y - (this.prevMouseY - mouseY);
				this.y2 = this.y;
				this.setWidth(this.width + (this.prevMouseX - mouseX));
				this.width2 = this.width;
				this.x = this.x - (this.prevMouseX - mouseX);
				this.x2 = this.x;
				
				break;
				
			case 5:
				this.setHeight(this.height + (mouseY - this.prevMouseY));
				this.setWidth(this.width + (this.prevMouseX - mouseX));
				this.width2 = this.width;
				this.x = this.x - (this.prevMouseX - mouseX);
				this.x2 = this.x;
				
				break;
				
			case 6:
				this.setHeight(this.height + (this.prevMouseY - mouseY));
				this.y = this.y - (this.prevMouseY - mouseY);
				this.y2 = this.y;
				this.setWidth(this.width + (mouseX - this.prevMouseX));
				this.width2 = this.width;
				
				break;
				
			case 7:
				this.setHeight(this.height + (mouseY - this.prevMouseY));
				this.setWidth(this.width + (mouseX - this.prevMouseX));
				this.width2 = this.width;
				
				break;
			}
		}
		
		this.prevMouseX = mouseX;
		this.prevMouseY = mouseY;
	}
	
	public void drawBackground(Batch batch, int mouseX, int mouseY)
	{		
		batch.draw(Button.inside, this.x, Gdx.graphics.getHeight() - this.y - 22, this.width, 24);
		
		batch.draw(Button.border, this.x, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width, this.height - 24);
	}
	
	public void drawForeground(Batch batch, int mouseX, int mouseY, OS os)
	{
		Font.appName.draw(batch, this.getAppName(), this.x + 4, Gdx.graphics.getHeight() - this.y - 4);
		
		this.close.x = this.x + this.width - 5 - (int) Font.getWidth("x", Font.buttonWindow);
		this.close.y = Gdx.graphics.getHeight() - this.y - 1;
		batch.draw(closeTextureButton, close.x - 4, close.y - 16, 13, 13);
		
		if(this.resizable())
		{
			this.fullscreen.x = this.x + this.width - 15 - (int) Font.getWidth("x +", Font.buttonWindow);
			this.fullscreen.y = Gdx.graphics.getHeight() - this.y - 3;
			batch.draw(fullscreenTextureButton, fullscreen.x - 2, fullscreen.y - 14, 13, 13);
		
			this.reduce.x = this.x + this.width - 25 - (int) Font.getWidth("x + -", Font.buttonWindow);
			this.reduce.y = Gdx.graphics.getHeight() - this.y - 3;
			batch.draw(reduceTextureButton, reduce.x - 5, reduce.y - 14, 13, 13);
		}
		else
		{
			this.reduce.x = this.x + this.width - 15 - (int) Font.getWidth("x -", Font.buttonWindow);
			this.reduce.y = Gdx.graphics.getHeight() - this.y - 3;
			batch.draw(reduceTextureButton, reduce.x - 5, reduce.y - 14, 13, 13);
		}
	}
	
	public abstract String getAppName();
	
	public boolean isMouseInside(int mouseX, int mouseY)
	{
		return (mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height);
	}
	
	public boolean canInteractWithOther(int mouseX, int mouseY)
	{
		return !this.isMouseInside(mouseX, mouseY) && !this.move && !this.resize;
	}
	
	public boolean resizable()
	{
		return true;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
		this.width2 = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
		this.height2 = height;
	}
	
	public void setDimension(int width, int height)
	{
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public abstract int getAppID();
}
