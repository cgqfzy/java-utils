package com.cgq.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Locale;

/**
 * write numbers and text to files
 * @author cgqfzy
 *
 */
public class Out {

	private static final String CHARSET_NAME = "utf-8";
	private static final Locale LOCALE = Locale.CHINESE;
	
	private PrintWriter out;
	
	public Out(OutputStream os) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(os, CHARSET_NAME);
			out = new PrintWriter(osw);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Out() {
		// TODO Auto-generated constructor stub
		this(System.out);
	}
	
	public Out(Socket socket){
		try {
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os,CHARSET_NAME);
			out = new PrintWriter(osw);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Out(String str){
		try {
			OutputStream os = new FileOutputStream(str);
			OutputStreamWriter osw = new OutputStreamWriter(os,CHARSET_NAME);
			out = new PrintWriter(osw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		out.close();
	}
	
	/**
     * Terminate the line.
     */
    public void println() {
        out.println();
    }

   /**
     * Print an object and then terminate the line.
     */
    public void println(Object x) {
        out.println(x);
    }

   /**
     * Print a boolean and then terminate the line.
     */
    public void println(boolean x) {
        out.println(x);
    }

   /**
     * Print a char and then terminate the line.
     */
    public void println(char x) {
        out.println(x);
    }

   /**
     * Print an double and then terminate the line.
     */
    public void println(double x) {
        out.println(x);
    }

   /**
     * Print a float and then terminate the line.
     */
    public void println(float x) {
        out.println(x);
    }

   /**
     * Print an int and then terminate the line.
     */
    public void println(int x) {
        out.println(x);
    }

   /**
     * Print a long and then terminate the line.
     */
    public void println(long x) {
        out.println(x);
    }

   /**
     * Print a byte and then terminate the line.
     */
    public void println(byte x) {
        out.println(x);
    }



   /**
     * Flush the output stream.
     */
    public void print() {
        out.flush();
    }

   /**
     * Print an object and then flush the output stream.
     */
    public void print(Object x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print an boolean and then flush the output stream.
     */
    public void print(boolean x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print an char and then flush the output stream.
     */
    public void print(char x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print an double and then flush the output stream.
     */
    public void print(double x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print a float and then flush the output stream.
     */
    public void print(float x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print an int and then flush the output stream.
     */
    public void print(int x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print a long and then flush the output stream.
     */
    public void print(long x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print a byte and then flush the output stream.
     */
    public void print(byte x) {
        out.print(x);
        out.flush();
    }

   /**
     * Print a formatted string using the specified format string and arguments,
     * and then flush the output stream.
     */
    public void printf(String format, Object[] args) {
        out.printf(LOCALE, format, args);
        out.flush();
    }

   /**
     * Print a formatted string using the specified locale, format string and arguments,
     * and then flush the output stream.
     */
    public void printf(Locale locale, String format, Object[] args) {
        out.printf(locale, format, args);
        out.flush();
    }

    public static void main(String[] args) {
    	Out out;

        // write to stdout
        out = new Out();
        out.println("Test 1");
        out.close();

        // write to a file
        out = new Out("./data/test.txt");
        out.println("Test 2");
        out.close();
	}
	
	
}
