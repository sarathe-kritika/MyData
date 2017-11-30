package com.parkar.exception;

public class ParkarCoreCommonException extends Exception {
	 private static final long serialVersionUID = -6725534691869595212L;
	 
	 /**
	  * ParkarCoreCommonException's constructor with a message.
	  * 
	  * @param message: String
	  */
	 public ParkarCoreCommonException(String message) {
	        super(message);
	    }

	 /**
	  * ParkarCoreCommonException's constructor with a message.
	  * 
	  * @param message: String
	  * @param  cause:Throwable
	  */
	 public ParkarCoreCommonException(String message, Throwable cause) {
	  // TODO Auto-generated constructor stub
	  super(message, cause);
	 }

	 /**
	  * ParkarCoreCommonException's constructor with a message.
	  * 
	  * @param  cause:Exception
	  */
	 public ParkarCoreCommonException(Exception cause) {
	  // TODO Auto-generated constructor stub
	  super(cause);
	 }
}
