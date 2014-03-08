package gov.nasa.ial.mde.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import sun.misc.Regexp;
import sun.security.util.Length;

import gov.nasa.ial.mde.solver.MdeFeatureNode;
import gov.nasa.ial.mde.util.MathUtil;

public class Sampling {
	
	private PointXY first;
	private PointXY last;
	private PointXY[] sampling;
	private PointXY[] allPoints;
	private PointXY[] lb, rb;
    private MdeFeatureNode node;
    private double left, right, top, bottom;
	
	public Sampling(PointXY[] points, double leftBound, double rightBound, double bottomBound, double topBound, double difference) {
		left = leftBound;
		right = rightBound;
		top = topBound;
		bottom = bottomBound;
		this.allPoints = points;
		int len = points.length;
		int increment = (int) (difference * (len - 1) / Math.abs(rightBound - leftBound));
		ArrayList<PointXY> temp = new ArrayList<PointXY>();
		for (int i = 0; i < len; i += increment) {
			  if (isWithinBounds(points[i])) {
				  temp.add(points[i]);
			  }
		}
		this.sampling = temp.toArray(new PointXY[temp.size()]);
		if (!temp.isEmpty()) {
		this.first = temp.get(0);
		this.last = temp.get(temp.size()-1);
		}
	}
	
	/**
	 * Makes the branches for a parabola.
	 */
	public void getBranches(PointXY midpoint, String openingDirection) {
		if (!isWithinBounds(midpoint)) {
			return;
		}
		ArrayList<PointXY> lt = new ArrayList<PointXY>(allPoints.length);
		ArrayList <PointXY> rt = new ArrayList<PointXY>(allPoints.length);
			String value = (openingDirection == "up" || openingDirection == "down") ? "x" : "y";
				double comparison = midpoint.getValue(value);
				trimToFitBounds(lt, rt, midpoint, comparison, value, openingDirection);
				double increment = calculateIncrement(lt, rt, midpoint, value);
					lb = getLeftSample(lt, comparison, increment, value);
					rb = getRightSample(rt, comparison, increment, value);
					addBranchesToNode();
	}

	/**
     * Returns the MDE Feature Node for the sampling.
     * 
     * @return the MDE Feature Node for the sampling.
     */
    public MdeFeatureNode getMFN() {
        node = new MdeFeatureNode();
        node.addKey("first");
        node.addValue("first", first.toString());
            node.addKey("last");
            node.addValue("last", last.toString());
            node.addKey("sampling");
            node.addValue("sampling", getPointsXML());

        return node;
    } // end getMFN
    
    private void addBranchesToNode() {
        node.addKey("lb");
        node.addValue("lb", getLeftBranchXML());
        node.addKey("rb");
        node.addValue("rb", getRightBranchXML());
    }

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
            if (lb != null) {
                s.append("\n  <lb>" + getLeftBranchXML() + "</lb>");
                s.append("\n<rb>" + getRightBranchXML() + "\n</rb");
            }
        return s.toString();
    }
    
    private String getPointsXML() {
        StringBuffer s = new StringBuffer();
        for (PointXY p : sampling) {
        	s.append(p + ", ");
        }
        s.delete(s.length()-2, s.length());
        return s.toString();
    }

    private String getLeftBranchXML() {
        StringBuffer s = new StringBuffer();
        for (PointXY p : lb) {
        	s.append(p + ", ");
        }
        s.delete(s.length()-2, s.length());
        return s.toString();
    }

    private String getRightBranchXML() {
        StringBuffer s = new StringBuffer();
        for (PointXY p : rb) {
        	s.append(p + ", ");
        }
        s.delete(s.length()-2, s.length());
        return s.toString();
    }

    private boolean isWithinBounds(PointXY p) {
    	return p.x >= left && p.x <= right && p.y >= bottom && p.y <= top;
    }
    
    private void trimToFitBounds(ArrayList<PointXY> lt, ArrayList<PointXY> rt, PointXY midpoint, double comparison, String value, String openingDirection) {
		for (PointXY p : allPoints) {
			if (p.equals(midpoint) || !isWithinBounds(p)) {
				continue;
			}
			if (value == "x" && p.getValue(value) < comparison) {
				lt.add(0, p);
			} else if (value == "y" && p.getValue(value) < comparison) {
				if (openingDirection == "left") {
					rt.add(p);
				} else {
				rt.add(0, p);
				}
			} else if (value == "x" && p.getValue(value) > comparison) {
				rt.	add(p);
			} else if (value == "y" && p.getValue(value) > comparison) {
				if (openingDirection == "left") {
					lt.add(0, p);
				} else {
				lt.add(p);
			}
			}
		}
    }

private PointXY[] getLeftSample(ArrayList<PointXY> lt, double comparison, double increment, String value) {
	LinkedList<PointXY> lSample = new LinkedList<PointXY>();
	int i = 0;
	while (i < lt.size()) {
		comparison = value == "x" ? ((double) Math.round((comparison - increment) * 1000)) / 1000.0 : ((double) Math.round((comparison + increment) * 1000)) / 1000.0;
		while (i < lt.size() && (comparison < lt.get(i).getValue(value) && value == "x" || comparison > lt.get(i).getValue(value) && value == "y")) {
			i++;
		}
		if ( i == lt.size() && !lSample.contains(lt.get(lt.size()-1))) {
			lSample.add(lt.get(lt.size()-1));
			lSample.getLast().value = value;
			break;
		}
		if (lt.get(i).getValue(value) == comparison) {
			lSample.add(lt.get(i));
			lSample.getLast().value = value;
		}
			i++;
	}
	return lSample.toArray(new PointXY[lSample.size()]);
}

private PointXY[] getRightSample(ArrayList<PointXY> rt, double comparison, double increment, String value) {
	LinkedList<PointXY> rSample = new LinkedList<PointXY>();
int i = 0;
while (i < rt.size()) {
	comparison = value == "x" ? ((double) Math.round((comparison + increment) * 1000)) / 1000.0 : ((double) Math.round((comparison - increment) * 1000)) / 1000.0;
	while (i < rt.size() && (comparison > rt.get(i).getValue(value) && value == "x" || comparison < rt.get(i).getValue(value) && value == "y")) {
		i++;
	}
	if ( i == rt.size() && !rSample.contains(rt.get(rt.size()-1))) {
		rSample.add(rt.get(rt.size()-1));
		rSample.getLast().value = value;
		break;
	}
	if (rt.get(i).getValue(value) == comparison) {
		rSample.add(rt.get(i));
		rSample.getLast().value = value;
	}
		i++;
}
return rSample.toArray(new PointXY[rSample.size()]);
}

private double calculateIncrement(ArrayList<PointXY> lt, ArrayList<PointXY> rt, PointXY midpoint, String value) {
double lDiff = Math.abs(lt.get(lt.size()-1).getValue(value) - midpoint.getValue(value));
double rDiff = Math.abs(rt.get(rt.size()-1).getValue(value) - midpoint.getValue(value));
double increment;
increment = (lDiff >= rDiff) ? lDiff / 4.0 : rDiff / 4.0;
if (increment > 0.25) {
if (increment % 0.25 != 0) {
	increment = ((int)(increment * 4)) / 4.0;
} else {
	increment = (lDiff >= rDiff) ? lDiff / 5.0 : rDiff / 5.0;
}
}
return increment;
}

}
