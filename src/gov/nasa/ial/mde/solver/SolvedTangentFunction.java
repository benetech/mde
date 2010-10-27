package gov.nasa.ial.mde.solver;

import java.util.ArrayList;

import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AsymptoteFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

@SuppressWarnings("all")
public class SolvedTangentFunction extends SolvedTrigFunction implements FrequencyFeature, OffsetFeature, AsymptoteFeature{

	protected String[] newFeatures = {"frequency", "phase", "offset", "shift", "period"};

	
	protected TrigClassifier TC;
	
	public SolvedTangentFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "tangent function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		String[] parts = equat.split("\\)");
		
		double A,B,C,D;
		double xOffset = Double.NaN;
		double yOffset = Double.NaN;
		String baseAsymptote = null;
		double period = Double.NaN;
		double amplitude = Double.NaN;
		    
		
		
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
