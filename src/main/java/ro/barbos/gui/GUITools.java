package ro.barbos.gui;

import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class GUITools {

	public static Image getImage(String resLink){
		   InputStream inImage = GUITools.class.getResourceAsStream(resLink);
		   BufferedImage image=null;
		   if(inImage!=null) {
				  try{
					   image = ImageIO.read(inImage);
				  }catch (IOException e) {
					e.printStackTrace();
				}
		   } 
		 if(image!=null) return  image;
		 else return null;
	   }
	
	public static void closeParentDialog(JComponent component) {
		Window w = SwingUtilities.getWindowAncestor(component);
		if (w != null)
			w.dispose();
	}
	
}
