package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.solver.features.GraphProperty;

public interface MinimaGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "Minima";
	
	public Double[][] getMinima();
	public boolean hasMinima();
	public boolean canCalculateMinima();
}
