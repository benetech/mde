package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.solver.features.GraphProperty;

public interface MaximaGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "Maxima";
	
	public Double[][] getMaxima();
	public boolean hasMaxima();
	public boolean canCalculateMaxima();
}
