package com.cgq.io;

import java.io.BufferedOutputStream;

/**
 * @comment write bits to standard output
 * @author cgqfzy
 * @version 1.0
 * @date Apr 9, 2015 5:17:56 PM
 */
public final class BinaryStdOut {

	private static BufferedOutputStream out = new BufferedOutputStream(System.out);
	//8-bit buffer of bits to write out
	private static int buffer;
	// number of bits remaining in buffer
	private static int N;
	private BinaryStdOut(){
		
	}
	private static void writeBit(boolean bit){
		buffer <<= 1;
		if (bit) {
			buffer |= 1;
		}
		N ++;
		if (N == 8) {
			clearBuffer();
		}
	}
	/**
	 * Write the 8-bit to standard output
	 * @param x
	 */
	public static void writeByte(int x){
		if (x <0 || x >= 256){
			throw new IllegalArgumentException("BinaryStdOut write byte x = " + x + "  is not illegal");
		}
		if (N == 0){
			try {
				out.write(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ;
		}
		for (int i = 0; i < 8; i++) {
			boolean bit = (( x >>> (8 - i - 1)) & 1) == 1;
			writeBit(bit);
		}
	}
	
	public static void flush(){
		clearBuffer();
		try {
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//write out any remaining bits in buffer to standard output, padding with 0s
	private static void clearBuffer() {
		if (N == 0){
			return ;
		}
		if (N > 0){
			buffer <<= (8-N);
		}
		try {
			out.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		N = 0;
		buffer = 0;
	}
	public static void write(boolean x){
		writeBit(x);
	}
	/**
	 * Write the 8-bit byte to standard output
	 * @param x
	 */
	public static void write(byte x){
		writeByte(x);
	}
	/**
	 * Write the 32-bit int to standard output
	 * @param x
	 */
	public static void write(int x){
		writeByte((x >>> 24) & 0xff );
		writeByte((x >>> 16) & 0xff );
		writeByte((x >>> 8) & 0xff );
		writeByte((x >>> 0) & 0xff );
	}
	/**
	 * Write the 64-bit long to standard output
	 * @param x
	 */
	public static void write(long x){
		writeByte((int)(x >>> 56) & 0xff );
		writeByte((int)(x >>> 48) & 0xff );
		writeByte((int)(x >>> 40) & 0xff );
		writeByte((int)(x >>> 32) & 0xff );
		writeByte((int)(x >>> 24) & 0xff );
		writeByte((int)(x >>> 16) & 0xff );
		writeByte((int)(x >>> 8) & 0xff );
		writeByte((int)(x >>> 0) & 0xff );
	}
	/**
	 * Write the r-bit int to standard output
	 * @param x the int to write
	 * @param r the number of relevant bits in the char
	 */
	public static void write(int x,int r){
		if (r == 32){
			write(x);
			return ;
		}
		if ( r < 1 || r > 32){
			throw new IllegalArgumentException("Illegal value for r = " + r);
		}
		if (x < 0 || x >= (1 << r)){
			throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
		}
		for (int i = 0; i < r; i++) {
			boolean bit = ((x >>> (r-i-1)) & 1) == 1;
			writeBit(bit);
		}
	}
	
	public static void writeDouble(double x){
		write(Double.doubleToLongBits(x));
	}
	
	public static void writeFloat(float x){
		write(Float.floatToIntBits(x));
	}
	
	public static void write(char x){
		if (x < 0 || x >= 256){
			throw new IllegalArgumentException("Illegal 6-bit char = " +x );
		}
		writeByte(x);
	}
	
	public static void write(char x, int r){
		if (r == 8){
			write(x);
			return;
		}
		if ( r < 1 || r > 16){
			throw new IllegalArgumentException("Illegal value for r = " + r);
		}
		if (x < 0 || x >= (1 << r)){
			throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
		}
		for (int i = 0; i < r; i++) {
			boolean bit = ((x >>> (r-i-1)) & 1) == 1;
			writeBit(bit);
		}
	}
	
	
	public static void write(String str){
		for (int i = 0; i < str.length(); i++) {
			write(str.charAt(i));
		}
	}
	/**
	 * Write the String of r-bit characters to standard output.
	 * @param str
	 * @param r
	 */
	public static void write(String str,int r){
		for (int i = 0; i < str.length(); i++) {
			write(str.charAt(i),r);
		}
	}
	
	public static void main(String[] args) {
		int t = 10;
		for (int i = 0; i < t; i++) {
			BinaryStdOut.write(i);
		}
		BinaryStdOut.write("Hello,World!");
		BinaryStdOut.flush();
	}
	
}
