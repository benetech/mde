package gov.nasa.ial.mde.solver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.classifier.QuadraticClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Expression;
import gov.nasa.ial.mde.solver.symbolic.Polynomial;

public class SolvedSquareRoot extends SolvedXYGraph {

	protected String[] newFeatures = { 
            "vertex" };
	
	protected PolynomialClassifier PC;

	public SolvedSquareRoot(AnalyzedEquation ae) {
		super(ae, "square root");
		
		
		//can be solved by making some assumptions	
		PC = (PolynomialClassifier) ae.getClassifier();
		Polynomial poly = ae.getLhs();
		
		String equat= ae.printOriginalEquation();
		
		String[] parts =equat.split("\\)");
		
		for(int i = 0; i < parts.length;i++)
		{
			System.out.println(parts[i]);
		}
		
		parts[0]= parts[0] +")";
		
		
		//Get the inner part of the SQRT( _______)
		
		
		String insideSQRT = "sqrt\\(([^)\\n]*)\\)";
		
		String innerEquat=parts[0].replaceAll(insideSQRT,"$1");
		
	
		
		//Send that part thru the MDE
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();
	    
	    //TODO: make an isLinear check for a lot of this
	    Solution solution = solver.get(0);
	    String innerFeatures =solution.getFeatures().getXMLString();
	    
	    
	    //get Slope and "yIntercept"
	   // double slope= Double.valueOf(innerFeatures.);
	    
	   
		
		// get vertex
		/* assuming the form is sqrt(mx + b) + c, 
		 * x intercept is the -b/m
		 * y intercept is  c 
		 */
		
	    double xVertice;
		double yVertice;
		
		if(parts.length>=2)
		{
			
			yVertice = Double.valueOf(parts[1]);
		}
		else
		{
			yVertice= 0;
		}
		
		System.out.println(yVertice);
	
		
		
		
	}
	
	
	public static void main(String[] args){
		String test = "sqrt(x+4+3332) +5 ";
		
		String insideSQRT = "sqrt\\(" +
		"([^)\\n]*)" +
		"\\)";
		
		/*
		Pattern pattern= Pattern.compile(insideSQRT);
		Matcher matcher = pattern.matcher(test);
		matcher.find();
		*/
		
		
		System.out.println(test);
		test = test.replaceFirst(insideSQRT, "$1");
		System.out.println(test);
		
		
		
		
		
	}
	
}
