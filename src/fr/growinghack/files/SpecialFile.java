package fr.growinghack.files;

import fr.growinghack.GrowingHack;
import fr.growinghack.application.Application;

public class SpecialFile extends File 
{
	public Class<?> toOpen;
	
	public String getExtention() 
	{
		return null;
	}

	public void open() 
	{
		if(this.toOpen != null)
		{
			try 
			{
				GrowingHack.currentOS.applications.add((Application) this.toOpen.newInstance());
			} 
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
