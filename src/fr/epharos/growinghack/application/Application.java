package fr.epharos.growinghack.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.epharos.growinghack.os.OS;
import fr.epharos.growinghack.ui.Button;
import fr.epharos.growinghack.ui.ButtonLabel;
import fr.epharos.growinghack.util.Font;

public abstract class Application 
{
	
	/** @TODO retravailler de fond en comble
	 * Aucune documentation
	 */
	
	public int x, y;
	public int x2, y2;
	public int width, height;
	public int width2, height2;
	public int minWidth, minHeight;
	
	protected ButtonLabel close = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "x", Font.buttonWindow, Font.buttonWindowOver);
	protected ButtonLabel fullscreen = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x +", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "+", Font.buttonWindow, Font.buttonWindowOver);
	protected ButtonLabel reduce = new ButtonLabel(this.x + this.width - 4 - (int) Font.getWidth("x + -", Font.buttonWindow), Gdx.graphics.getHeight() - this.y - 1, "-", Font.buttonWindow, Font.buttonWindowOver);
	
	public boolean visible = true;
	public boolean isFullscreen = false;
	
	private boolean move = false;
	private boolean resize = false;
	private int resizeType = -1;
	
	public int prevMouseX = -1;
	public int prevMouseY = -1;
	
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
	
	public void renderApp(Batch batch, int mouseX, int mouseY, OS os)
	{
		if(this.visible)
		{
			this.drawBackground(batch, mouseX, mouseY);
			this.render(batch, mouseX, mouseY);
			this.drawForeground(batch, mouseX, mouseY, os);
			
			if(this.isFullscreen)
			{
				this.width = Gdx.graphics.getWidth() - 40;
				this.height = Gdx.graphics.getHeight() - 40;
				this.x = 2;
				this.y = 38;
			}
			else
			{
				this.setWidth(this.width2);
				this.setHeight(this.height2);
				this.x = this.x2;
				this.y = this.y2;
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
		
		if(this.fullscreen.isButtonOver(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			this.isFullscreen = !this.isFullscreen;
		}
		
		if(this.reduce.isButtonOver(mouseX, mouseY) && Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			this.visible = false;
		}
		
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
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
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.4f);
		
		batch.draw(Button.inside, this.x - 4, Gdx.graphics.getHeight() - 4 - this.y - this.height, this.width + 8, this.height + 8);
		
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
		
		batch.draw(Button.inside, this.x, Gdx.graphics.getHeight() - this.y - this.height, this.width, this.height);
		
		batch.draw(Button.border, this.x + 2, Gdx.graphics.getHeight() - this.y - this.height + 2, this.width - 4, this.height - 24);
	}
	
	public void drawForeground(Batch batch, int mouseX, int mouseY, OS os)
	{
		Font.appName.draw(batch, this.getAppName(), this.x + 4, Gdx.graphics.getHeight() - this.y - 4);
		
		this.close.x = this.x + this.width - 4 - (int) Font.getWidth("x", Font.buttonWindow);
		this.close.y = Gdx.graphics.getHeight() - this.y - 1;
		this.close.draw(batch, mouseX, mouseY);
		
		this.fullscreen.x = this.x + this.width - 4 - (int) Font.getWidth("x +", Font.buttonWindow);
		this.fullscreen.y = Gdx.graphics.getHeight() - this.y - 3;
		this.fullscreen.draw(batch, mouseX, mouseY);
		
		this.reduce.x = this.x + this.width - 4 - (int) Font.getWidth("x + -", Font.buttonWindow);
		this.reduce.y = Gdx.graphics.getHeight() - this.y - 3;
		this.reduce.draw(batch, mouseX, mouseY);
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
