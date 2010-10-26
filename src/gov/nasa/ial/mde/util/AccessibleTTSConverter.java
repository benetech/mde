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
		string = string.replaceAll("+", " plus ");
		return string;
	}
	
	public static String replacePlusWithPositive(String string){
		string = string.replaceAll("+", " positive ");
		return string;
	}
	
	public static String replaceMinus(String string){
		string = string.replaceAll("-", " minus ");
		return string;
	}
	
	public static String replaceMinusWithNegative(String string){
		string = string.replaceAll("-", " negative ");
		return string;
	}
	
	public static String replaceMultiply(String string){
		string = string.replaceAll("*", " times ");
		return string;
	}
	
	public static String replaceDivided(String string){
		string = string.replaceAll("/", " divided by ");
		return string;
	}
	
	public static String replaceDividedWithOver(String string){
		string = string.replaceAll("/", " over ");
		return string;
	}
	
	public static String replaceGreater(String string){
		string = string.replaceAll(">=", "greater than or equal to");
		string = string.replaceAll(">", "greater than");
		return string;	
	}
	
	public static String replaceLesser(String string){
		string = string.replaceAll("<=", "less than or equal to");
		string = string.replaceAll("<", "less than");
		return string;	
	}
	
	public static String replaceEquals(String string){
		string.replaceAll("=", " equals ");
		return string;	
	}
	
	
	public static void main(String[] args) {
		String test = "{x such that -infinity < x < infinity}";
		test = removeBrackets(test);
		
		System.out.println(test);
		

	}

}
