package fr.dush.test.dblog.dao.scope.language;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import fr.dush.test.dblog.dao.scope.IContextLocator;


/**
 * L'application est multi langue. Pour certaines raisons (performances ?), chaque langue a sa prope base de données.<br/>
 * Tous les objets d'accès aux données ont une porté limitée à une langue. Les objets qui ont cette limitations de portée sont :
 * <ul>
 * <li>Source de données (datasource) : une définition et instance par langue</li>
 * <li>SessionFactory d'hibernate</li>
 * <li>Tous les DAO</li>
 * <li>Les objets métier (services). Ils peuvent donc conserver une forme de cache (pour la validation par exemple) sans se préoccuper de gérer le multilangue.</li>
 * </ul>
 *
 * <p>
 * Tous ces objets n'ont pas à gérer le mode multi-langue.<br/>
 * Les autres objets, comme les controlleurs WEB, ont une portée plus limitée que la langue (session ou request). Le système multi-langue est transparent tant que
 * cette contrainte est respectée.
 * </p>
 *
 * <p>
 * Dans cet objet LanguageScope, on défini le {@link Scope} (sens Spring) <i>language</i>. Le bean {@link IContextLocator} indique la langue pour laquelle le code
 * est exécuté. Les références des objets de ce scope sont concervées ici.
 * </p>
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class LanguageScope implements Scope, ApplicationContextAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageScope.class);

	/**
	 * Indique le langage dans lequel est exécuter le code.
	 */
	private IContextLocator contextLocator;

	/**
	 * Références de tous les objets instanciés dont la portée est <i>language</i>.
	 */
	private final Map<String, ScopedInstances> scopedInstances = new HashMap<>();

	public LanguageScope() {
		LOGGER.debug("Create new LanguageScope.");
	}

	public void setContextLocator(IContextLocator contextLocator) {
		this.contextLocator = contextLocator;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// On ne peut pas injecter des beans dans un objet context (les scopes sont créés avant tout...)
		contextLocator = applicationContext.getBean(IContextLocator.class);
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		final String languageKey = getLanguage();

		LOGGER.debug("Request '{}' object in '{}' scope.", name, languageKey);

		// Recherche des instances de ce contexte
		ScopedInstances instances = scopedInstances.get(languageKey);
		if (instances == null) {
			instances = new ScopedInstances(languageKey);
			scopedInstances.put(languageKey, instances);
		}

		// Recherche de l'instance demandée.
		if (instances.containsInstance(name)) {
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
		if (instances != null) return instances.removeInstance(name);

		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		LOGGER.debug("registerDestructionCallback '{}' object in context '{}'.", name, getLanguage());

		final ScopedInstances instances = scopedInstances.get(getLanguage());
		if (instances != null) {
			instances.setCallback(name, callback);
		}
	}

	@Override
	public Object resolveContextualObject(String key) {
		final String languageKey = getLanguage();
		LOGGER.debug("resolveContextualObject '{}' object in '{}' scope.", key, languageKey);

		final ScopedInstances instances = scopedInstances.get(languageKey);
		if (instances != null) return instances.getInstance(key);

		return null;
	}

	@Override
	public String getConversationId() {
		return getLanguage();
	}

	/**
	 * Retourne la clef du langage utilisé.
	 *
	 * @return
	 */
	protected String getLanguage() {
		return contextLocator.getLanguageKey();
	}

}
