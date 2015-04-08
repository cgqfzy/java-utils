package com.cgq.io;

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
	 
	 
}
