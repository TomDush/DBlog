package fr.dush.test.dblog.engine.mock.controller;

import java.util.Locale;

import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.controller.I18nController;

@Scope("thread")
public class I18nControllerMock extends I18nController {

	private Locale locale = new Locale("fr", "FR");

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}