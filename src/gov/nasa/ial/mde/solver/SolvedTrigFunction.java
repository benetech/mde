package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedTrigFunction extends SolvedXYGraph{

	public SolvedTrigFunction(){
		super();
	}
	
	public SolvedTrigFunction(AnalyzedEquation e) {
		super(e);
	}
	
	public SolvedTrigFunction(AnalyzedEquation analyzedEquation, String graphName) {
		
		super(analyzedEquation, graphName);
		System.out.println("here");
	}
}
