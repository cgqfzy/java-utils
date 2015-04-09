package com.cgq.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

/**
 * @comment read bits from files and URLs
 * @author cgqfzy
 * @version 1.0
 * @date Apr 9, 2015 6:17:04 PM
 */
public class BinaryIn {

	private static final int EOF = 0;
	private BufferedInputStream in;
	private int buffer;
	private int N;
	public BinaryIn() {
		// TODO Auto-generated constructor stub
		in = new BufferedInputStream(System.in);
		fillBuffer();
	}
	
	public BinaryIn(InputStream is){
		in = new BufferedInputStream(is);
		fillBuffer();
	}
	
	
	public BinaryIn(URL url){
		try {
			in = new BufferedInputStream(url.openConnection().getInputStream());
			fillBuffer();
		} catch (IOException e) {
			System.err.println("Could not open " + url);
		}
	}
	
	
	public BinaryIn(Socket socket){
		try {
			in = new BufferedInputStream(socket.getInputStream());
			fillBuffer();
		} catch (IOException e) {
			System.err.println("Could not open " + socket);
		}
	}
	
	public BinaryIn(String str){
		try{
			File file = new File(str);
			if(file.exists()){
				in = new BufferedInputStream(new FileInputStream(file));
				fillBuffer();
				return;
			}
			URL url = getClass().getResource(str);
			if (url == null) {
				url = new URL(str);
			}
			in = new BufferedInputStream(url.openConnection().getInputStream());
			fillBuffer();
		}catch(Exception e){
			System.err.println("Could not open " + str);
		}
	}
	
	private void fillBuffer(){
		try {
			buffer = in.read();
			N = 8;
		} catch (Exception e) {
			System.err.println("EOF");
			buffer = EOF;
			N = -1;
		}
	}
	
	/**
     * Does the binary input stream exist?
     */
	private boolean exists(){
		return in != null;
	}
	/**
	 * Return true if the binary input stream is empty
	 * @return
	 */
	public boolean isEmpty(){
		return buffer == EOF;
	}
	
	/**
	 * Read the next bit of data from the binary input stream and return as a boolean.
	 * @return
	 */
	public boolean readBoolean(){
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		N--;
		boolean bit = ((buffer >> N) & 1) ==1;
		if (N == 0) {
			fillBuffer();
		}
		return bit;
	}
	
	/**
	 * Read the next 8 bits from binary input stream and return as char
	 * @return
	 */
	public char readChar(){
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		if (N == 8){
			int x = buffer;
			fillBuffer();
			return (char)(x & 0xff);
		}
		//combine last N bits of current buffer with first 8-N bits of new buffer
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
	 * Read the next r bits from binary input stream and return as r-bit character
	 * @param r
	 * @return
	 */
	public char readChar(int r){
		if(r < 1 || r > 16){
			throw new IllegalArgumentException("Illegal value of r = " + r);
		}
		if (r == 8){
			return readChar();
		}
		char x = 0;
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
	 * Read the remaining bytes of data from binary input stream and return as a string
	 * @return
	 */
	public String readString(){
		if (isEmpty()) {
			throw new RuntimeException("Reading from empty input stream");
		}
		StringBuilder sb = new StringBuilder();
		while (!isEmpty()) {
			sb.append(readChar());
		}
		return sb.toString();
	}
	/**
	 * Read the next 16 bits from the binary input stream and return as a 16-bit short
	 * @return
	 */
	public short readShort(){
		short x = 0;
		for (int i = 0; i < 2; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	/**
	 * Read the next 32 bits from binary input stream and return as a 32-bit int
	 * @return
	 */
	public int readInt(){
		int x = 0;
		for (int i = 0; i < 4; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	/**
	 * Read the next r bits from the binary input stream and return as r-bit int
	 * @param r
	 * @return
	 */
	public int readInt(int r){
		if(r < 1 || r > 16){
			throw new IllegalArgumentException("Illegal value of r = " + r);
		}
		int x = 0;
		for (int i = 0; i < r; i++) {
			boolean bit = readBoolean();
			x <<= 1;
			if (bit){
				x |= 1;
			}
		}
		return x;
	}
	
	/**
	 * Read the next 64 bits from binary input stream and return as a 64-bit long
	 * @return
	 */
	public long readLong(){
		long x = 0;
		for (int i = 0; i < 8; i++) {
			char c = readChar();
			x <<= 8;
			x |= c;
		}
		return x;
	}
	
	public double readDouble(){
		return Double.longBitsToDouble(readLong());
	}
	
	public float readFloat(){
		return Float.intBitsToFloat(readInt());
	}
	/**
	 * Read the next 8 bits from the binary input stream and return a byte
	 * @return
	 */
	public byte readByte(){
		char c = readChar();
		byte x = (byte)(c & 0xff);
		return x;
	}
	
	
}
