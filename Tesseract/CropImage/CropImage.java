package tess4j.example;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class CropImage {
	
	//获取长方形，单位像素，以图片左上角为圆点
	int[] X      =   {1900,1380,790,140,1420,1420};
	int[] Y      =   {400,1290,3320,400,620,620};
	int[] Width  =   {400,800,500,780,670,670};
	int[] Height =   {60,230,40,160,50,35};

	public BufferedImage cropImageMethod(BufferedImage src, Rectangle rect) {	
		BufferedImage dest = src.getSubimage((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
	    return dest; 
   }
		
}