package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.math.PointXY;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.features.individual.SlopeFeature;
import gov.nasa.ial.mde.solver.features.individual.VertexFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSquareRoot extends SolvedXYGraph implements VertexFeature {

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
	
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
	    
		
		//get Slope and "yIntercept"
	    double slope =Double.NaN;
	    double intercept=Double.NaN;
	    double xVertice = Double.NaN;
	    double yVertice = Double.NaN;
		
	    
		
	    if(features instanceof SolvedLine){
	    	slope=((SlopeFeature) features).getSlope();
	    	
	    	if(parts.length>=2)
			{
				yVertice = Double.valueOf(parts[1]);
			}
			else
			{
				yVertice= 0;
			}
	    	
	    	
	    	intercept =((SolvedLine) features).getYIntercept();
	    	xVertice = -intercept/slope;
	    	
	    	String getCoeff = "(-?\\d*\\.?\\d*)\\*sqrt";
	    	double coeff= 1;
	    	parts[0]=parts[0].replace("y", "");
	    	parts[0]=parts[0].replace("=", "");
	    	parts[0]=parts[0].replace(" ", "");
	    	
	   //TODO: Work on array out of bounds error 	
	    	
	    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
	    	if(temp.contains("----")){
	    		coeff = Double.valueOf((temp.split("----")[0]));
	    	}
	    	
	    	IntervalXY D, R;
	    	
	    	
	    	String orientation;
	    	if(coeff>0 && slope>0){
	    		orientation="quadrant I";
	    		D = new IntervalXY(analyzedEq.getActualVariables()[0], xVertice, Double.POSITIVE_INFINITY);
	    		//D.setEndPointExclusions(IntervalXY.EXCLUDE_LOW_X | IntervalXY.EXCLUDE_HIGH_X);
                R = new IntervalXY(analyzedEq.getActualVariables()[1], yVertice, Double.POSITIVE_INFINITY);
	    	}else if(coeff>0 && slope<0){
	    		orientation="quadrant II";
	    		D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, xVertice);
	    		//D.setEndPointExclusions(IntervalXY.EXCLUDE_LOW_X | IntervalXY.EXCLUDE_HIGH_X);
	    		R = new IntervalXY(analyzedEq.getActualVariables()[1], yVertice, Double.POSITIVE_INFINITY);
	    	}else if(coeff<0 && slope>0){
	    		orientation="quadrant IV";
	    		D = new IntervalXY(analyzedEq.getActualVariables()[0], xVertice, Double.POSITIVE_INFINITY);
	   
                R = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NEGATIVE_INFINITY, yVertice);
	    	}else if(coeff<0 && slope<0){
	    		orientation="quadrant III";
	    		D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, xVertice);
                R = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NEGATIVE_INFINITY, yVertice);
	    	}
	    	else{
	    		orientation="you should never be here.";
	    		D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NaN, Double.NaN);
                R = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NaN , Double.NaN);
	    	}
	    	
	    	
	    	PointXY vertex = new PointXY( new double[]{xVertice,yVertice});
			System.out.println(vertex.toString());
			
	    	putNewFeatures(newFeatures);
			putFeature("vertex", vertex);
			putFeature("orientation", orientation);
			putFeature("domain", D);
			putFeature("range", R);
	    }else
	    {
	    	
	    	System.out.println("sqrt does not have a linear function inside it");
	    	//TODO: figure out a way to get a more general description instead.
	    }	
	}

	public PointXY getVertex() {
		Object value = this.getValue(VertexFeature.PATH, VertexFeature.KEY);
		String vertexString = (String)value;
		System.out.println("Getting vertex.\nVertex is : " + vertexString);
		String[] split = vertexString.split(",");
		split[0] = split[0].replace("(", "");
		split[1] = split[1].replace(")", "");
		double xPos = Double.valueOf(split[0]);
		double yPos = Double.valueOf(split[1]);
		
		return (new PointXY(xPos,yPos));
	}
}
