package gov.nasa.ial.mde;

/* DescriberTest
 * This class is used to automate regression test runs for Describer.java
 * A human is still required to check the output.
 * 
 * @author Terry Hodgson for Benetech
 * 
 * August 8, 2012
 * 
 * Derivative of Tutorial_CommandLineDescriber.java:
 * ========================================================================
 * Tutorial_CommandLineDescriber.java
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 * 
 * Created on Sep 3, 2004
 *
 * @author Terry Hodgson
 */

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;
import gov.nasa.ial.mde.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * @author terry
 * 
 */
public class DescriberTest {
	//TODO: allow specification of description-mode-based test coverage
	// ALL would include any new modes added by the client 
//	public static final enum TEST_COVERAGE {ALL,VISUAL,MATH,STANDARDS};
	public static enum INPUT_FILE_TYPE {equationsFile, listOfEquationsFiles, data}
	public static enum OUTPUT_FORMAT {TEXT_OUTPUT, HTML_OUTPUT, XML_OUTPUT};
	
	private String outputFormat;
	private String descriptionMode;
	private String xslFile;
	private String equationsFilename;
	private BufferedReader equationsFile;
	private String inputFileListName;
	private BufferedReader inputFileList;
	MdeSettings currentSettings;
	Solver solver;
	Describer describer;

	
	public DescriberTest(){
		currentSettings = new MdeSettings("myMDE.properties");
		solver = new Solver();
		this.descriptionMode = currentSettings.getDescriptionMode();
		this.xslFile = Describer.modeMap.get(descriptionMode);

		describer = new Describer(solver, currentSettings);
	}
	
	public DescriberTest(Describer d, Solver s, MdeSettings ms){
		this.describer = d;
		this.solver = s;
		this.currentSettings = ms;
		this.descriptionMode = d.getCurrentDescriptionMode();
	}
	
	public DescriberTest(String defaultMode){
		this();
		describer.setCurrentDescriptionMode(defaultMode);
		this.descriptionMode = defaultMode;
		this.xslFile = Describer.modeMap.get(defaultMode);
	}
	
	public DescriberTest(String newMode, String descriptionTemplate){
		this();
		describer.addDescriptionMode(newMode, descriptionTemplate);
		describer.setCurrentDescriptionMode(newMode);
		this.descriptionMode = newMode;
		this.xslFile = descriptionTemplate;
	}
	
	
	public BufferedReader initEquationsFile(String equationsFilename) {
		try {
			equationsFilename = equationsFilename;
			equationsFile = initInputFile(equationsFilename);
		} catch (Exception e) {
			System.out.println(e);
		}
		return equationsFile;
	}
	
	public BufferedReader initFileListFile(String fileListFilename) {
		try {
			inputFileListName = fileListFilename;
			inputFileList = initInputFile(fileListFilename);
		} catch (Exception e) {
			System.out.println(e);
		}
		return inputFileList;
	}
	
	public BufferedReader initInputFile(String inputFilename)
			throws FileNotFoundException {
		BufferedReader inputFile = null;
		// ResourceUtil ru = new ResourceUtil(MdeSettings.RESOURCES_PATH);
		ResourceUtil ru = new ResourceUtil("/test/input/");
		try {
			inputFile = ru.getResourceAsReader(inputFilename);
			if (inputFile == null)
				System.out.println("inputFile is null");
		} catch (Exception e) {
			System.out.println(e);
		}
		return inputFile;
	}
	
	public void runEquationsFile(String filename){
		this.equationsFilename = filename;
		String equation;

		// Process equations
		try {
			initEquationsFile(equationsFilename);
			equation = equationsFile.readLine();
			while (equation != null) {
				System.out
						.println("\n\n====================================================");

				System.out.println("equation: " + equation);

				// Give Solver equation and solve
				solver.add(equation);
				solver.solve();

				if (solver.anyDescribable()) {
					String description = describer.getDescriptions();
					System.out.println(description);
				} else {
					System.out
							.println("MDE could not generate a description for "
									+ equation + ".");
				}

				// Clear Solver so next equation will be processed singly
				// (we only want one description at a time)
				solver.removeAll();
				equation = equationsFile.readLine();
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("End of File");
		}
	}

	public void runFileList(String fileListFile) {
		String filename;
		String afile;
		BufferedReader listReader = null;
		// MDE Init:

		describer.setOutputFormat(outputFormat);

		// Process equation files
		try {
			listReader = initInputFile(fileListFile);

			// Prompt user for input until they enter CTRL-C.
			afile = listReader.readLine();
			while (afile != null) {
				System.out
						.println("\n\n====================================================");

				System.out.println("Processing File:  "+afile);
				runEquationsFile(afile);
				afile = listReader.readLine();
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("End of File");
		}
	}

	public String getDescriptionMode() {
		return descriptionMode;
	}

	public void setDescriptionMode(String descriptionMode) {
		this.descriptionMode = descriptionMode;
		describer.setCurrentDescriptionMode(descriptionMode);
	}

	public String getXslFile() {
		return xslFile;
	}

	public void setXslFile(String xslFile) {
		this.xslFile = xslFile;
	}

	public String getEquationsFilename() {
		return equationsFilename;
	}

	public void setEquationsFilename(String equationsFilename) {
		this.equationsFilename = equationsFilename;
	}

	public BufferedReader getEquationsFile() {
		return equationsFile;
	}

	//TODO: probably should have this return handle to the file
	public void setEquationsFile(String filename){
		initEquationsFile(filename);
	}
	public void setEquationsFile(BufferedReader equationsFile) {
		this.equationsFile = equationsFile;
	}

	public BufferedReader getInputFileList() {
		return inputFileList;
	}

	public BufferedReader setInputFileList(String file) {
		BufferedReader reader = null;
		try {
			reader = initInputFile(file);
		} catch (Exception e) {
			System.out.println(e);
		}
		this.inputFileListName = file;
		return reader;
	}

	public void setInputFileList(BufferedReader inputFileList) {
		this.inputFileList = inputFileList;
	}

	public String getInputFileListName() {
		return inputFileListName;
	}

	public void setInputFileListName(String inputFileListName) {
		this.inputFileListName = inputFileListName;
	}
	
	public void setInputFile(String filename, INPUT_FILE_TYPE type){
		switch(type) {
		case equationsFile:
			equationsFile = initEquationsFile(filename);
			break;
		case listOfEquationsFiles:
			inputFileList = initFileListFile(filename);
			break;
		case data:
			;
		}
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
		describer.setOutputFormat(outputFormat);
	}

	public String toString() {
		return "descriptionMode: " + descriptionMode + " xslFile: " + xslFile
				+ " equationsFilename: " + equationsFilename;
	}

	public static void main(String[] args) {
		String descriptionMode = new String(args[0]);
		String xslFile = new String(args[1]);
		String equationsFilename = new String(args[2]);

		//init DescriberTest with specified description mode
		DescriberTest dt = new DescriberTest(descriptionMode,xslFile);
		
		//set our desired output format (options are text, html and xml
		dt.setOutputFormat("text");
//		dt.setOutputFormat("xml");
//		dt.setOutputFormat("html");
		
		//Run tests: Input is an "equation file" - a file with a list of equations, one per line
		dt.runEquationsFile(equationsFilename);
		
		//Run tests: Input is a file with a list of equation files
//		dt.runFileList("equationInputFiles.txt");
		
		//TODO:  Run tests: Input is a data file
		
		//Run tests: equation file against a default mode (visual, math or standard)
//		dt.setDescriptionMode("visual");
//		dt.runEquationsFile(equationsFilename);

	
		
	} // end main

} // end class TestDescriber
