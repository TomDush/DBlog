package fr.dush.test.dblog.dao.context;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import fr.dush.test.dblog.controller.I18nController;

/**
 * Mais où suis-je ?? Ne vous inquiétez plus, vous êtes ici -----> *
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
@Named("contextLocator")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
public class ContextLocatorImpl implements IContextLocator {

	@Inject
	private ApplicationContext context;

	@Override
	public Locale getCurrentLocale() {
		return context.getBean(I18nController.class).getLocale();
	}
}
