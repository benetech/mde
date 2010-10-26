package gov.nasa.ial.mde.util;

public class AccessibleTTSConverter {

	/**
	 * @param args
	 */
	
	
	public static String handleCoefficients(String string){
		return string;
	}
	
	public static String removeBrackets(String string){
		string = string.replaceAll("{", "\n");
		string = string.replaceAll("}", "\n");
		return string;
	}
	
	public static String replacePlus(String string){
		return null;
	}
	
	public static String replacePlusWithPositive(String string){
		return null;
	}
	
	public static String replaceMinusWithNegative(String string){
		return null;
	}
	
	public static String replaceMinus(String string){
		return null;
	}
	
	public static String replaceMultiply(String string){
		return null;
	}
	
	public static String replaceDivided(String string){
		return null;
	}
	
	public static String replaceDividedWithOver(String string){
		return null;
	}
	
	public static String replaceGreater(String string){
		return null;	
	}
	
	public static String replaceLesser(String string){
		return null;	
	}
	
	public static String replaceEquals(String string){
		return null;	
	}
	
	
	public static void main(String[] args) {
		String test = "{x such that -infinity < x < infinity}";
		test = removeBrackets(test);
		
		System.out.println(test);
		

	}

}
