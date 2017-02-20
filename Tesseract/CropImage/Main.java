package tess4j.example;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;

public class Main {

	public static void main(String[] args) throws IOException  {
		
		CropImage cropImage = new CropImage();
		Rectangle rect = null;
		//读入源图片
		BufferedImage src = ImageIO.read(new File (OCR.folderPath + "Bills-of-lading-page.jpg") );

		for(int i = 0, length = cropImage.X.length; i < length; i++){
			rect = new Rectangle(cropImage.X[i],cropImage.Y[i],cropImage.Width[i],cropImage.Height[i]);
			//以长方形为背景截取图片,将新的图片写入磁盘
			ImageIO.write(cropImage.cropImageMethod(src, rect), "jpg", new File(OCR.folderPath +"Bills-of-lading-page-00" + i + ".jpg"));
			//将新的图片名称加入到列表中
			OCR.fileName.add("Bills-of-lading-page-00" + i + ".jpg");
			}
	
		ITesseract instance = new Tesseract();
		List<RenderedFormat> formats = new ArrayList<RenderedFormat>();
        formats.add(RenderedFormat.TEXT);
		
		//instance.setLanguage("chi_sim");
		//instance.setLanguage("chi_tra");
        //进行OCR识别
        try {	
        	for(int i = 0, length = cropImage.X.length; i < length; i++){
        		instance.createDocuments(OCR.folderPath + OCR.fileName.get(i) , OCR.folderPath + OCR.fileName.get(i) , formats);
        		System.out.println("Processing Bills-of-lading-page-00" + i + ".jpg");
        	}
        	System.err.println("The job is Done!");
		} catch (TesseractException e) {
			e.printStackTrace();
		}	
	}
}
