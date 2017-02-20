package com.hello2;
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

public class Hello2 {
	
	static String[] suffix={".tif",".gif",".txt",".pdf",".rtf"};
	
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
		  "pic00041"									//----12				
		  };


	public static void main( String[] args ) {
		try {
			Hello2 application = new Hello2();
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
		// Possible profile names are:
		//   "DocumentConversion_Accuracy", "DocumentConversion_Speed",
		//   "DocumentArchiving_Accuracy", "DocumentArchiving_Speed",
		//   "BookArchiving_Accuracy", "BookArchiving_Speed",
		//   "TextExtraction_Accuracy", "TextExtraction_Speed",
		//   "FieldLevelRecognition",
		//   "BarcodeRecognition_Accuracy", "BarcodeRecognition_Speed",
		//   "HighCompressedImageOnlyPdf",
		//   "BusinessCardsProcessing",
		//   "EngineeringDrawingsProcessing",
		//   "Version9Compatibility",
		//   "Default"
	}

	private void processImage() {
		String imagePath = SamplesConfig.GetSamplesFolder() + fileName[12] + suffix[1];

		try {
			// Don't recognize PDF file with a textual content, just copy it
			if( engine.IsPdfWithTextualContent( imagePath, null ) ) {
				displayMessage( "Copy results..." );
				String resultPath = SamplesConfig.GetSamplesFolder() + fileName[12] + suffix[2];
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
				
				IDocumentProcessingParams documentProcessingParams = engine.CreateDocumentProcessingParams();
				documentProcessingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("English");

				//ISynthesisParamsForDocument synthesisParamsForDocument = engine.CreateSynthesisParamsForDocument();
				documentProcessingParams.getSynthesisParamsForDocument().getFontSet().getSystemFontSet().setFontNamesFilter(0);
				document.Process( documentProcessingParams );

				// Save results
				displayMessage( "Saving results..." );

				// Save results to rtf with default parameters
							
				String rtfExportPath = SamplesConfig.GetSamplesFolder() + fileName[12] + suffix[4];
				document.Export( rtfExportPath, FileExportFormatEnum.FEF_RTF, null );

				// Save results to pdf using 'balanced' scenario
				IPDFExportParams pdfParams = engine.CreatePDFExportParams();
				pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );

				String pdfExportPath = SamplesConfig.GetSamplesFolder() + fileName[12] + suffix[3];
				document.Export( pdfExportPath, FileExportFormatEnum.FEF_PDF, pdfParams );
			} finally {
				// Close document
				document.Close();
			}
		} catch( Exception ex ) {
			displayMessage( ex.getMessage() );
		}
	}

	private void unloadEngine() throws Exception {
		displayMessage( "Deinitializing Engine..." );
		engine = null;
		Engine.DeinitializeEngine();
	}

	private static void displayMessage( String message ) {
		System.out.println( message );
	}

	private IEngine engine = null;
}
