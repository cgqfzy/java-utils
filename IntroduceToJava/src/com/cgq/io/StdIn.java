package com.cgq.io;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.cgq.exception.StdInException;

public final class StdIn {

	private StdIn(){
		
	}
	
	private static Scanner scanner;
	private static final String CHARSET_NAME = "utf-8";
	private static final Locale LOCAL = Locale.CHINESE;
	
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
	private static final Pattern EMPTY_PATTERN = Pattern.compile("");
	
	private static final Pattern EVERY_PATTERN = Pattern.compile("\\A");
	
	public static boolean isEmpty(){
		return !scanner.hasNext();
	}
	
	
	public static boolean hasNextLine(){
		return scanner.hasNextLine();
	}
	
	
	public static boolean hasNextChar(){
		scanner.useDelimiter(EMPTY_PATTERN);
		boolean result = scanner.hasNext();
		scanner.useDelimiter(WHITESPACE_PATTERN);
		return result;
	}
	
	
	public static String readLine(){
		String line = null;
		try{
		line = scanner.nextLine();
		} catch(Exception e){
			line = null;
		}
		return line;
	}
	
	public static char readChar() throws StdInException{
		scanner.useDelimiter(EMPTY_PATTERN);
		String ch = scanner.next();
		if (ch.length() != 1){
			throw new StdInException("readChar error");
		}
		scanner.useDelimiter(WHITESPACE_PATTERN);
		return ch.charAt(0);
	}
	
	
	public static String readAll(){
		if (!scanner.hasNextLine()){
			return "";
		}
		String result = scanner.useDelimiter(EVERY_PATTERN).next();
		scanner.useDelimiter(WHITESPACE_PATTERN);
		return result;
	}
	
	public static String readString() {
        return scanner.next();
    }
	
	 public static int readInt() {
	        return scanner.nextInt();
	    }
	 
	 public static double readDouble() {
	        return scanner.nextDouble();
	    }
	 
	 public static float readFloat() {
	        return scanner.nextFloat();
	    }
	 
	 public static long readLong() {
	        return scanner.nextLong();
	    }
	 public static byte readByte() {
	        return scanner.nextByte();
	    }
	 
	 public static boolean readBoolean(){
		 String s = readString();
		 if ("true".equalsIgnoreCase(s)){
			 return true;
		 }
		 if ("false".equalsIgnoreCase(s)){
			 return false;
		 }
		 if ("1".equalsIgnoreCase(s)){
			 return true;
		 }
		 if ("0".equalsIgnoreCase(s)){
			 return false;
		 }
		 return false;
	 }
	 
	 public static String[] readAllStrings(){
		 String[] tokens = WHITESPACE_PATTERN.split(readAll());
		 if (tokens.length == 0 || tokens[0].length() > 0){
			 return tokens;
		 }
		 String[] decaptokens = new String[tokens.length - 1];
		 for (int i = 0; i < tokens.length - 1; i++) {
			decaptokens[i] = tokens[i+1];
		}
		 return decaptokens;
	 }
	 
	 public static String[] readAllLines(){
		 ArrayList lines = new ArrayList();
		 while (hasNextLine()) {
			lines.add(readLine());
		}
		 return (String[]) lines.toArray(new String[0]);
	 }
	 
	 
	 public static int[] readAllInts(){
		 String[] fields = readAllStrings();
		 int[] vals = new int[fields.length];
		 for (int i = 0; i < fields.length; i++) {
			vals[i] = Integer.parseInt(fields[i]);
		 }
		 return vals;
	 }
	
	 public static double[] readAllDoubles(){
		 String[] fields = readAllStrings();
		 double[] vals = new double[fields.length];
		 for (int i = 0; i < fields.length; i++) {
			vals[i] = Double.parseDouble(fields[i]);
		 }
		 return vals;
	 }
	 
	 static{
		 resync();
	 }
	 
	 private static void resync(){
		 setScanner(new Scanner(new BufferedInputStream(System.in),CHARSET_NAME));
	 }

	private static void setScanner(Scanner scanner) {
		// TODO Auto-generated method stub
		StdIn.scanner = scanner;
		StdIn.scanner.useLocale(LOCAL);
	}
	
	public static int[] readInts(){
		return readAllInts();
	}
	
	public static double[] readDoubles(){
		return readAllDoubles();
	}
	
	public static String[] readStrings(){
		return readAllStrings();
	}
	
	public static void main(String[] args) {
		System.out.println("Type a string: ");
		String s = StdIn.readString();
		System.out.println("Your string is: " + s);
		System.out.println();
		
		System.out.println("Type an int: ");
        int a = StdIn.readInt();
        System.out.println("Your int was: " + a);
        System.out.println();

        System.out.println("Type a boolean: ");
        boolean b = StdIn.readBoolean();
        System.out.println("Your boolean was: " + b);
        System.out.println();

        System.out.println("Type a double: ");
        double c = StdIn.readDouble();
        System.out.println("Your double was: " + c);
        System.out.println();
	}
}
