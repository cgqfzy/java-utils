package com.cgq.io;

import java.io.BufferedInputStream;
import java.io.IOException;

import sun.net.www.content.audio.x_aiff;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Zephyr;

/**
 * @comment read bits from standard input
 * @author cgqfzy
 * @version 1.0
 * @date Apr 9, 2015 4:42:15 PM
 */

public final class BinaryStdIn {

	private static BufferedInputStream in = new BufferedInputStream(System.in);
	private static final int EOF = -1;
	private static int buffer;
	private static int N;

	private BinaryStdIn() {
		// TODO Auto-generated constructor stub
	}

	static {
		fillBuffer();
	}

	private static void fillBuffer() {
		try {
			buffer = in.read();
			N = 8;
		} catch (Exception e) {
			System.err.println("EOF");
			buffer = EOF;
			N = -1;
		}
	}

	public static void close() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not close BinaryStdIn");
		}
	}

	public static boolean isEmpty() {
		return buffer == EOF;
	}
	/**
	 * Read the next bit of data from standard input and return as a boolean
	 * @return
	 */
	public static boolean readBoolean() {
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		N--;

		boolean bit = ((buffer >> N) & 1) == 1;
		if (N == 0) {
			fillBuffer();
		}
		return bit;
	}
	/**
	 * Read the next 8 bits from standard input and return as an 8-bit char.
     * Note that char is a 16-bit type;
     * to read the next 16 bits as a char, use readChar(16)
	 * @return
	 */
	public static char readChar() {
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		if (N == 8){
			int x = buffer;
			fillBuffer();
			return (char)(x & 0xff);
		}
		int x = buffer;
		x <<= (8-N);
		int oldN = N;
		fillBuffer();
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		N = oldN;
		x |= (buffer >>> N);
		return (char)(x & 0xff);
	}
	
	/**
	 * Read the next r bits from standard input and return as an r-bit character.
     * @param r number of bits to read.
	 * @return
	 */
	public static char readChar(int r){
		if (r < 1 || r > 16){
			throw new IllegalArgumentException("Illegal value of r = " + r);
		}
		if (r == 8){
			return readChar();
		}
		char x = 0;
		for (int i = 0; i < r; i++) {
			x <<= 1;
			boolean bit = readBoolean();
			if (bit){
				x |= 1;
			}
		}
		return x;
	}
	
	/**
	 * Read the remaining bytes of data from standard input and return as a string. 
	 * @return
	 */
	public static String readString(){
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		StringBuilder sb = new StringBuilder();
		while (!isEmpty()) {
			char c = readChar();
			sb.append(c);
		}
		return sb.toString();
	}
	/**
	 * Read the next 16 bits from standard input and return as a 16-bit short.
	 * @return
	 */
	public static short readShort(){
		short x = 0;
		for (int i = 0; i < 2; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	/**
	 * Read the next 32 bits from standard input and return as a 32-bit int
	 * @return
	 */
	public static int readInt(){
		int x = 0;
		for (int i = 0; i < 4; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	/**
	 * Read the next r bits from standard input and return as r-bit int
	 * @param r number of bits to read
	 * @return
	 */
	public static int readInt(int r){
		if (r < 1 || r > 16){
			throw new RuntimeException("Illegal value of r = " + r);
		}
		if (r== 32){
			return readInt();
		}
		int x = 0;
		for (int i = 0; i < r; i++) {
			x <<= 1;
			boolean bit = readBoolean();
			if(bit){
				x |= 1;
			}
		}
		return x;
	}
	/**
	 * Read the next 64 bits from standard input and return as a 64-bit long
	 * @return
	 */
	public static long readLong(){
		long x = 0;
		for (int i = 0; i < 8; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	/**
	 * Read the next 8 bits from standard input and return as a 32-bit float
	 * @return
	 */
	public static float readFloat(){
		return Float.intBitsToFloat(readInt());
	}
	/**
	 * Read the next 8 bits from standard input and return as 8-bit byte
	 * @return
	 */
	public static byte readByte(){
		char c = readChar();
		byte x = (byte) (c & 0xff);
		return x;
	}
	
	public static void main(String[] args) {
		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			System.out.println(c);
			BinaryStdOut.write(c);
		}
		BinaryStdOut.flush();
	}
}
