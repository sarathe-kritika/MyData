package com.parkar.api.rest.exception;

import com.parkar.exception.ParkarCoreCommonException;

public class ParkarCoreAPIException extends ParkarCoreCommonException {

	private static final long serialVersionUID = 8962780449592331794L;

	/**
	 * Constructor with message
	 * 
	 * @param message: String
	 */
	public ParkarCoreAPIException(String message) {
		super(message);
	}

	/**
	 * Constructor with message and cause
	 * 
	 * @param message: String
	 * @param cause: Throwable
	 */
	public ParkarCoreAPIException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with cause
	 * 
	 * @param cause: Throwable
	 */
	public ParkarCoreAPIException(Exception cause) {
		super(cause);
	}

}
