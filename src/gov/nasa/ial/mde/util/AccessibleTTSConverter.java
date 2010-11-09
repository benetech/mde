package gov.nasa.ial.mde.util;

import java.util.regex.Pattern;

public class AccessibleTTSConverter {

	/**
	 * @param args
	 */
	
	
	public static String convertCoefficients(String string){
		return string;
	}
	
	public static String convertDomainAndRange(String string){
		//TODO: get domain and range to the format "the domain|range is x|y between blank and blanky
		
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
		string = string.replaceAll("\\+", " plus ");
		return string;
	}
	
	public static String replacePlusWithPositive(String string){
		string = string.replaceAll("\\+", " positive ");
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
		string = string.replaceAll("\\*", " times ");
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
	
	public static String replaceExponent(String string){
		string = string.replaceAll("\\^", "");
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
		string = string.replaceAll("=", "equals");
		return string;	
	}
	
	
	public static void main(String[] args) {
		//Pattern p = new Pattern();
	}

}
