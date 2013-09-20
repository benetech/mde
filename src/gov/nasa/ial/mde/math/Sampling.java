package gov.nasa.ial.mde.math;

import java.util.ArrayList;
import java.util.Arrays;

import gov.nasa.ial.mde.solver.MdeFeatureNode;

public class Sampling {
	
	private PointXY first;
	private PointXY last;
	private PointXY[] points;
	
	public Sampling(PointXY[] points, double leftBound, double rightBound, double bottomBound, double topBound, double difference) {
		int len = points.length;
		int increment = (int) (difference * (len - 1) / Math.abs(rightBound - leftBound));
		ArrayList<PointXY> temp = new ArrayList<PointXY>();
		for (int i = 0; i < len; i += increment) {
			  if (points[i].x >= leftBound && points[i].x <= rightBound && points[i].y >= bottomBound && points[i].y <= topBound) {
				  temp.add(points[i]);
			  }
		}
		this.points = temp.toArray(new PointXY[temp.size()]);
		if (!temp.isEmpty()) {
		this.first = temp.get(0);
		this.last = temp.get(temp.size()-1);
		}
	}

	/**
     * Returns the MDE Feature Node for the sampling.
     * 
     * @return the MDE Feature Node for the sampling.
     */
    public MdeFeatureNode getMFN() {
        MdeFeatureNode r = new MdeFeatureNode();
        r.addKey("first");
        r.addValue("first", first.toString());
            r.addKey("last");
            r.addValue("last", last.toString());
            r.addKey("sampling");
            r.addValue("sampling", getPointsXML());

        return r;
    } // end getMFN

    /**
     * Returns the XML representation of the sampling.
     * 
     * @return the XML representation of the sampling.
     */
    public String getXML() {
        StringBuffer s = new StringBuffer("");
        s.append("\n  <first>" + first.getXML() + "</first>");
            s.append("\n  <last>" + last.getXML() + "</last>");
            s.append("\n<sampling>" + getPointsXML() + "\n</sampling");
        return s.toString();
    }
    
    private String getPointsXML() {
        StringBuffer s = new StringBuffer();
        for (PointXY p : points) {
        	s.append(p + ", ");
        }
        s.delete(s.length()-2, s.length());
        return s.toString();
    }

}
