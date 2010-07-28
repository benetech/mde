package gov.nasa.ial.mde.solver.classifier;

import java.util.ArrayList;

import gov.nasa.ial.mde.solver.SolvedGraph;
import gov.nasa.ial.mde.solver.SolvedTrigFunction;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Polynomial;

public class TrigClassifier extends MDEClassifier {
	
	private boolean hasSin = false,  
					hasCos = false, 
					hasTan = false,
					hasMultiples =false,
					hasX = false;
	
	public ArrayList<String> arr;
	private Polynomial lhs;
	
	
	public TrigClassifier() {
		super();
		
		
	}
	
	
	
	public TrigClassifier(Polynomial lhs) {
		this.lhs=lhs;
	}



	public SolvedGraph getFeatures(AnalyzedEquation analyzedEquation) {
		SolvedGraph features = new SolvedTrigFunction(analyzedEquation);
		
		return features;
	}



	private void detectTrig(AnalyzedEquation analyzedEquation) {
		String equat = analyzedEquation.getInputEquation();
		
		hasSin = equat.contains("sin");
		hasCos = equat.contains("cos");
		hasTan = equat.contains("tan");
		
		
	}
	
}
