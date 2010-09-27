package gov.nasa.ial.mde.solver;


import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeaure;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeaure{

	protected String[] newFeatures = {"frequency" , "amplitude"};
	
	protected TrigClassifier TC;
	private final double PI = 3.142;
	
	public SolvedSineFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "Sine Function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		String[] parts = equat.split("\\)");
		
		// TODO improve the spliting 
		
		for(int i = 0; i < parts.length;i++)
		{
			System.out.println(parts[i]);
		}
		parts[0]= parts[0] +")";
		
		String insideSIN = "sin\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideSIN,"$1");
		
		String amplitude= null;
		String frequency= null;
		double phase = Double.NaN;
		double offset = Double.NaN;
		
		
		offset = Double.valueOf(parts[1]);
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
	    
	    phase = ((SolvedLine) features).getYIntercept();
	    
	   
		
	}

	public String getFrequency() {
		return null;
	}

	public String getAmplitude() {
		return null;
	}

}
