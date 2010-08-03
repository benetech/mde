package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.PointXY;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.classifier.QuadraticClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Equation;
import gov.nasa.ial.mde.solver.symbolic.Expression;
import gov.nasa.ial.mde.solver.symbolic.Polynomial;
import gov.nasa.ial.mde.solver.symbolic.RationalExpression;

public class SolvedAbsoluteValue extends SolvedXYGraph{
	
	protected String[] newFeatures = { 
            "vertex"};
	
	protected PolynomialClassifier PC;

	public SolvedAbsoluteValue(AnalyzedEquation ae) {
		super(ae, "absolute value");
		PC = (PolynomialClassifier) ae.getClassifier();
		
		String equat= ae.printOriginalEquation();
		String[] parts =equat.split("\\)");
		
		for(int i = 0; i < parts.length;i++)
		{
			System.out.println(parts[i]);
		}
		parts[0]= parts[0] +")";
		
		//TODO: Add a call to use Regex to extract the equation with the abs
		String insideABS = "abs\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideABS,"$1");
		
		
		
		//TODO: Run the extracted equation thru the MDE recursively, if possible, to extract data
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    String xmlString =solution.getFeatures().getXMLString(); 
	    //TODO: make an isLinear check for a lot of this.  Perhaps we need a LinearAbs and a LinearSQRT
	    //TODO: actually putfeatures could be handled on different functions in this class 
	    
	    
	    //for a linear equation 
	    //form of a|mx+b| + c
	    // vertex is ( -ab/m, c)
	    // slope of am
	        
		

	}
	

}
