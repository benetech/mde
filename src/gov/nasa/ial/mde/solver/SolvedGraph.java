/* 
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 */
package gov.nasa.ial.mde.solver;

import gov.nasa.ial.mde.math.IntervalXY;
import gov.nasa.ial.mde.math.NumberModel;
import gov.nasa.ial.mde.math.PointRT;
import gov.nasa.ial.mde.math.PointXY;

/**
 * The class represents a solved graph.
 * 
 * @author Dr. Robert Shelton
 * @version 1.0
 * @since 1.0
 */
public class SolvedGraph {

    private final static String[] COMPASS_DIRECTIONS = { 
            "East", "ENE", "NE", "NNE", "North", "NNW", "NW", "WNW", 
            "West", "WSW", "SW", "SSW", "South", "SSE", "SE", "ESE" };
    
    private final static String[] GENERAL_DIRECTIONS = { 
    	"nowhere", "upwards", "downwards", "to the right", "to the left" };

    private String[] defaultFeatureNames = { "graphName", "graphBoundaries", "equationType",
            "equationPrint", "originalEquationPrint", "graphDescriptionDomain",
            "graphDescriptionRange", "domain", "range", "abscissaSymbol", "ordinateSymbol",
            "abscissaLabel", "ordinateLabel", "coordinateSystem", "graphClosure",
            "xIntercept(s)", "yIntercept(s)", "maxima", "minima", "ascendingRegions",
            "descendingRegions" };

    private MdeFeatureNodeManager featureTree;

    /**
     * Default constructor.
     */
    public SolvedGraph() {
        featureTree = new MdeFeatureNodeManager();
        featureTree.addNode("", "MDE");
        featureTree.setCurrent("MDE");
        featureTree.addNode("", "GraphData");
        featureTree.setCurrent("GraphData");

        // load default features
        int len = defaultFeatureNames.length;

        for (int i = 0; i < len; i++)
            featureTree.addKey(defaultFeatureNames[i]);
    } // end SolvedGraph

    /**
     * Puts a feature for the specified key and value.
     * 
     * @param k the key.
     * @param v the value.
     */
    public void putFeature(String k, Object v) {
        if ((v instanceof String) || (v instanceof MdeFeatureNode))
            featureTree.addValue(k, v);
        else if ((v instanceof IntervalXY) || (v instanceof PointXY) || (v instanceof PointRT))
            featureTree.addValue(k, v.toString());
        else if (v instanceof NumberModel)
            featureTree.addValue(k, ((NumberModel)v).getMFN());
        else
            featureTree.addValue(k, new MdeFeatureNode(v));
    } // end putFeature

    /**
     * Puts a feature for the specified node path, key, and value to the last
     * node.
     * 
     * @param path the path to the feature node.
     * @param key the key.
     * @param value the value.
     */
    public void putFeature(String path, String key, Object value) {
        putFeature(path, key, value, MdeFeatureNodeManager.ADD_LAST);
    } // end putFeature
    
    /**
     * Puts a feature for the specified node path, key, value and node flag.
     * 
     * @param path the path to the feature node.
     * @param k the key.
     * @param v the value.
     * @param whichNode which node to put the feature which is one of 
     * MdeFeatureNodeManager.ADD_LAST|ADD_ALL.
     */
    public void putFeature(String path, String k, Object v, int whichNode) {
        if ((v instanceof String) || (v instanceof MdeFeatureNode))
            featureTree.addValue(path, k, v, whichNode);
        else if ((v instanceof IntervalXY) || (v instanceof PointXY) || (v instanceof PointRT))
            featureTree.addValue(path, k, v.toString(), whichNode);
        else if (v instanceof NumberModel)
            featureTree.addValue(path, k, ((NumberModel)v).getMFN(), whichNode);
        else
            featureTree.addValue(path, k, new MdeFeatureNode(v), whichNode);
    } // end putFeature

    /**
     * Puts a new feature for the specified key and value.
     * 
     * @param k the key.
     * @param v the value.
     */
    public void putNewFeature(String k, Object v) {
        featureTree.addKey(k);
        if (v != null)
            putFeature(k, v);
    } // end putNewFeature

    /**
     * Puts the new features.
     * 
     * @param features the array of features.
     */
    public void putNewFeatures(String[] features) {
        int i, n = features.length;

        for (i = 0; i < n; i++)
            putNewFeature(features[i], null);
    } // end putNewFeatures
    
    /**
     * Puts a new feature for the specified node path, key, value, and flag for
     * a new node.
     * 
     * @param path path for the node.
     * @param k the key.
     * @param v the value.
     * @param newNode true to add a node for the given path, false to not.
     */
    public void putNewFeature(String path, String k, Object v, boolean newNode) {
        if (newNode)
            featureTree.addNode("", path);

        featureTree.addKey(path, k);

        if (v != null)
            putFeature(path, k, v);
    } // end putNewFeature

    /**
     * Adds to the feature.
     * 
     * @param k the key.
     * @param v the value.
     */
    public void addToFeature(String k, Object v) {
        putFeature(k, v);
    } // end addToFeature

    /**
     * Copies from the specified solved graph to this solved graph.
     * 
     * @param other the other solved graph to copy.
     */
    public void copyFrom(SolvedGraph other) {
        featureTree = other.featureTree;
    } // end copyFrom

    /**
     * Returns the compass direction based on the angle.
     * 
     * @param theta the angle.
     * @return the compase direction which is one of COMPASS_DIRECTIONS.
     */
    public static String getCompassDir(double theta) {
        double zeta = theta + 11.25;
        double turns = zeta / 360.0;
        double t = Math.floor(turns);
        double phi = 360.0 * (turns - t);
        int n = (int)Math.floor(phi / 22.5);
        
        System.out.println("theta = " + theta);
        System.out.println("zeta = " + zeta);
        System.out.println("turns = " + turns);
        System.out.println("t = " + t);
        System.out.println("phi = " + phi);
        System.out.println("n = " + n);

        return COMPASS_DIRECTIONS[n];
    } // end getCompassDir
    
    /**
     * Returns the general direction based on the angle.
     * 
     * 
     * 
     * @param int of value 0-4
     * @return the direction which is one of GENERAL_DIRECTIONS.
     */
    public static String getGeneralDir(int n) {
        return GENERAL_DIRECTIONS[n];
    } // end getCompassDir
    

    /**
     * Returns an XML represntation of the solved graph.
     * 
     * @return an XML represntation of the solved graph.
     */
    public String toString() {
        return getXMLString();
    } // end toString

    /**
     * Returns an XML represntation of the solved graph.
     * 
     * @return an XML represntation of the solved graph.
     */
    public String getXMLString() {
        MdeFeatureNode[] nodes = featureTree.getNodes("/MDE");
        int i, n = nodes.length;
        StringBuffer b = new StringBuffer();

        for (i = 0; i < n; i++)
            b.append(nodes[i].getXMLString());

        return b.toString();
    } // end getXMLString
    
    
   
//    private static void doPrint(String[] f) {
//        int i, n = f.length;
//
//        if (n > 0) {
//            for (i = 0; i < n; i++)
//                System.out.println(f[i]);
//        } // end if
//        else
//            System.out.println("[NOTHING TO PRINT]");
//    } // end doPrint
//
//    // Main routine for test only
//    public static void main(String[] args) {
//        try {
//            double d = new Double(args[0]).doubleValue();
//            System.out.println(SolvedGraph.getCompassDir(d));
//        } // end try
//        catch (NumberFormatException nfe) {
//        }
//    } // end main
} // end class SolvedGraph
