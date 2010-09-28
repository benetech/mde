package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedCosineFunction extends SolvedTrigFunction {
	
	protected String[] newFeatures = {"frequency" , "amplitude", "phase", "offset"};
	
	protected TrigClassifier TC;
	private final double PI = 3.142;
	
	
	
	public SolvedCosineFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "Cosine Function");
	}

}
