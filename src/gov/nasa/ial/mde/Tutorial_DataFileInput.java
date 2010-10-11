package gov.nasa.ial.mde;
/* 
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 * 
 * Created on Sep 3, 2004
 *
 * @author Terry Hodgson
 */

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.io.TextDataFileParser;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedData;

public class Tutorial_DataFileInput {

    public static void main(String[] args) {
        // MDE Init:
        MdeSettings currentSettings = new MdeSettings("myAppsMdeProperties");
        Solver solver = new Solver();
        Describer describer = new Describer(solver, currentSettings);
        describer.setOutputFormat(Describer.TEXT_OUTPUT);
        
        
        File file = new File("InputText.txt");
                
        TextDataFileParser fileParser = new TextDataFileParser(file);
        AnalyzedData data = null;
        
        try {
			List list = fileParser.parse();
			
			System.out.println(list.size());
			
			data = (AnalyzedData) list.get(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } // end main

} // end class Tutorial_DataFileInput
