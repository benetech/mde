/* 
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 */
package gov.nasa.ial.mde.solver.classifier;

import gov.nasa.ial.mde.math.MultiPointXY;
import gov.nasa.ial.mde.solver.SolvedCubicPolynomial;
import gov.nasa.ial.mde.solver.SolvedEquationData;
import gov.nasa.ial.mde.solver.SolvedGraph;
import gov.nasa.ial.mde.solver.SolvedRationalFunction;
import gov.nasa.ial.mde.solver.SolvedXYGraph;
import gov.nasa.ial.mde.solver.numeric.PolynomialModel;
import gov.nasa.ial.mde.solver.numeric.PolynomialModelBuilder;
import gov.nasa.ial.mde.solver.numeric.QuadraticModel;
import gov.nasa.ial.mde.solver.numeric.RationalModel;
import gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A classifier for Polynomials.
 * 
 * @author ddexter
 * @version 1.0
 * @since 1.0
 */
public class PolynomialClassifier extends MDEClassifier {
    
    private final static int SIZE = 8;
    private final static int DEGREE = SIZE - 1;
    private PolynomialModelBuilder qBuilder = new PolynomialModelBuilder(2, 2);
    private PolynomialModelBuilder rBuilder = new PolynomialModelBuilder(DEGREE, 1);
    private PolynomialModel bestGuess = null;

    /**
     * Incorporates available data points and performs classification
     * 
     * @param points the array of MultiPointXY defining the curve
     * @param worstFit  the largest log base 10 residual for a model
     */
    public PolynomialClassifier(MultiPointXY[] points, double worstFit) {
        int i, n = points.length;

        for (i = 0; i < n; i++) {
            qBuilder.addNewPoint(points[i]);
            rBuilder.addNewPoint(points[i]);
        } // end for i

        PolynomialModel[] rpm = new PolynomialModel[n = 1 + SIZE * SIZE];
        ArrayList finalists = new ArrayList();

        rpm[0] = new QuadraticModel(qBuilder);
        for (i = 1; i < n; i++) {
            rpm[i] = new RationalModel(rBuilder, (i - 1) / SIZE, (i - 1) % SIZE);
        }

        Arrays.sort(rpm, new Comparator() {
            public int compare(Object o1, Object o2) {
                PolynomialModel p1 = (PolynomialModel)o1, p2 = (PolynomialModel)o2;

                if (p1.fit > p2.fit) {
                    return 1;
                }

                if (p1.fit < p2.fit) {
                    return -1;
                }

                return 0;
            } // end compare
        } // end new Comparator
        ); // end sort

        for (i = 0; i < n; i++) {
            if (rpm[i].fit > worstFit) {
                break;
            }

            finalists.add(rpm[i]);
        } // end for i

        if ((n = finalists.size()) == 0) {
            return;
        }

        bestGuess = (PolynomialModel)finalists.get(0);

        for (i = 1; i < n; i++) {
            PolynomialModel t = (PolynomialModel)finalists.get(i);

            if (t.complexity < bestGuess.complexity) {
                bestGuess = t;
            }
        } // end for i
    } // end PolynomialClassifier

    /**
     * Creates a polynomial classifier for the given points.
     * 
     * @param points the function points.
     */
    public PolynomialClassifier(MultiPointXY[] points) {
        this(points, -12.0);
    } // end PolynomialClassifier

    /**
     * Returns the best guess of the polynomial model.
     * 
     * @return the best guess of the polynomial model.
     */
    public PolynomialModel getBestGuess() {
        return bestGuess;
    } // end getBestGuess


    public SolvedGraph getFeatures(AnalyzedEquation analyzedEq) {
    	System.out.println("Inside polynomial classifier");
    	
    	int degree = analyzedEq.getDegree();
    	
    	System.out.println("Degree: " + degree);
    	
        SolvedGraph features;

        if (analyzedEq.isSolvableFunction()) {
            if (!analyzedEq.isPolynomial()){
            	System.out.println("It's not a polynomial????");
            	features = new SolvedEquationData(analyzedEq);
            }
            else if(degree==3){
            	System.out.println("Cubic Polynomial");
            	features = new SolvedCubicPolynomial(analyzedEq);
            }
            else
            {
            	System.out.println("It's a rational function.");
                features = new SolvedRationalFunction(analyzedEq);
            }
        } // end if
        else
        {
        	System.out.println("It's an XYgraph.");
            features = new SolvedXYGraph(analyzedEq);
        }

        // Make sure we add the graphBoundaries feature.
        addGraphBoundariesFeature(analyzedEq, features);

        return features;
    } // end getFeatures
    
//    // Main routine for testing purposes
//    // @param args  the input equation as one or more discrete strings
//    public static void main(String[] args) {
//        gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation ae = 
//            new gov.nasa.ial.mde.solver.symbolic.AnalyzedEquation(
//                gov.nasa.ial.mde.util.StringSplitter.combineArgs(args));
//        MDEClassifier c = ae.getClassifier();
//
//        if (c != null) {
//            System.out.println(c.toString());
//        } // end if
//    } // end main

} // end class PolynomialClassifier
