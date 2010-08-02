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
            "vertex", "slope"
            };
	
	protected PolynomialClassifier PC;

	public SolvedAbsoluteValue(AnalyzedEquation ae) {
		super(ae, "absolute value");
		PC = (PolynomialClassifier) ae.getClassifier();
		
		
		
		Polynomial poly = ae.getLhs();
		
		Expression[] coeffs =poly.getCoefficientsAsExpressions("x");
	
		System.out.println(coeffs[0]);
		
		System.out.println("TEST POINT");
		//initialize();
		
		
		//TODO: Add a call to use Regex to extract the equation with the abs
		
		//TODO: Run the extracted equation thru the MDE recursively, if possible, to extract data
		
		
		
		
		
	}

	private void initialize() {
		
		
		 //PointXY vertex = new PointXY(QC.UV2XY(vertexUV));
		 //putFeature("vertex", vertex);
	}
	
	

}
