package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.math.PointXY;
import gov.nasa.ial.mde.solver.classifier.PolynomialClassifier;
import gov.nasa.ial.mde.solver.features.individual.SlopeFeature;
import gov.nasa.ial.mde.solver.features.individual.VertexFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedAbsoluteValue extends SolvedXYGraph implements VertexFeature{
	
	protected String[] newFeatures = { 
            "vertex", "absDirection"};
	
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
		
		String insideABS = "abs\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideABS,"$1");
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
	
	    if(features instanceof SolvedLine){		    
		    //for a linear equation 
		    //form of a*|mx+b| + c
		    // vertex is ( -ab/m, c)
		    // slope of am
	    	
	    	double slope =Double.NaN;
		    double intercept=Double.NaN;
		    double xVertice = Double.NaN;
		    double yVertice = Double.NaN;
		    
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
	    	
	    	String getCoeff = "(-?\\d*\\.?\\d*)\\*abs";
	    	double coeff= 1;
	    	parts[0]=parts[0].replace("y", "");
	    	parts[0]=parts[0].replace("=", "");
	    	parts[0]=parts[0].replace(" ", "");
	    	

	    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
	    	if(temp.contains("----")){
	    		coeff = Double.valueOf((temp.split("----")[0]));
	    	}
	    	xVertice = -(intercept*coeff)/slope;
	    	
	    	IntervalXY D, R;
	    	PointXY vertex = new PointXY( new double[]{xVertice,yVertice});
	    	D = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	    	
	    	String direction;
	    	if(coeff>0){
                R = new IntervalXY(analyzedEq.getActualVariables()[1], yVertice, Double.POSITIVE_INFINITY);
                direction="up";
	    	}else{
	    		R = new IntervalXY(analyzedEq.getActualVariables()[1], Double.NEGATIVE_INFINITY, yVertice);
                direction="down";
	    	}
	    	
	    	
	    	putNewFeatures(newFeatures);
			putFeature("vertex", vertex);
			putFeature("absDirection", direction);
			putFeature("domain", D);
			putFeature("range", R);
	    	
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
