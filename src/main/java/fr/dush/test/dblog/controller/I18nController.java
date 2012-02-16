package fr.dush.test.dblog.controller;

import java.util.Collection;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.services.II18nManager;

@Named("i18n")
public class I18nController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(I18nController.class);

	@Inject
	private II18nManager i18nManager;

	public AvailableLocale[] getAvailableLocalesList() {
		final Collection<AvailableLocale> locales = i18nManager.getAvailableLocales();
		return locales.toArray(new AvailableLocale[locales.size()]);
	}

	public void changeLocale(final Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

	public void onChangeLocale(final ActionEvent event) {
		logger.info(" --> onChangeLocale  ({})", event);
		final String strLocale = event.getComponent().getId();
		logger.warn("Change language to {}", strLocale);
		changeLocale(new Locale(strLocale));
	}
}
