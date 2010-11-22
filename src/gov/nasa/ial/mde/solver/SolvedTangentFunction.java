package gov.nasa.ial.mde.solver;

import java.util.ArrayList;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AsymptoteFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

@SuppressWarnings("all")
public class SolvedTangentFunction extends SolvedTrigFunction implements FrequencyFeature, OffsetFeature, AsymptoteFeature{

	protected String[] newFeatures = {"frequency", "phase", "offset", "shift", "period", "orientation"};

	
	protected TrigClassifier TC;
	
	public SolvedTangentFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "tangent function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		String[] parts = equat.split("\\)");
		
		double A,B,C,D;
		double phase = Double.NaN;
		double offset = Double.NaN;
		String baseAsymptote = null;
		String period = "";
		double amplitude = Double.NaN;
		String orientation = "WARRRGL!";
		    
		IntervalXY domain = null; // domain
		IntervalXY range = null; // Range
		
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
	    
	    if(parts.length>=2)
		{
	    	D = Double.valueOf(parts[1]);
		}
		else
		{
			D = 0;
		}
	    
	    
	    String getCoeff = "(-?\\d*\\.?\\d*)\\*tan";
	    A = 1;
	    parts[0]=parts[0].replace("y", "");
    	parts[0]=parts[0].replace("=", "");
    	parts[0]=parts[0].replace(" ", "");
    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
    	if(temp.contains("----")){
    		A = Double.valueOf((temp.split("----")[0]));
    	}
	    
	    B = ((SolvedLine) features).getSlope();
	    C = ((SolvedLine) features).getYIntercept();
	    
	    
	    //B affects the period
	    //B = 1 creates asymptotes at -pi/2 and pi/2 
	    //B = pi creates asymptotes at -.5 and .5
	    
	    
	    if(Math.signum(A*B)==1){
	    	orientation = "ascending";
	    }
	    else if(Math.signum(A*B)==-1){
	    	orientation = "decending";
	    }
	    else{
	    	orientation = "0";
	    }
	    
	    phase = -C/B;
	    offset = D;
	    
	    domain = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
 	    
	    period = 1.0 /(Math.round((Math.abs(B*4)))/4.0) +"pi";
	    System.out.println(period);
	    
	    putNewFeatures(newFeatures);
	    putFeature("phase", phase + "");
    	putFeature("offset", offset + "");
    	putFeature("domain", domain);
    	putFeature("orientation", orientation);
	    
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

	public String[] getAsymptotes() {
		Object values = this.getValues(AsymptoteFeature.PATH, AsymptoteFeature.KEY);
		ArrayList list = (ArrayList)values;
		System.out.println("The size of the returned array is"+list.size());
		String[] asymptotes = new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			System.out.println(list.get(i));
			asymptotes[i]=(String) list.get(i);
		}
		
		return asymptotes;
	}

}
