package com.cgq.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * read numbers and text from files and URLs
 * 
 * @author cgqfzy
 * 
 */
public class In {

	private Scanner scanner;
	private static final String CHART_SET = "utf-8";
	private static final Locale LOCALE = Locale.CHINESE;
	// the defalut token seperator
	private static final Pattern WHITESAPCE_PATTERN = Pattern
			.compile("\\p{javaWhitespace}+");
	// make whitespace characters significant
	private static final Pattern EMPTY_PATTERN = Pattern.compile("");
	// used to read the entire input source
	private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A+");

	public In() {
		scanner = new Scanner(new BufferedInputStream(System.in), CHART_SET);
		scanner.useLocale(LOCALE);
	}

	public In(File file) {
		try {
			scanner = new Scanner(file, CHART_SET);
			scanner.useLocale(LOCALE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open " + file);
		}
	}

	public In(Scanner scanner) {
		this.scanner = scanner;
	}

	public In(Socket scoket) {
		try {
			InputStream is = scoket.getInputStream();
			scanner = new Scanner(new BufferedInputStream(is), CHART_SET);
			scanner.useLocale(LOCALE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open " + scoket);
		}
	}

	public In(String str) {
		try {
			File file = new File(str);
			if (file.exists()) {
				scanner = new Scanner(file, CHART_SET);
				scanner.useLocale(LOCALE);
				return;
			}
			URL url = getClass().getResource(str);
			if (url == null) {
				url = new URL(str);
			}
			URLConnection site = url.openConnection();
			InputStream is = site.getInputStream();
			scanner = new Scanner(new BufferedInputStream(is), CHART_SET);
			scanner.useLocale(LOCALE);
		} catch (IOException e) {
			System.err.println("Could not open " + str);
		}
	}

	public In(URL url) {
		try {
			URLConnection site = url.openConnection();
			InputStream is = site.getInputStream();
			scanner = new Scanner(new BufferedInputStream(is), CHART_SET);
			scanner.useLocale(LOCALE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not open " + url);
		}
	}

	public void close() {
		scanner.close();
	}

	public boolean exists() {
		return scanner != null;
	}

	public boolean hasNextLine() {

		return scanner.hasNextLine();
	}

	public boolean hasNextChar() {
		scanner.useDelimiter(EMPTY_PATTERN);
		boolean result = scanner.hasNext();
		scanner.useDelimiter(WHITESAPCE_PATTERN);
		return result;
	}

	public boolean isEmpty() {
		return !scanner.hasNext();
	}

	public String readAll() {
		if (!scanner.hasNext()) {
			return "";
		}
		String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
		scanner.useDelimiter(WHITESAPCE_PATTERN);
		return result;
	}

	public double[] readAllDoubles() {

		String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
	}

	public int[] readAllInts() {
		String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
	}

	public String[] readAllLines() {
		ArrayList lines = new ArrayList();
		while (hasNextLine()) {
			lines.add(readLine());
		}
		return (String[]) lines.toArray(new String[0]);
	}

	public String[] readAllStrings() {
		String[] tokens = WHITESAPCE_PATTERN.split(readAll());
		if (tokens.length == 0 || tokens[0].length() > 0)
			return tokens;
		String[] decapitokens = new String[tokens.length - 1];
		for (int i = 0; i < tokens.length - 1; i++)
			decapitokens[i] = tokens[i + 1];
		return decapitokens;
	}

	public boolean readBoolean() {
		String s = scanner.next();
		if ("false".equals(s)) {
			return false;
		}
		if ("true".equals(s)) {
			return true;
		}
		if ("0".equals(s)) {
			return false;
		}
		if ("1".equals(s)) {
			return true;
		}
		return false;
	}

	public byte readByte() {
		return scanner.nextByte();
	}

	public char readChar() {
		scanner.useDelimiter(EMPTY_PATTERN);
		String ch = scanner.next();
		if (ch.length() != 1) {
			System.err.println("In readChar error.");
		}
		scanner.useDelimiter(WHITESAPCE_PATTERN);
		return ch.charAt(0);
	}

	public double readDouble() {
		return scanner.nextDouble();
	}

	public int readInt() {
		return scanner.nextInt();
	}

	public float readFloat() {
		return scanner.nextFloat();
	}

	public static double[] readDoubles() {
		return null;
	}

	public static double[] readDoubles(String fileName) {
		return new In(fileName).readAllDoubles();
	}

	public static int[] readInts() {
		return new In().readAllInts();
	}

	public static int[] readInts(String fileName) {
		return new In(fileName).readAllInts();
	}

	public long readLong() {
		return scanner.nextLong();
	}

	public String readString() {
		return scanner.next();
	}

	public String readLine() {
		String line = null;
		try {
			line = scanner.nextLine();
		} catch (Exception e) {
			// TODO: handle exception
			line = null;
		}
		return line;
	}

	public short readShort() {
		return scanner.nextShort();
	}

	public static String[] readStrings() {
		return new In().readAllStrings();
	}

	public String[] readStrings(String fileName) {
		return new In(fileName).readAllStrings();
	}
	
	public static void main(String[] args) {
		In in;
        String urlName = "http://introcs.cs.princeton.edu/stdlib/InTest.txt";

        // read from a URL
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            System.out.println(in.readAll());
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();

        // read one line at a time from URL
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();

        // read one string at a time from URL
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readString();
                System.out.println(s);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();


        // read one line at a time from file in current directory
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("./data/in1.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();


        // read one line at a time from file using relative path
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("../data/in2.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();

        // read one char at a time
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("in1.txt");
            while (!in.isEmpty()) {
                char c = in.readChar();
                System.out.print(c);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();
        System.out.println();

        // read one line at a time from absolute OS X / Linux path
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        in = new In("/n/fs/introcs/www/java/stdlib/in1.txt");
        try {
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();


        // read one line at a time from absolute Windows path
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("G:\\Github\\java-utils\\IntroduceToJava\\in1.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println();

	}
}
