package gov.nasa.ial.mde.solver.graphinterfaces;

public interface XInterceptGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "xIntercepts";
	
	public Double[] getXIntercepts();
}