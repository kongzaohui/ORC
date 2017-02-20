// (c) 2013 ABBYY Production LLC
// SAMPLES code is property of ABBYY, exclusive rights are reserved. 
//
// DEVELOPER is allowed to incorporate SAMPLES into his own APPLICATION and modify it under 
// the  terms of  License Agreement between  ABBYY and DEVELOPER.


// ABBYY FineReader Engine 11 Sample

// This sample shows basic steps of ABBYY FineReader Engine usage:
// Initializing, opening image file, recognition and export.

import com.abbyy.FREngine.*;

import java.nio.file.*;

public class Hello {
	
	static String[] suffix={".tif",".txt",".pdf",".rtf"};
	
	static String[] fileName={
		  "English_Number_Garamond",					//----0
		  "English_Number_Estrangelo Edessa",			//----1
		  "English_Number_Times New Roman",				//----2

		  "English",									//----3
		  "English_and_picture_and_handwrite",			//----4
		  "English_handwrite",							//----5
		  "SCB_Traditional_Chinese&English",			//----6
		  "Traditional Chinese",						//----7
		  "Traditional Chinese and English_1",			//----8
		  "Traditional Chinese and English_2",			//----9
		  "Simplified Chinese",							//----10
		  "Simplified Chinese and English",				//----11
		  };

	public static void main( String[] args ) {
		try {
			Hello application = new Hello();
			application.Run();
		} catch( Exception ex ) {
			displayMessage( ex.getMessage() );
		}
	}

	public void Run() throws Exception {
		// Load ABBYY FineReader Engine
		loadEngine();
		try{
			// Process with ABBYY FineReader Engine
			processWithEngine();
		} finally {
			// Unload ABBYY FineReader Engine
			unloadEngine();
		}
	}

	private void loadEngine() throws Exception {
		displayMessage( "Initializing Engine..." );
		engine = Engine.GetEngineObject( SamplesConfig.GetDllFolder(), SamplesConfig.GetDeveloperSN() );
	}

	private void processWithEngine() {
		try {
			// Setup FREngine
			setupFREngine();

			// Process sample image
			processImage();
		} catch( Exception ex ) {
			displayMessage( ex.getMessage() );
		}
	}

	private void setupFREngine() {
		displayMessage( "Loading predefined profile..." );
		engine.LoadPredefinedProfile( "DocumentConversion_Accuracy" );
	}

	private void processImage() {
		String imagePath = SamplesConfig.GetSamplesFolder() + fileName[8] + suffix[0];

		try {
			// Don't recognize PDF file with a textual content, just copy it
			if( engine.IsPdfWithTextualContent( imagePath, null ) ) {
				displayMessage( "Copy results..." );
				String resultPath = SamplesConfig.GetSamplesFolder();
				
				Files.copy( Paths.get( imagePath ), Paths.get( resultPath ), StandardCopyOption.REPLACE_EXISTING );
				return;
			}

			// Create document
			IFRDocument document = engine.CreateFRDocument();

			try {
				// Add image file to document
				displayMessage( "Loading image..." );
				document.AddImageFile( imagePath, null, null );

				// Process document
				displayMessage( "Process..." );
				document.Process( null );

				// Save results
				displayMessage( "Saving results..." );

				// Save results to rtf with default parameters
				
				/*String rtfExportPath = SamplesConfig.GetSamplesFolder() + fileName[9] +  suffix[3];
				document.Export( rtfExportPath, FileExportFormatEnum.FEF_RTF, null );*/
				
				
				// Save results to txt with default parameters
				
				/*String txtExportPath = SamplesConfig.GetSamplesFolder() + fileName[9] +  suffix[1];
				document.Export( txtExportPath, FileExportFormatEnum.FEF_TextUnicodeDefaults, null );*/
				

				// Save results to pdf using 'balanced' scenario, default library is English
				
				/*IPDFExportParams pdfParams = engine.CreatePDFExportParams();
				pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );
				
				String pdfExportPath = SamplesConfig.GetSamplesFolder() + fileName[9] + suffix[2];
				document.Export( pdfExportPath, FileExportFormatEnum.FEF_PDF, pdfParams );*/
				
				
				//Deal with Chinese files
//				1)      Create a DocumentProcessingParams object using the CreateDocumentProcessingParams method of the Engine object.
				IDocumentProcessingParams documentProcessingParams=engine.CreateDocumentProcessingParams();
		
//				2)      Specify the recognition language. Use the SetPredefinedTextLanguage method of the RecognizerParams sub-object of the PageProcessingParams sub-object.
				documentProcessingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("ChineseTaiwan,English");
				displayMessage( "Specify the recognition language with documentProcessingParams..." );
				
//				3)      Select the font set suitable for CJK languages. Use the ISynthesisParamsForDocument::FontSet property of the SynthesisParamsForDocument sub-object.
				//c# : dpp.SynthesisParamsForDocument.FontSet.SystemFontSet.FontNamesFilter = (int)FREngine.FontNamesFiltersEnum.FNF_Japanese;
				documentProcessingParams.getSynthesisParamsForDocument().getFontSet().getSystemFontSet().setFontNamesFilter(0);;				
				displayMessage( "Select the font set suitable for CJK languages with synthesisParamsForDocument..." );
				
//				4)      Pass the configured DocumentProcessingParams object to the Process method of the FRDocument object.
//				If you use methods of the Engine object, you should call one of the synthesis methods of the Engine object 
//				with the configured SynthesisParamsForDocument object as a parameter before export.				
				document.Process( documentProcessingParams );
				documentProcessingParams.getSynthesisParamsForDocument();
				
//				5)      Perform export of the recognized text with the help of the Export method of the FRDocument object. 
//				If you export to PDF of PDF/A format, specify the required export mode.
				
				String rtfExportPath = SamplesConfig.GetSamplesFolder() + fileName[8] +  suffix[3];
				document.Export( rtfExportPath, FileExportFormatEnum.FEF_RTF, null );
				
				String txtExportPath = SamplesConfig.GetSamplesFolder() + fileName[8] +  suffix[1];
				document.Export( txtExportPath, FileExportFormatEnum.FEF_TextUnicodeDefaults, null );
				
				IPDFExportParams pdfParams = engine.CreatePDFExportParams();
				pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );
				
				String pdfExportPath2 = SamplesConfig.GetSamplesFolder() + fileName[8] + suffix[2];				
				document.Export( pdfExportPath2, FileExportFormatEnum.FEF_PDF, pdfParams );
			
			} finally {
				// Close document
				document.Close();
			}
		} catch( Exception ex ) {
			displayMessage( ex.getMessage() );
		}
	}

	private void unloadEngine() throws Exception {
		displayMessage( "Deinitializing Engine...The job is done!" );
		engine = null;
		Engine.DeinitializeEngine();
	}

	private static void displayMessage( String message ) {
		System.out.println( message );
	}

	private IEngine engine = null;
}
