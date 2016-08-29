package fr.growinghack.auth;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import fr.growinghack.GrowingHack;
import fr.growinghack.packets.PacketCreateAccount;
import fr.growinghack.screen.RegisterScreen;
import fr.growinghack.util.ImageEncoding;

public class AccountCreation 
{
	private static List<String> errors = new ArrayList<String>();
	private static boolean[] types = new boolean[] {false, false, false};
	
	public static void createAccount(String username, String password, String cpassword, String mail, String cmail, String avatar)
	{
		PacketCreateAccount packet = new PacketCreateAccount();
		boolean send = AccountCreation.checkMails(mail, cmail) && AccountCreation.checkPassword(password, cpassword) && AccountCreation.checkUsername(username);
		
		if(!AccountCreation.checkImage(avatar))
		{
			packet.useDefaultAvatar = true;
		}
		else
		{
			File image = new File(avatar);
			
			packet.avatar = ImageEncoding.imageToBytes(image, "jpg");
		}
		
		if(!send)
		{
			RegisterScreen.errorMessage = "Votre inscription n'est pas conforme : ";
			
			int errors = 0;
			
			if(!AccountCreation.types[2])
			{
				errors++;
				RegisterScreen.errorMessage = RegisterScreen.errorMessage.concat("mail, ");
			}
			
			if(!AccountCreation.types[1])
			{
				errors++;
				RegisterScreen.errorMessage = RegisterScreen.errorMessage.concat("mot de passe, ");
			}
			
			if(!AccountCreation.types[0])
			{
				errors++;
				RegisterScreen.errorMessage = RegisterScreen.errorMessage.concat("nom d'utilisateur");
			}
			
			RegisterScreen.errorMessage = RegisterScreen.errorMessage.concat(" (" + errors + " erreurs)");
		}
		else
		{
			packet.username = username;
			
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] bytes = password.getBytes("UTF-8");
				md.reset();
				md.update(bytes, 0, password.length());
				byte[] crypted = md.digest(bytes);
				packet.password = new String(new BigInteger(1, crypted).toString(16));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			packet.mail = mail;
			
			GrowingHack.instance.client.client.sendTCP(packet);
		}
	}
	
	private static boolean checkImage(String avatar)
	{
		File image = new File(avatar);
		
		if(!image.exists())
		{
			AccountCreation.errors.add("L'image n'existe pas.");
			return false;
		}
		else
		{
			Texture img = new Texture(Gdx.files.absolute(avatar));
			
			if(img.getWidth() > 200 || img.getHeight() > 200 || (!avatar.toLowerCase().endsWith(".jpg") && !avatar.toLowerCase().endsWith(".jpeg")))
			{
				if(img.getWidth() > 200 || img.getHeight() > 200)
				{
					AccountCreation.errors.add("L'image n'est pas dans les bonnes dimensions (200x200 maximum).");
				}
				
				if(!avatar.toLowerCase().endsWith(".jpg") && !avatar.toLowerCase().endsWith(".jpeg"))
				{
					AccountCreation.errors.add("L'image doit être sous format jp(e)g.");
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean checkMails(String mail, String cmail)
	{
		if(!mail.equals(cmail))
		{
			AccountCreation.errors.add("Vos mails ne correspondent pas.");
			return false;
		}
		
		if(!mail.contains("@") || mail.equals(""))
		{
			AccountCreation.errors.add("Votre adresse mail n'est pas conforme.");
			return false;
		}
		
		AccountCreation.types[2] = true;
		
		return true;
	}
	
	private static boolean checkPassword(String password, String cpassword)
	{
		if(!password.equals(cpassword))
		{
			AccountCreation.errors.add("Vos mots de passes ne correspondent pas.");
			return false;
		}
		
		if(password.equals("") || password.length() < 6 || password.length() > 48)
		{
			AccountCreation.errors.add("Votre mot de passe n'est pas conforme" + (password.length() > 48 ? ", il est trop long." : "") + (password.length() < 6 ? ", il est trop court." : "") + (password.equals("") ? "." : ""));
			return false;
		}
		
		AccountCreation.types[1] = true;
		
		return true;
	}
	
	private static boolean checkUsername(String username)
	{
		if(username.equals("") || username.length() < 3 || username.length() > 18)
		{
			AccountCreation.errors.add("Votre nom de compte n'est pas conforme" + (username.length() > 18 ? ", il est trop long." : "") + (username.length() < 3 ? ", il est trop court." : "") + (username.equals("") ? "." : ""));
			return false;
		}
		
		AccountCreation.types[0] = true;
		
		return true;
	}
}
