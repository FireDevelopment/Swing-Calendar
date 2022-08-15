package utils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ScaleUtils {
	
	//get the window resolution
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
	//create window resolution varaibles
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
		
	//get the size of the calendar window
	public static int windowWidth = screenSize.width/32*25;
	public static int windowHeight = screenSize.height/6*5;
	
	public static ImageIcon scaleImage(BufferedImage image, double scalex, double scaley) { //scale images to scalex by scaley
		if (scalex == 0) { //image has to be at least 1 pixel wide
			scalex++;
		}
		if (scaley == 0) {
			scaley++;
		}
		ImageIcon img1 = new ImageIcon(image); //convert
		Image img2 = img1.getImage(); //convert again
		Image img3 = img2.getScaledInstance((int) scalex, (int) scaley,  java.awt.Image.SCALE_SMOOTH); //scale
		ImageIcon finalimg = new ImageIcon(img3); //convert
		return finalimg;
	}
	
	public static int placeX(double position, double componentWidth) { //give x position based on chosen position
		double newPosition = (windowWidth-componentWidth)*position;
		return (int) newPosition;
	}
	
	public static int placeY(double position, double componentHeight) { //give y
		double newPosition = (windowHeight-componentHeight)*position;
		return (int) newPosition;
	}
}
