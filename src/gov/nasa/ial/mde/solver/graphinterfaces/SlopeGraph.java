package gov.nasa.ial.mde.solver.graphinterfaces;

public interface SlopeGraph {
	public static String SLOPE_PATH = "/MDE/GraphData/slope/";
	public static String SLOPE_KEY = "approximateDecimalValue";
	
	public double getSlope();
}
