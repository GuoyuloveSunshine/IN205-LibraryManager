package com.ensta.librarymanager.exception;

public class DaoException extends Exception{
	private static final long serialVersionUID = 1L;
	public DaoException() {
		super("Une erreur s'est produite dans le Dao.");
	}
	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
	public DaoException(String message) {
		super(message);
	}
	public DaoException(Throwable cause) {
		super(cause);
	}
}
