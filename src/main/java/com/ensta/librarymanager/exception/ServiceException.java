package com.ensta.librarymanager.exception;

public class ServiceException extends Exception{
	private static final long serialVersionUID = 1L;
	public ServiceException() {
		super("Une erreur s'est produite dans le Service.");
	}
	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
