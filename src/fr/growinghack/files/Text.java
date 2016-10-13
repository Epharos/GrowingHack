package fr.growinghack.files;

import fr.growinghack.icon.Icon;

public class Text extends File
{	
	public Text()
	{
		this.icon = new Icon("apps/note.png");
	}
	
	public String getExtention() 
	{
		return "txt";
	}

	public void open() 
	{
		/** @TODO Quand l'�diteur de texte sera dispo **/
	}
}
