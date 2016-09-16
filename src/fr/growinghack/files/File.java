package fr.growinghack.files;

import java.util.ArrayList;

public abstract class File 
{
	public ArrayList<String> content = new ArrayList<String>();
	
	public File()
	{
		
	}
	
	public abstract String getExtention();
	
	public abstract void open();
}
