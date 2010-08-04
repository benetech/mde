package gov.nasa.ial.mde.solver.graphinterfaces.eachproperty;

import gov.nasa.ial.mde.solver.features.GraphProperty;

public interface DomainGraph extends GraphProperty {
	public static String PATH = GraphProperty.GRAPH_DATA_PATH;
	public static String KEY = "domain";
	
	public String getDomain();
}
