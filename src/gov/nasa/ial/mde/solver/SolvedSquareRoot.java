package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.PointXY;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.graphinterfaces.SlopeGraph;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Polynomial;

public class SolvedSquareRoot extends SolvedXYGraph {

	protected String[] newFeatures = {"vertex" , "orientation"};
	
	protected PolynomialClassifier PC;

	public SolvedSquareRoot(AnalyzedEquation ae) {
		super(ae, "square root");
		
		
		//can be solved by making some assumptions	
		PC = (PolynomialClassifier) ae.getClassifier();
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
	    SolvedGraph features = solution.getFeatures();
	    
	    
	    //String xmlString =solution.getFeatures().getXMLString(); 
	    
	    
	    
	    //System.out.println(xmlString);
		
		//get Slope and "yIntercept"
	    double slope =Double.NaN;
	    double intercept=Double.NaN;
	    double xVertice = Double.NaN;
	    double yVertice = Double.NaN;
		
		
	    
			    
	    if(features instanceof SlopeGraph){
	    	slope=((SlopeGraph) features).getSlope();
	    	
	    	
	    	if(parts.length>=2)
			{
				yVertice = Double.valueOf(parts[1]);
			}
			else
			{
				yVertice= 0;
			}
	    	
	    	xVertice = -intercept/slope;
	    }else
	    {
	    	
	    }
	    
	    
			
		
		PointXY vertex = new PointXY( new double[]{xVertice,yVertice});
		System.out.println(vertex.toString());
		
		putNewFeatures(newFeatures);
		putFeature("vertex", vertex);
		putFeature("orientation", "TEST: the orientation is XXXXX");
		
	}
	
	
	public static void main(String[] args){
		String test = "sqrt(x+4+3332) +5 ";
		
		String insideSQRT = "sqrt\\(" +
		"([^)\\n]*)" +
		"\\)";
		
		
		//String checkNegative = "-?x[ ]*sqrt";
		
		
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
