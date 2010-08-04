package gov.nasa.ial.mde.solver.graphinterfaces;

public interface YInterceptGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "yIntercepts";
	
	public Double[] getYIntercepts();
}
