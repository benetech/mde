package gov.nasa.ial.mde.solver;


import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.PhaseFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeature, PhaseFeature{

	protected String[] newFeatures = {"frequency" , "amplitude", "phase", "offset"};
	
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
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
		
		String amplitude = null;
		double amplitude_value = Double.NaN;
		String frequency = null;
		double frequency_value = Double.NaN;
		String phase = null;
		double phase_value = Double.NaN;
		double offset = Double.NaN;
		String shift =null;
		double shift_value = Double.NaN;
		
		
		if(parts.length>=2)
		{
			offset = Double.valueOf(parts[1]);
		}
		else
		{
			offset = 0;
		}

	    phase_value = ((SolvedLine) features).getYIntercept();
	    
    	String getCoeff = "(-?\\d*\\.?\\d*)\\*sin";
    	double coeff= 1;
    	parts[0]=parts[0].replace("y", "");
    	parts[0]=parts[0].replace("=", "");
    	parts[0]=parts[0].replace(" ", "");
    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
    	if(temp.contains("----")){
    		coeff = Double.valueOf((temp.split("----")[0]));
    	}
    	
    	amplitude_value = coeff;
    	frequency_value = ((SolvedLine) features).getSlope();
    	shift_value = phase_value/frequency_value;
    	
    	//TODO:  Create a method to give a more well define value, such as 2/3 pi or 5/6 pi or 1/4
    	
    	phase = ((Math.round((phase_value/PI) *4))/ 4.0) +" pi";
    	
    	
    	System.out.println("Amplitude: " + amplitude_value);
    	System.out.println("Frequency: " + frequency_value);
    	System.out.println("Phase value: " + phase_value);
    	System.out.println("Phase: " + phase);
    	System.out.println("Offset: "+ offset);
    	System.out.println("Shift:" + shift_value);
    	
    	
	}

	public String getFrequency() {
		return null;
	}

	public double getAmplitude() {
		return Double.NaN;
	}

	public String getPhase() {
		// TODO Auto-generated method stub
		return null;
	}

}
