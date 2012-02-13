package fr.dush.test.dblog.services;

import java.util.Set;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;

public interface II18nManager {

	/**
	 * Revoie les langues disponibles pour l'application
	 * @return
	 */
	public abstract Set<AvailableLocale> getAvailableLocales();

}