
package com.my.biz.scaneum.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions {
	
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}
	
	public static RuntimeException runtimeException(String msg, Exception e) {
		return new RuntimeException(msg, e);
	}
	
	public static RuntimeException runtimeException(Exception e) {
		return unchecked(e);
	}
	
	public static RuntimeException runtimeException(String msg) {
		return new RuntimeException(msg);
	}
	
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
