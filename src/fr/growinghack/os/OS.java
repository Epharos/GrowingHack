package fr.growinghack.os;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import fr.growinghack.application.Application;
import fr.growinghack.files.File;
import fr.growinghack.icon.Icon;

public abstract class OS 
{		
	public List<Icon> icons = new ArrayList<Icon>(); /** Liste des icones à afficher sur le bureau **/
	public List<Application> applications = new ArrayList<Application>(); /** Liste des applications ouvertes **/
	
	public List<File> files = new ArrayList<File>();
	
	public int currentApplication = 0; /** L'application actuelle au premier plan **/
	
	public abstract void render(Batch batch, int mouseX, int mouseY); /** Affichage de l'OS **/
	
	public abstract String getName(); /** Retourne le nom de l'OS **/
}
