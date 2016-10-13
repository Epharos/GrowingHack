package fr.growinghack.files;

import java.util.*;

import fr.growinghack.icon.Icon2;

public class Folder extends File
{
	public List<File> files = new ArrayList<File>();
	
	public Folder()
	{
		this.icon = new Icon2("apps/folder.png");
	}

	public String getExtention() 
	{
		return null;
	}

	public void open() 
	{
		
	}
}
