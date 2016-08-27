package fr.epharos.growinghack.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageEncoding 
{
	public static void bytesToImage(byte[] bytes, File output, String extension)
	{
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage bImageFromConvert;
		
		try 
		{
			bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, extension, output);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static byte[] imageToBytes(File file, String extention)
	{
		byte[] bytes = null;
		
		try 
		{
			BufferedImage originalImage = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, extention, baos);
			baos.flush();
			bytes = baos.toByteArray();
			baos.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return bytes;
	}
}
