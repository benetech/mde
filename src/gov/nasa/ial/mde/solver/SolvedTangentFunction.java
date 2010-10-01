package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AsymptoteFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedTangentFunction extends SolvedTrigFunction implements FrequencyFeature, OffsetFeature, AsymptoteFeature{

	protected String[] newFeatures = {"frequency", "phase", "offset", "shift", "period"};

	
	protected TrigClassifier TC;
	private final double PI = 3.142;
	
	public SolvedTangentFunction(AnalyzedEquation e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	public String getFrequency() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAsymptotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
