package gov.nasa.ial.mde.solver;


import java.util.ArrayList;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeature;
import gov.nasa.ial.mde.solver.features.individual.DomainFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;
import gov.nasa.ial.mde.solver.features.individual.PhaseFeature;
import gov.nasa.ial.mde.solver.features.individual.ShiftFeature;
import gov.nasa.ial.mde.solver.features.individual.SlopeFeature;
import gov.nasa.ial.mde.solver.features.individual.YInterceptFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeature, PhaseFeature, OffsetFeature, ShiftFeature{

	protected String[] newFeatures = {"frequency" , "amplitude", "phase", "offset", "shift"};
	
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
		
		double amplitude = Double.NaN;
		String period= null;
		double period_value =Double.NaN;
		String frequency = null;  //
		double frequency_value = Double.NaN; // angular frequency, measured in radians/second 
		String phase = null;  // another time we need to use pi	
		double phase_value = Double.NaN;
		double offset = Double.NaN;
		double shift = Double.NaN;  //shift is a pi divided by b pi.  If x were time in seconds, then this is how far this appears to be shifted
		double shift_value= Double.NaN;
		IntervalXY D = null; // domain
		IntervalXY R = null; // Range
		
		
		
		
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
    	
    	frequency_value = ((SolvedLine) features).getSlope();
    	shift_value = phase_value/frequency_value;
    	
    	
    	
    	//TODO:  Create a method to give a more well define value, such as 2/3 pi or 5/6 pi or 1/4
    	amplitude = coeff;
    	phase = ((Math.round((phase_value/PI) *4))/ 4.0) +" pi";
    	shift = ((Math.round((shift_value/PI) *4))/ 4.0);
    	frequency =(((Math.round((frequency_value/PI) *4))/ 4.0))/2.0 + "/pi"; //b/2pi
    	
    	
 	    D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
 	    R = new IntervalXY(analyzedEq.getActualVariables()[1], - Math.abs(amplitude) + offset, Math.abs(amplitude)+offset);
    	
    	putNewFeatures(newFeatures);    	
    	putFeature("amplitude", amplitude + "");
    	putFeature("phase", phase);
    	putFeature("offset", offset + "");
    	putFeature("shift", shift+ "");
    	putFeature("frequency", frequency);
    	putFeature("domain", D);
    	putFeature("range", R);
    
    	getOffset();
    	getShift();
    	getPhase();
    	getAmplitude();
    	getFrequency();
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

	public String getPhase() {
		Object value = this.getValue(PhaseFeature.PATH, PhaseFeature.KEY);
		String string = (String) value;
		System.out.println("Getting Phase.\nPhase is : " + string);
		return string;
	}

	public double getShift() {
		Object value = this.getValue(ShiftFeature.PATH, ShiftFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Shift.\nShift is : " + doubleValue);		
		return doubleValue;
	}

	public double getOffset() {
		Object value = this.getValue(OffsetFeature.PATH, OffsetFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Offset.\nOffset is : " + doubleValue);
		return doubleValue;
	}
	
}
