package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Expression;
import gov.nasa.ial.mde.solver.symbolic.Polynomial;

public class SolvedSquareRoot extends SolvedXYGraph {

	protected String[] newFeatures = { 
            "vertex", "slope"
            };
	
	protected PolynomialClassifier PC;

	public SolvedSquareRoot(AnalyzedEquation ae) {
		super(ae, "square root");
		PC = (PolynomialClassifier) ae.getClassifier();
		
		
		
		//Polynomial poly = ae.getLhs();
		
	//	Expression[] coeffs =poly.getCoefficientsAsExpressions("x");
	
		//System.out.println(coeffs[0]);
		
		//System.out.println("TEST POINT");
		//initialize();
		
		
		
		
		
	}

	private void initialize() {
		
		
		 //PointXY vertex = new PointXY(QC.UV2XY(vertexUV));
		 //putFeature("vertex", vertex);
	}
}
