package gov.nasa.ial.mde.solver.tests;

import gov.nasa.ial.mde.solver.SolvedGraph;
import gov.nasa.ial.mde.solver.SolvedLine;
import gov.nasa.ial.mde.solver.classifier.MDEClassifier;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestCase;

public class SolverTest extends TestCase {
	
	private String[][] linearFormulas = {
	{ // good ones
		"y=x",
		"y=x+3",
		"y=x+5",
	}, 
	{ // bad ones
		"y=x*x",
		"y=x+3*x*x",
		"y=4*x+x*x",
	}
	};
	
	private String[][][] formulas = {
			linearFormulas,
	};
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
	private SolvedGraph loadSolvedGraph(String equation) {
		AnalyzedEquation analyzedEquation = new AnalyzedEquation(equation);
		MDEClassifier classifier = analyzedEquation.getClassifier();
		SolvedGraph solvedGraph = classifier.getFeatures(analyzedEquation);
		return solvedGraph;
	}
	
	private void equationMatches(boolean expected, String expectedClassName, String[] formulas) {
		String actualClassName;
		SolvedGraph solvedGraph;
		for(String formula: formulas) {
			solvedGraph = this.loadSolvedGraph(formula);
			actualClassName = solvedGraph.getClass().getCanonicalName();
			this.classMatch(expected, actualClassName, expectedClassName);
		}
	}
	
	private void classMatch(boolean expected, String actualClassName, String expectedClassName) {
		boolean actual = expectedClassName.equals(actualClassName);
		assertEquals(expected, actual);
	}
	
	public void testLinearMatch() {
		String[] goodLinearFormulas = this.linearFormulas[0];
		String[] badLinearFormulas = this.linearFormulas[1];
		String name = SolvedLine.class.getCanonicalName();
		this.equationMatches(true, name, goodLinearFormulas);
		this.equationMatches(false, name, badLinearFormulas);
	}
	
	private void dump() {
		for(String[][] saa : this.formulas) {
			for(String[] sa : saa) {
				for(String formula : sa) {
					SolvedGraph graph = this.loadSolvedGraph(formula);
					String className = graph.getClass().getCanonicalName();
					System.out.println(formula + " has class "+className);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SolverTest test = new SolverTest();
		test.dump();
	}
}
