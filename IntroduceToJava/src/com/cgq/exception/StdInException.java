package com.cgq.exception;

public class StdInException extends Exception{

	private String errorInfo;
	public StdInException() {
		// TODO Auto-generated constructor stub
	}
	public StdInException(String errorInfo) {
		// TODO Auto-generated constructor stub
		this.errorInfo = errorInfo;
	}
	public String toString() {
		return "StdInException [errorInfo=" + errorInfo + "]";
	}
	
}
