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

	protected String[] newFeatures = {"frequency", "phase", "offset", "shift", "period", 
			"orientation", "asymptotes", "rate"};

	
	protected TrigClassifier TC;
	
	public SolvedTangentFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "tangent function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		System.out.println(equat);
		String[] parts = equat.split("\\)");
		
		double A,B,C,D;
		double phase = Double.NaN;
		double offset = Double.NaN;
		String baseAsymptote = "";
		String period = "";
		String frequency = "";
		double amplitude = Double.NaN;
		String orientation = "WARRRGL!";
		String interval= "";
		
		
		    
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
	    System.out.println(B);
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
	    
	    offset = D;
	    
	    
 	    //pi/B
 	    //need some dectection for PI in B
 	    
 	    period = calculatePeriod(B);
 	    
	   //period = (Math.round((Math.abs(1.0/B)*100))/100.0) + "pi";
	    
	    frequency = (Math.round((Math.abs((B)*100)))/100.0) +"/pi";
	    
	    baseAsymptote = (Math.round((Math.abs((Math.PI/B)*100)))/100.0) - C+"";
	    
	    
	  //domain = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
 	    range = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	    
	    
	    putNewFeatures(newFeatures);
    	putFeature("offset", offset + "");
    	putFeature("domain", "something with a lot of funky asymptotes");
    	putFeature("range", range);
    	putFeature("orientation", orientation);
    	putFeature("rate",A+"");
		// TODO Auto-generated constructor stub
	}

	private String calculatePeriod(double coeff) {
		if(this.isMultipleOfPi(coeff)){
			double ret = coeff/3.142;
			return "1/"+ret;
		}else{
			return "pi/"+ coeff;
		}
	}

	public String getFrequency(){
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
