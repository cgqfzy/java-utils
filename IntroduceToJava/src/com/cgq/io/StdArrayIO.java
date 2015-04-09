package com.cgq.io;

public class StdArrayIO {

	private StdArrayIO(){
		
	}
	
	public static void print(boolean[] a) {
		int n = a.length;
		StdOut.print(n + " [");
		for (int i = 0; i < a.length; i++) {
			StdOut.print(a[i] + " ");
		}
		StdOut.println("]");
	}

	public static void print(boolean[][] a) {
		int row = a.length;
		int col = a[0].length;
		StdOut.print(row + "*" + col);
		StdOut.println("[");
		for (int i = 0; i < row; i++) {
			StdOut.print("[");
			for (int j = 0; j < col; j++) {
				StdOut.print(a[i][j] + " ");
			}
			StdOut.println("]");
		}
		StdOut.println("]");
	}

	public static void print(double[] a) {
		int n = a.length;
		StdOut.print(n + " [");
		for (int i = 0; i < a.length; i++) {
			StdOut.printf("%9.5f", Double.valueOf(a[i]));
		}
		StdOut.println("]");
	}

	public static void print(double[][] a) {
		int row = a.length;
		int col = a[0].length;
		StdOut.print(row + "*" + col);
		StdOut.println("[");
		for (int i = 0; i < row; i++) {
			StdOut.print("[");
			for (int j = 0; j < col; j++) {
				StdOut.printf("%9.5f", Double.valueOf(a[i][j]));
			}
			StdOut.println("]");
		}
		StdOut.println("]");
	}

	public static void print(int[] a) {
		int n = a.length;
		StdOut.print(n + " [");
		for (int i = 0; i < a.length; i++) {
			StdOut.print(a[i] + " ");
		}
		StdOut.println("]");
	}

	public static void print(int[][] a) {
		int row = a.length;
		int col = a[0].length;
		StdOut.print(row + "*" + col);
		StdOut.println("[");
		for (int i = 0; i < row; i++) {
			StdOut.print("[");
			for (int j = 0; j < col; j++) {
				StdOut.print(a[i][j] + " ");
			}
			StdOut.println("]");
		}
		StdOut.println("]");
	}

	public static boolean[] readBoolean1D() {
		int n = StdIn.readInt();
		boolean[] a = new boolean[n];
		for (int i = 0; i < n; i++) {
			a[i] = StdIn.readBoolean();
		}
		return a;
	}

	public static boolean[][] readBoolean2D() {
		int row = StdIn.readInt();
		int col = StdIn.readInt();
		boolean[][] a = new boolean[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				a[i][j] = StdIn.readBoolean();
			}
		}
		return a;
	}

	public static int[] readInt1D() {
		int n = StdIn.readInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = StdIn.readInt();
		}
		return a;
	}

	public static int[][] readInt2D() {
		int row = StdIn.readInt();
		int col = StdIn.readInt();
		int[][] a = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				a[i][j] = StdIn.readInt();
			}
		}
		return a;
	}

	public static double[] readDouble1D() {
		int n = StdIn.readInt();
		double[] a = new double[n];
		for (int i = 0; i < n; i++) {
			a[i] = StdIn.readDouble();
		}
		return a;
	}

	public static double[][] readDouble2D() {
		int row = StdIn.readInt();
		int col = StdIn.readInt();
		double[][] a = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				a[i][j] = StdIn.readDouble();
			}
		}
		return a;
	}

	public static void main(String[] args) {
		double[] a = readDouble1D();
		print(a);
	}
}
