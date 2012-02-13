package fr.dush.test.dblog.dto.i18n;

import java.util.Locale;

/**
 * Locale (langue, pays, nom icone) disponible pour l'application.
 *
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
public class AvailableLocale {

	private Locale locale;

	private String name;

	private String icon;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}

}
