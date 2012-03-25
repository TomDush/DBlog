package fr.dush.test.dblog.services.scopes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import fr.dush.test.dblog.controller.I18nController;

/**
 * L'application repose simultanément sur 2 bases de données. L'une pour la
 * langue française, l'autre pour la langue anglaise. Les beans qui dépendent
 * des base de données ont un scope <i>language</i> qui est défini ici.
 *
 * @author dush
 *
 */
//@Named
public class LanguageScope implements Scope, ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageScope.class);

	private ApplicationContext context;

	private final Map<String, ScopedInstances> scopedInstances = new HashMap<>();

	public LanguageScope() {
		LOGGER.debug("Create new LanguageScope. (context = {})", context);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static String getLanguageFromLocale(Locale locale) {
		return locale.getISO3Language();
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		LOGGER.debug("Request {} object.", name);

		final String languageKey = getLanguage();

		// Recherche des instances de ce contexte
		ScopedInstances instances = scopedInstances.get(languageKey);
		if(instances == null) {
			instances = new ScopedInstances(languageKey);
			scopedInstances.put(languageKey, instances);
		}

		// Recherche de l'instance demandée.
		if(instances.containsInstance(name)) {
			return instances.getInstance(name);
		} else {
			return instances.register(name, objectFactory.getObject());
		}
	}

	@Override
	public Object remove(String name) {
		LOGGER.debug("Remove '{}' object.", name);

		// TODO remove devrai être géré.
		throw new UnsupportedOperationException("Remove object : " + name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		LOGGER.debug("registerDestructionCallback '{}' object.", name);
		callback.run();
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null; // ??
	}

	@Override
	public String getConversationId() {
		return Thread.currentThread().getName(); // FIXME revoie une valeur au pif..
	}

	/**
	 * Retourne la clef du langage utilisé.
	 * @return
	 */
	protected String getLanguage() {
		final I18nController i18nController = context.getBean(I18nController.class);
		return getLanguageFromLocale(i18nController.getLocale());
	}

}
