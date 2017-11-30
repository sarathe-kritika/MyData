package com.parkar.exception;

import com.parkar.exception.ParkarCoreCommonException;

public class ParkarCoreMobileException extends ParkarCoreCommonException {

	private static final long serialVersionUID = 689088837052178789L;

	/**
	 * Constructs a ParkarCoreMobileException Object via message
	 * 
	 * @param message:String
	 */
	public ParkarCoreMobileException(String message) {
		super(message);
	}

	
	/**
	 * Constructs a ParkarCoreMobileException Object via message and cause
	 * 
	 * @param message:String
	 * @param cause:Throwable
	 */
	public ParkarCoreMobileException(String message, Throwable cause) {
		super(message, cause);
	}
	

	/**
	 * Constructs a ParkarCoreMobileException Object via cause
	 * 
	 * @param cause:Exception
	 */
	public ParkarCoreMobileException(Exception cause) {
		super(cause);
	}
}
