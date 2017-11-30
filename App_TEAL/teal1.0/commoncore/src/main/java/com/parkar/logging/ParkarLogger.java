package com.parkar.logging;

import org.apache.log4j.Logger;

public class ParkarLogger {

	static Logger log = Logger.getLogger(ParkarLogger.class);

	/**
	 * Log trace enter statement for method.
	 */
	public static void traceEnter() {
		StackTraceElement[] stack = (new Throwable().getStackTrace());
		String methodName = stack[1].getMethodName();
		String classNameWithParkarException = stack[1].getClassName();
		String className = classNameWithParkarException
				.substring(classNameWithParkarException.lastIndexOf(".") + 1);
		log.info(String.format("Entering: %s.%s method", className, methodName));
	}

	/**
	 * Log trace leave statement for method.
	 */
	public static void traceLeave() {
		StackTraceElement[] stack = (new Throwable().getStackTrace());
		String methodName = stack[1].getMethodName();
		String classNameWithParkarException = stack[1].getClassName();
		String className = classNameWithParkarException
				.substring(classNameWithParkarException.lastIndexOf(".") + 1);
		log.info(String.format("Exiting: %s.%s method", className, methodName));
	}
}
