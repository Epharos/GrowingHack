package fr.epharos.growinghack.files;

public abstract class File 
{
	public final boolean editable = true;
	public final String extention;
	
	public File(String extention)
	{
		this.extention = extention;
	}
}
