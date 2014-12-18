package com.store.exception;

public class DBException extends Exception {
	private static final long serialVersionUID = -2952482717014475456L;

	public DBException() {
		super();
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}