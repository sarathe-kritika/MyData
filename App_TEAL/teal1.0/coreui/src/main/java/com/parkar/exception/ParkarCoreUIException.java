package com.parkar.exception;

import com.parkar.exception.ParkarCoreCommonException;

public class ParkarCoreUIException extends ParkarCoreCommonException {

	private static final long serialVersionUID = 689088837052178789L;

	/**
	 * Constructs a ParkarCoreUIException Object via message
	 * 
	 * @param message:String
	 */
	public ParkarCoreUIException(String message) {
		super(message);
	}

	
	/**
	 * Constructs a ParkarCoreUIException Object via message and cause
	 * 
	 * @param message:String
	 * @param cause:Throwable
	 */
	public ParkarCoreUIException(String message, Throwable cause) {
		super(message, cause);
	}
	

	/**
	 * Constructs a ParkarCoreUIException Object via cause
	 * 
	 * @param cause:Exception
	 */
	public ParkarCoreUIException(Exception cause) {
		super(cause);
	}
}
