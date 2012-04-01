package fr.dush.test.dblog.dao.context;

import java.util.Locale;

public interface IContextLocator {

	/**
	 * Retrouve la locale dans laquelle on est...
	 * @return
	 */
	Locale getCurrentLocale();

	/**
	 * Renvoie un identifiant unique de la langue utilisée.
	 * @return
	 */
	String getLanguageKey();

}