package fr.dush.test.dblog.dao.scope.impl;

import java.util.Locale;

import javax.inject.Named;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.controller.I18nController;
import fr.dush.test.dblog.dao.scope.IContextLocator;

/**
 * Mais où suis-je ?? Ne vous inquiétez plus, vous êtes ici -----> *
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
@Named("contextLocator")
@Scope(value = "singleton")
public class ContextLocatorImpl implements IContextLocator, ApplicationContextAware {

	private ApplicationContext context;

	@Override
	public Locale getCurrentLocale() {
		return context.getBean(I18nController.class).getLocale();
	}

	@Override
	public String getLanguageKey() {
		return getCurrentLocale().getISO3Language();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// Ce bean est utilisé très tôt : l'injection n'est pas encore active.
		context = applicationContext;
	}
}
