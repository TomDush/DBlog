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

	private String language;

	private String icon;

	public String getFullLocale() {
		return locale.getLanguage();
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "AvailableLocale [locale=" + locale + ", name=" + language + ", icon=" + icon + ", getFullLocale()=" + getFullLocale() + "]";
	}

}
