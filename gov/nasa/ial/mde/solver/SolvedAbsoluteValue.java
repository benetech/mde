package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;
import gov.nasa.ial.mde.solver.symbolic.Expression;
import gov.nasa.ial.mde.solver.symbolic.RationalExpression;

public class SolvedAbsoluteValue extends SolvedXYGraph{

	public SolvedAbsoluteValue(AnalyzedEquation ae) {
		super(ae);
		
		 Expression e = ae.getFunction();
		 
		 if (e == null)
	            throw new IllegalArgumentException("Input equation is not a function.");
		 
		 RationalExpression r = new RationalExpression(new Expression(e.toString()));
		
		 System.out.println(r);
		// TODO Auto-generated constructor stub
	}
	
	

}
