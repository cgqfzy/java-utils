package com.cgq.io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.org.apache.regexp.internal.recompile;

/**
 * @comment write bits to files
 * @author cgqfzy
 * @version 1.0
 * @date Apr 9, 2015 8:48:40 PM
 */
public class BinaryOut {

	private BufferedOutputStream out;
	// 8-bit buffer of bits to write out
	private int buffer;
	// number if bits remaining in buffer
	private int N;

	public BinaryOut(OutputStream os) {
		out = new BufferedOutputStream(os);
	}

	public BinaryOut(String str) {
		try {
			out = new BufferedOutputStream(new FileOutputStream(str));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Write special bit to the binary output stream
	 * 
	 * @param bit
	 */
	private void writeBit(boolean bit) {
		buffer <<= 1;
		if (bit) {
			buffer |= 1;
		}
		N++;
		if (N == 8) {
			clearBuffer();
		}
	}

	/**
	 * Write the 8-bit byte to the binary output stream
	 * 
	 * @param x
	 */
	private void writeByte(int x) {
		if (x <= 0 || x > 256) {
			throw new RuntimeException("Illegal value of x = " + x);
		}
		if (N == 0) {
			try {
				out.write(x);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		for (int i = 0; i < 8; i++) {
			boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
			writeBit(bit);
		}
	}

	/**
	 * Write out any remaining bits in buffer to the binary output stream ,
	 * padding with 0s
	 */
	private void clearBuffer() {
		if (N == 0) {
			return;
		}
		if (N > 0) {
			buffer <<= (8 - N);
		}
		try {
			out.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		N = 0;
		buffer = 0;
	}

	/**
	 * Flush the binary output stream, padding 0s if number of bits written so
	 * far
	 */
	public void flush() {
		clearBuffer();
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Close and flush the binary output stream, Once it is closed you can no
	 * longer write bits
	 */
	public void close() {
		flush();
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Write the specified bit to the binary output stream
	 * 
	 * @param x
	 */
	public void write(boolean x) {
		writeBit(x);
		;
	}

	public void write(byte x) {
		writeByte(x & 0xff);
	}

	/**
	 * Write the 32-bit int to the binary output stream
	 * 
	 * @param x
	 */
	public void write(int x) {
		writeByte((x >>> 24) & 0xff);
		writeByte((x >>> 16) & 0xff);
		writeByte((x >>> 8) & 0xff);
		writeByte((x >>> 0) & 0xff);
	}

	/**
	 * Write the r-bit int to the binary output stream
	 * 
	 * @param x
	 * @param r
	 */
	public void write(int x, int r) {
		if (r == 32) {
			write(x);
			return;
		}
		if (r < 1 || r > 32) {
			throw new RuntimeException("Illegal value for r = " + r);
		}
		if (x < 0 || x >= (1 << r)) {
			throw new RuntimeException("Illegal " + r + "-bit char = " + x);
		}
		for (int i = 0; i < r; i++) {
			boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
			writeBit(bit);
		}
	}

	/**
	 * Write out 64-bit long to the binary output stream
	 * 
	 * @param x
	 */
	public void write(long x) {
		writeByte((int) ((x >>> 56) & 0xff));
		writeByte((int) ((x >>> 48) & 0xff));
		writeByte((int) ((x >>> 40) & 0xff));
		writeByte((int) ((x >>> 32) & 0xff));
		writeByte((int) ((x >>> 24) & 0xff));
		writeByte((int) ((x >>> 16) & 0xff));
		writeByte((int) ((x >>> 8) & 0xff));
		writeByte((int) ((x >>> 0) & 0xff));
	}

	public void write(double x) {
		write(Double.doubleToRawLongBits(x));
	}

	/**
	 * Write the 32-bit float to the binary output stream.
	 * 
	 * @param x
	 */
	public void write(float x) {
		write(Float.floatToRawIntBits(x));
	}

	/**
	 * Write the 16-bit int to the binary output stream.
	 * 
	 * @param x
	 */
	public void write(short x) {
		writeByte((x >>> 8) & 0xff);
		writeByte((x >>> 0) & 0xff);
	}

	public void write(char x) {
		if (x < 0 || x > 256) {
			throw new RuntimeException("Illegal 8-bit char = " + x);
		}
		writeByte(x);
	}
	/**
	 * Write the r-bit char to the binary output stream
	 * @param x
	 * @param r
	 */
	public void write(char x, int r) {
		if (r == 8) {
			write(x);
			return;
		}
		if (r < 1 || r > 16) {
			throw new RuntimeException("Illegal value for r = " + r);
		}
		if (x < 0 || x >= (1 << r)) {
			throw new RuntimeException("Illegal " + r + "-bit char = " + x);
		}
		for (int i = 0; i < r; i++) {
			boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
			writeBit(bit);
		}
	}
	/**
	 * Write the string of 8-bit character to the binary output stream
	 * @param str
	 */
	public void write(String str){
		for (int i = 0; i < str.length(); i++) {
			write(str.charAt(i));
		}
	}
	
	/**
	 * Write the string of r-bit character to the binary output stream
	 * @param str
	 */
	public void write(String str,int r){
		for (int i = 0; i < str.length(); i++) {
			write(str.charAt(i),r);
		}
	}
	
	public static void main(String[] args) {
		String fileName = "./data/cgq.dat";
		BinaryOut out = new BinaryOut(fileName);
		BinaryIn in = new BinaryIn();
		while (!in.isEmpty()) {
			char c = in.readChar();
			out.write(c);
		}
		out.flush();
	}
}
