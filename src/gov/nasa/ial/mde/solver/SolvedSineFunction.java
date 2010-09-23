package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeaure;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeaure{

	public SolvedSineFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "Sine Function");	
	}

	public double getFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getAmplitude() {
		// TODO Auto-generated method stub
		return 0;
	}

}
