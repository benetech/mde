package gov.nasa.ial.mde.solver.graphinterfaces.eachproperty;

import gov.nasa.ial.mde.solver.features.GraphProperty;

public interface YInterceptGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "yIntercepts";
	
	public Double[] getYIntercepts();
}
