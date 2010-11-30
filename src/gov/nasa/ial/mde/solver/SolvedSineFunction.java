package gov.nasa.ial.mde.solver;


import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.solver.classifier.TrigClassifier;
import gov.nasa.ial.mde.solver.features.individual.AmplitudeFeature;
import gov.nasa.ial.mde.solver.features.individual.FrequencyFeature;
import gov.nasa.ial.mde.solver.features.individual.OffsetFeature;
import gov.nasa.ial.mde.solver.features.individual.PeriodFeature;
import gov.nasa.ial.mde.solver.features.individual.PhaseFeature;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

public class SolvedSineFunction extends SolvedTrigFunction implements FrequencyFeature, AmplitudeFeature, PhaseFeature, OffsetFeature, PeriodFeature{

	protected String[] newFeatures = {"frequency" , "amplitude", "phase", "offset", "period"};
	
	protected TrigClassifier TC;
	
		
	public SolvedSineFunction(AnalyzedEquation analyzedEquation) {
		super(analyzedEquation, "sine function");
		
		TC  = (TrigClassifier) analyzedEquation.getClassifier();
		String equat = analyzedEquation.printOriginalEquation();
		
		//System.out.println(equat);
		
		String[] parts = equat.split("\\)");
		
		// TODO improve the spliting 
		
		/*for(int i = 0; i <= parts.length;i++)
		{
			System.out.println(i);
			System.out.println(parts[i]);	
		}*/
		
		System.out.println(parts.length);
		
		for(int i = 0; i < (parts.length);i++)
		{
			System.out.println(parts[i]);
			parts[i]= parts[i] +")";
		}

		//parts[0]= parts[0] +")";
		
		String insideSIN = "sin\\(([^)\\n]*)\\)";
		String innerEquat=parts[0].replaceAll(insideSIN,"$1");
		
		Solver solver = new Solver();
		solver.add(innerEquat);
	    solver.solve();   
	    
	    Solution solution = solver.get(0);
	    SolvedGraph features = solution.getFeatures();
		
	    // for a basic sinusoid y=A*sin(Bx+C)+D
	    // amplitude = A
	    // period = 2*pi / |B|
	    // frequency = |B| / 2*pi
	    // phase shift = C/B ???
	    // offset = D
	    
	    
	    double A = 1;
	    double B = Double.NaN;
	    double C = Double.NaN;
	    double D = Double.NaN;
	    
	    double amplitude = Double.NaN;
		String period= null;
		String frequency = null;
		double phase = Double.NaN;
		double offset = Double.NaN;
		IntervalXY domain = null; // domain
		IntervalXY range = null; // Range
		
		
		if(parts.length>=2)
		{
			//D = Double.valueOf(parts[1]);
			D = Double.valueOf(parts[parts.length-1]);
		}
		else
		{
			D = 0;
		}

	    C = ((SolvedLine) features).getYIntercept();
	    
	    String getCoeff = "(-?\\d*[\\./]?\\d*)\\*sin";
    	parts[0]=parts[0].replace("y", "");
    	parts[0]=parts[0].replace("=", "");
    	parts[0]=parts[0].replace(" ", "");
    	String temp= parts[0].replaceFirst(getCoeff, "$1----");
    	if(temp.contains("----")){
    		A = Double.valueOf((temp.split("----")[0]));
    	}
    	
    	B = ((SolvedLine) features).getSlope();
    	
    	phase = -C/B;
    	    	
    	//TODO:  Create a method to give a more well define value, such as 2/3 pi or 5/6 pi or 1/4
    	amplitude = A;
    	
    	period = 2.0 /(Math.round((Math.abs(B*4)))/4.0) +"pi"; //2*pi/b
    	frequency =(((Math.round((Math.abs(B)) *4))/ 4.0))/2.0 + "/pi"; //b/2pi
    	offset = D;
    	    	
 	    domain = new IntervalXY(analyzedEq.getActualVariables()[0], Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
 	    range = new IntervalXY(analyzedEq.getActualVariables()[1], - Math.abs(amplitude) + offset, Math.abs(amplitude)+offset);
    	
    	putNewFeatures(newFeatures);    	
    	putFeature("amplitude", amplitude + "");
    	putFeature("phase", phase + "");
    	putFeature("offset", offset + "");
    	putFeature("frequency", frequency);
    	putFeature("period", period);
    	putFeature("domain", domain);
    	putFeature("range", range);
        
	}

	public String getFrequency() {
		Object value = this.getValue(FrequencyFeature.PATH, FrequencyFeature.KEY);
		String frequencyString = (String)value;
		System.out.println("Getting frequency.\nFrequency is : " + frequencyString);
		return frequencyString;
	}
	
	public String getPeriod() {
		Object value = this.getValue(PeriodFeature.PATH, PeriodFeature.KEY);
		String periodString = (String)value;
		System.out.println("Getting period.\nPeriod is : " + periodString);
		return periodString;
	}

	public double getAmplitude() {
		Object value = this.getValue(AmplitudeFeature.PATH, AmplitudeFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Amplitude.\nAmplitude is : " + doubleValue);
		return doubleValue;
	}

	public double getPhase() {
		Object value = this.getValue(PhaseFeature.PATH, PhaseFeature.KEY);
		Double doubleValue = new Double((String)value);
		System.out.println("Getting Phase.\nPhase is : " + doubleValue);
		return doubleValue;
	}

	public double getOffset() {
		Object value = this.getValue(OffsetFeature.PATH, OffsetFeature.KEY);
		Double doubleValue = new Double((String)value);	
		System.out.println("Getting Offset.\nOffset is : " + doubleValue);
		return doubleValue;
	}

	public static void main(String[] args){
		String getCoeff = "(-?\\d*[\\./]?\\d*)\\*sin";
		String insideSIN = "sin\\(([^)\\n]*)\\)";
		String getOffset = "sin\\([^)\\n]*\\)([\\+\\-]\\d*[\\./]?\\d*)";
		String all = "(-?\\d*[\\./]?\\d*)\\*sin\\(([^)\\n]*)\\)";
		String[] test = {"y= sin( x)","y=sin(4 * x)", "y=3 * sin(4*x+20)", "y=4.3*sin(4*x+432)+9"," y=5 /3 * sin( x/3)-4"};
		
		for(int i= 0; i<test.length; i++){
			test[i] = test[i].replaceAll(" ", "");
			System.out.println("\nTest case "+i);
			System.out.println("Equation: " + test[i]);
			System.out.println("   Coeff: " + test[i].replaceAll(getCoeff, "____ $1 ____"));
			System.out.println("    Sine: " + test[i].replaceAll(insideSIN, "____ $1 ____"));
			System.out.println("  Offset: " + test[i].replaceAll(getOffset, "____ $1 ____"));
			System.out.println("     All: " + test[i].replaceAll(all, "____ $1 ____ $2 ____"));
		}
	}
	
	
}
