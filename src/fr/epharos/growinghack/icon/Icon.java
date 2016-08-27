package fr.epharos.growinghack.icon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import fr.epharos.growinghack.application.Application;

public abstract class Icon
{
	public final Texture icon = new Texture(Gdx.files.internal("apps/" + this.getAppIcon() + ".png")); /** Chemin interne vers la texture de l'icone de l'application **/
	
	public abstract String getAppName(); /** Le nom de l'application **/
	public abstract String getAppIcon(); /** Le nom de l'icone de l'application **/
	public abstract Application openApp(); /** L'application que l'icone ouvre **/
	public abstract int getAppID(); /** L'ID de l'application à ouvrir **/
}
