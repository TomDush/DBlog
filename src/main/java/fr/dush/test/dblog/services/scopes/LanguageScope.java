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
public class LanguageScope implements Scope, ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageScope.class);

	private ApplicationContext context;

	private final Map<String, ScopedInstances> scopedInstances = new HashMap<>();

	public LanguageScope() {
		LOGGER.debug("Create new LanguageScope.");
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
		final String languageKey = getLanguage();

		LOGGER.debug("Request '{}' object in '{}' scope.", name, languageKey);

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
		final String languageKey = getLanguage();

		LOGGER.debug("Remove '{}' object from scope {}.", name, languageKey);

		// Retire l'instance en question du contexte
		final ScopedInstances instances = scopedInstances.get(languageKey);
		if(instances != null) return instances.removeInstance(name);

		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		// TODO implementer la méthode. On NE cherche PAS à fermer l'objet !!
		LOGGER.debug("registerDestructionCallback '{}' object in context '{}'.", name, getLanguage());
//		callback.run();
	}

	@Override
	public Object resolveContextualObject(String key) {
		LOGGER.debug("resolveContextualObject '{}' object in '{}' scope.", key, getLanguage());

		// TODO renvoyer l'objet demandé, s'il existe.
		return null; // ??
	}

	@Override
	public String getConversationId() {
		return getLanguage();
	}

	/**
	 * Retourne la clef du langage utilisé.
	 * @return
	 */
	protected String getLanguage() {
		// FIXME se baser sur le context locator...
		final I18nController i18nController = context.getBean(I18nController.class);
		return getLanguageFromLocale(i18nController.getLocale());
	}

}
