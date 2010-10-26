package gov.nasa.ial.mde.util;

public class AccessibleTTSConverter {

	/**
	 * @param args
	 */
	
	
	public static String convertCoefficients(String string){
		return string;
	}
	
	public static String convertDomainAndRange(String string){
		string = replaceBrackets(string);
		string = replaceLesser(string);
		string = replaceGreater(string);
		string = replaceEquals(string);
		string = replaceMinusWithNegative(string);
		return string;
	}
	
	public static String getSets(String string){
		
		
		
		
		string = string.replaceAll("(\\{[^\\}\n]*\\})", "blash");
		
		
		return string;
	}
	
	public static String replaceBrackets(String string){
		string = string.replaceAll("[{}]", "\n");
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
		string = string.replaceAll("-", "negative ");
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
		string = string.replaceAll(">=", "is greater than or equal to");
		string = string.replaceAll(">", "is greater than");
		return string;	
	}
	
	public static String replaceLesser(String string){
		string = string.replaceAll("<=", "is less than or equal to");
		string = string.replaceAll("<", "is less than");
		return string;	
	}
	
	public static String replaceEquals(String string){
		string = string.replaceAll("=", "equals");
		return string;	
	}
	
	
	public static void main(String[] args) {
		String test = "adfadfa {x such that -infinity < x < infinity} {x such that -infinity <= x >= infinity} {x such that -infinity = x < infinity}";
		test = getSets(test);
		
		System.out.println(test);
		

	}

}
