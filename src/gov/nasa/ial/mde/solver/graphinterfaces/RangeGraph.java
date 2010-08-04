package gov.nasa.ial.mde.solver.graphinterfaces;

public interface RangeGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "range";
	
	public String getRange();
}
