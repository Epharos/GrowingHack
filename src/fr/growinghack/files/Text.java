package fr.growinghack.files;

import fr.growinghack.icon.Icon2;

public class Text extends File
{	
	public Text()
	{
		this.icon = new Icon2("apps/note.png");
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
