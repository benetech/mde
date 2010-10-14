package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;


import gov.nasa.ial.mde.solver.features.individual.PhaseFeature;


public class SolvedCosineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeature, PhaseFeature, OffsetFeature{
	
	protected String[] newFeatures = {"frequency" , "amplitude", "phase", "offset", "period"};
	
	protected TrigClassifier TC;
	
	
	
	public SolvedCosineFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "cosine function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		String[] parts = equat.split("\\)");
		
		// TODO improve the spliting 
		
		for(int i = 0; i < parts.length;i++)
		{
			//System.out.println(parts[i]);
		}
		

		parts[0]= parts[0] +")";
		
		String insideCOS = "cos\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideCOS,"$1");
		
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
		
		
	 // for a basic sinusoid y=A*cos(Bx+C)+D
	    // amplitude = A
	    // period = 2*pi / |B|
	    // frequency = |B| / 2*pi
	    // phase shift = C/B ???
	    // offset = D
	    
	    
	    double A = 1;
	    double B = Double.NaN;
	    double C = Double.NaN;
	    double D = Double.NaN;
	    
	    double amplitude = Double.NaN;
		String period= null;
		String frequency = null;
		double phase = Double.NaN;
		double offset = Double.NaN;
		IntervalXY domain = null; // domain
		IntervalXY range = null; // Range
		
		
		if(parts.length>=2)
		{
			D = Double.valueOf(parts[1]);
		}
		else
		{
			D = 0;
		}

	    C = ((SolvedLine) features).getYIntercept();
	    
	    String getCoeff = "(-?\\d*\\.?\\d*)\\*cos";
    	parts[0]=parts[0].replace("y", "");
    	parts[0]=parts[0].replace("=", "");
    	parts[0]=parts[0].replace(" ", "");
    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
    	if(temp.contains("----")){
    		A = Double.valueOf((temp.split("----")[0]));
    	}
    	
    	B = ((SolvedLine) features).getSlope();
    	
    	phase = -C/B;
    	    	
    	//TODO:  Create a method to give a more well define value, such as 2/3 pi or 5/6 pi or 1/4
    	amplitude = A;
    	
    	period = 2.0 /(Math.round((Math.abs(B*4)))/4.0) +"pi"; //2*pi/b
    	frequency =(((Math.round((Math.abs(B)) *4))/ 4.0))/2.0 + "/pi"; //b/2pi
    	offset = D;
    	    	
 	    domain = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
 	    range = new IntervalXY(analyzedEq.getActualVariables()[1], - Math.abs(amplitude) + offset, Math.abs(amplitude)+offset);
    	
    	putNewFeatures(newFeatures);    	
    	putFeature("amplitude", amplitude + "");
    	putFeature("phase", phase + "");
    	putFeature("offset", offset + "");
    	putFeature("frequency", frequency);
    	putFeature("period", period);
    	putFeature("domain", domain);
    	putFeature("range", range);
    
    }



	public String getFrequency() {
		Object value = this.getValue(FrequencyFeature.PATH, FrequencyFeature.KEY);
		String frequencyString = (String)value;
		System.out.println("Getting frequency.\nFrequency is : " + frequencyString);
		return frequencyString;
	}
	

	public double getAmplitude() {
		Object value = this.getValue(AmplitudeFeature.PATH, AmplitudeFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Amplitude.\nAmplitude is : " + doubleValue);
		return doubleValue;
	}


	public double getOffset() {
		Object value = this.getValue(OffsetFeature.PATH, OffsetFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Offset.\nOffset is : " + doubleValue);
		return doubleValue;
	}


	public double getPhase() {
		Object value = this.getValue(PhaseFeature.PATH, PhaseFeature.KEY);
		Double doubleValue = new Double((String)value);
		System.out.println("Getting Phase.\nPhase is : " + doubleValue);
		return doubleValue;
	}
}
