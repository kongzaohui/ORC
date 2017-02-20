package tess4j.example.test_object_tesseract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractExample {
	
	static String folderPath="D:\\AntonKONG\\MYPROJECT\\Tesseract_Tess4j\\src\\tess4j\\example\\test_object_tesseract\\";
	
	static String[] fileName={
							  "English_Number_Garamond",
							  "English_Number_Estrangelo Edessa",
							  "English_Number_Times New Roman",
							  "eng2_page1",
		
							  "English",
							  "Traditional Chinese",
							  "Traditional Chinese and English_1",
							  "Simplified Chinese",
							  "Simplified Chinese and English",
							  "Simplified Chinese and English_English_Library"
							  };
	
	static String[] suffix={".tif",".txt",".pdf"};
	
/*	static String[] outFileName={
								"English_Number_Garamond",
								"English_Number_Estrangelo Edessa",
								"English_Number_Times New Roman",
		
								"English",
				    			"Traditional Chinese",
				    			"Traditional Chinese and English_1",
				    			"Simplified Chinese",
				    			"Simplified Chinese and English",
				    			"Simplified Chinese and English_English_Library"
				    			};
	
	static String[] outputBase={
								"English_Number_Garamond",
								"English_Number_Estrangelo Edessa",
								"English_Number_Times New Roman",
								
								"English",
				    			"Traditional Chinese",
				    			"Traditional Chinese and English_1",
				    			"Simplified Chinese",
				    			"Simplified Chinese and English",
				    			"Simplified Chinese and English_English_Library"
								};*/

	public static void main(String[] args) throws FileNotFoundException {
		ITesseract instance = new Tesseract();
		
		/**
		 * output pdf file
		 * https://sourceforge.net/p/tess4j/discussion/1202294/thread/582c2ccf/
		 * http://tess4j.sourceforge.net/docs/docs-3.0/
		 * http://tess4j.sourceforge.net/usage.html
		 */
		List<RenderedFormat> formats = new ArrayList<RenderedFormat>();
        formats.add(RenderedFormat.PDF);
        formats.add(RenderedFormat.TEXT);
		try {
			//instance.setLanguage("chi_sim");
			//instance.setLanguage("chi_tra");
			instance.setLanguage("eng2");
			
			instance.createDocuments(folderPath + fileName[0] + suffix[0], folderPath + fileName[0] + suffix[2], formats);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		
		/**
		 * output txt file
		 * http://stackoverflow.com/questions/1994255/how-to-write-console-output-to-a-txt-file*/
		
		/*File imageFile = new File(fileName[1]);
		try{
			//instance.setLanguage("chi_tra");
			//instance.setLanguage("chi_sim");
			
			String result = instance.doOCR(imageFile);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName[1]), "UTF-8"));
				try {
				    out.write(result);
				} finally {
				    out.close();
				}
				
			System.out.println("Well, the job is done!" + result);
	
		}catch(TesseractException e){
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}	
}