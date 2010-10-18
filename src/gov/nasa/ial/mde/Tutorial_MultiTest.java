package gov.nasa.ial.mde;

import gov.nasa.ial.mde.describer.Describer;
import gov.nasa.ial.mde.properties.MdeSettings;
import gov.nasa.ial.mde.solver.Solver;

public class Tutorial_MultiTest {
	
	private static String[] tests = {"x=y", "y=x^2+x", "y=sin(x)", "x^4-5=y","y=1/x"};
	
	
	public static void main(String[] args) {
        // MDE Init:
        MdeSettings currentSettings = new MdeSettings("myAppsMdeProperties");
        Solver solver = new Solver();
        Describer describer = new Describer(solver, currentSettings);
        describer.setOutputFormat(Describer.TEXT_OUTPUT);
        
        for(int i=0; i<tests.length ;i++)
        {
        	solver.add(tests[i]);
        }
        
        solver.solve();
        if (solver.anyDescribable()) {
            String description = describer.getDescriptions("standards");
            System.out.println("Description: " + description);
        }
	}

	
	
}
