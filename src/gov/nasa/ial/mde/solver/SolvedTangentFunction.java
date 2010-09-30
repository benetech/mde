package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedTangentFunction extends SolvedTrigFunction {

	protected TrigClassifier TC;
	private final double PI = 3.142;
	
	public SolvedTangentFunction(AnalyzedEquation e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
