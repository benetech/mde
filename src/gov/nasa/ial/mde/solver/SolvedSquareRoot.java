package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
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
		
		//Send that part thru the MDE
		
		// get vertex
		/* assuming the form is sqrt(mx + b) + c, 
		 * x intercept is the -b/m
		 * y intercept is  c 
		 */
		
		
		double yVertice = Double.valueOf(parts[1]);
		
		
				
	}
}
