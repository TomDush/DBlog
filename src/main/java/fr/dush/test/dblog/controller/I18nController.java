package fr.dush.test.dblog.controller;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.services.II18nManager;

@Named("i18n")
public class I18nController {

	@Inject
	private II18nManager i18nManager;

	public AvailableLocale[] getAvailableLocalesList() {
		final Collection<AvailableLocale> locales = i18nManager.getAvailableLocales();
		return locales.toArray(new AvailableLocale[locales.size()]);
	}
}
