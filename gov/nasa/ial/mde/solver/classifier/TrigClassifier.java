package gov.nasa.ial.mde.solver.classifier;

import java.util.ArrayList;

import gov.nasa.ial.mde.solver.SolvedGraph;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class TrigClassifier extends MDEClassifier {
	
	private boolean hasSin = false,  
					hasCos = false, 
					hasTan = false,
					hasMultiples =false,
					hasX = false;
	
	public ArrayList<String> arr;
	
	public TrigClassifier() {
		super();
		
		
	}
	
	
	
	public SolvedGraph getFeatures(AnalyzedEquation analyzedEquation) {
		SolvedGraph features = null;
		
		detectTrig(analyzedEquation);
		
		if(hasSin){
			features =null;
		}
		
		return features;
	}



	private void detectTrig(AnalyzedEquation analyzedEquation) {
		String equat = analyzedEquation.getInputEquation();
		
		hasSin = equat.contains("sin");
		hasCos = equat.contains("cos");
		hasTan = equat.contains("tan");
		
		
	}
	
}
