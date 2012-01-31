package org.meri.jpa.util;

@SuppressWarnings("serial")
public class ConfigurationException extends RuntimeException {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
