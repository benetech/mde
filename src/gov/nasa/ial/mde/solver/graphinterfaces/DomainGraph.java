package gov.nasa.ial.mde.solver.graphinterfaces;

public interface DomainGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "domain";
	
	public String getDomain();
}