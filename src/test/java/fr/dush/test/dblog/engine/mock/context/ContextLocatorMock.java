package fr.dush.test.dblog.engine.mock.context;

import java.util.Locale;

import fr.dush.test.dblog.dao.context.IContextLocator;

public class ContextLocatorMock implements IContextLocator {

	private Locale locale = new Locale("fr", "FR");

	@Override
	public Locale getCurrentLocale() {
		return locale;
	}

	@Override
	public String getLanguageKey() {
		return getCurrentLocale().getISO3Language();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
