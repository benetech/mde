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
	
	public SolvedTangentFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "tangent function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		String[] parts = equat.split("\\)");
		
		// TODO improve the spliting 
		
		for(int i = 0; i < parts.length;i++)
		{
			//System.out.println(parts[i]);
		}
		

		parts[0]= parts[0] +")";
		
		String insideTAN = "tan\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideTAN,"$1");
		
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
		// TODO Auto-generated constructor stub
	}

	public String getFrequency() {
		Object value = this.getValue(FrequencyFeature.PATH, FrequencyFeature.KEY);
		String frequencyString = (String)value;
		System.out.println("Getting frequency.\nFrequency is : " + frequencyString);
		return frequencyString;
	}

	public double getOffset() {
		Object value = this.getValue(OffsetFeature.PATH, OffsetFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Offset.\nOffset is : " + doubleValue);
		return doubleValue;
	}

	public String getAsymptotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
