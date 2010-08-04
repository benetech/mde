package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.solver.features.GraphProperty;

public interface SlopeGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH+"slope/";
	public static String KEY = "approximateDecimalValue";
	
	public double getSlope();
}
