package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.math.MultiPointXY;
import gov.nasa.ial.mde.solver.features.GraphFeature;

public interface MultipointsFeature extends GraphFeature {
	public static String PATH = GraphFeature.GRAPH_DATA_PATH;
	public static String KEY = "multipoints";
	
	public MultiPointXY[] getMultipoints();

}
