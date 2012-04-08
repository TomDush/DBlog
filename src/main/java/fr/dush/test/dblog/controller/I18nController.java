package fr.dush.test.dblog.controller;

import java.util.Collection;
import java.util.Locale;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.services.II18nManager;

@Named("i18n")
@Scope("session")
public class I18nController {

	private static final Logger LOGGER = LoggerFactory.getLogger(I18nController.class);

	@Inject
	private II18nManager i18nManager;

	private Locale locale;

	public AvailableLocale[] getAvailableLocalesList() {
		final Collection<AvailableLocale> locales = i18nManager.getAvailableLocales();
		LOGGER.debug("getAvailableLocalesList {}", locales);
		return locales.toArray(new AvailableLocale[locales.size()]);
	}

	/**
	 * Change la langue de la page courrante et sauvegarde la préférence.
	 * @param locale
	 */
	protected void changeLocale(final Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		this.locale = locale;
	}

	/**
	 * Méthode appellable à partir d'un HtmlCommandLink pour changer la langue du site.
	 * TODO Il y a plus simple ...
	 * @param event Event de source HtmlCommandLink qui détient un attribut "locale" de type Locale.
	 */
	public void onChangeLocale(final ActionEvent event) {
		if(event.getSource() instanceof HtmlCommandLink) {
			final Object obj = ((HtmlCommandLink)event.getSource()).getAttributes().get("locale");
			LOGGER.debug(" --> onChangeLocale  ({})", obj);
			if(obj instanceof Locale) {
				changeLocale((Locale) obj);
			}
		} else {
			LOGGER.warn("Unknown event {}", event);
		}
	}

	/**
	 * Renvoie la locale de la session en cours...
	 * @return
	 */
	public Locale getLocale() {
		if(locale == null) return FacesContext.getCurrentInstance().getViewRoot().getLocale();

		return locale;
	}

	/**
	 * Ne doit être utilisé que dans le cadre des tests.
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
