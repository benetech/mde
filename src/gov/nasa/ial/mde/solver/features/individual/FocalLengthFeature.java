package gov.nasa.ial.mde.solver.features.individual;

import gov.nasa.ial.mde.math.NumberModel;
import gov.nasa.ial.mde.solver.features.GraphFeature;

public interface FocalLengthFeature extends GraphFeature{
	public static String PATH = GraphFeature.GRAPH_DATA_PATH;
	public static String KEY = "focalLength";
	
	public Object getFocalLength();
}

