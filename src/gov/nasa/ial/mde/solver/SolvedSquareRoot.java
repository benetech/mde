package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.classifier.QuadraticClassifier;
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
		
		//System.out.print("ae.toString Equation: " + equat);
		
		
		//equat
		
		
		
		
		/*
		 * 
		QuadraticClassifier QC=new QuadraticClassifier(poly);
		System.out.println("\n " + QC.getNormalizedEquation());
		double[] coeffs =QC.getNormalizedCoefficients();
		for(int i = 0 ; i<coeffs.length ; i++)
		{
			System.out.println(coeffs[i]);
		}
		System.out.println("Inside a SolvedSquareRoot");
		System.out.println("Polynomial is :" + poly);
		
		
		
		
		//here we need to calculate the vertex
	
				
		
		
		
		*
		 * 
		 * case RIGHT :
                vertexUV[1] = transUV[1];
                vertexUV[0] = coeffs[4];
                axisInclination = alpha;
                break;

            case LEFT :
                vertexUV[1] = transUV[1];
                vertexUV[0] = coeffs[4];
                axisInclination = alpha + 180.0;
                break;
		 * 
		 * 
		
		
		
		
		IntervalXY D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        D.setEndPointExclusions(IntervalXY.EXCLUDE_LOW_X | IntervalXY.EXCLUDE_HIGH_X);
        putFeature("domain", D);
		
		*/
        
        
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
