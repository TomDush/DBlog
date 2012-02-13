package fr.dush.test.dblog.exceptions;

/**
 * Erreur de configuration.
 *
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 3428173183271573245L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(final String message) {
		super(message);
	}

	public ConfigurationException(final Throwable cause) {
		super(cause);
	}

}
