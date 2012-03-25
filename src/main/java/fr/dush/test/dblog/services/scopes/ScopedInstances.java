package fr.dush.test.dblog.services.scopes;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances dans un contexte language.
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class ScopedInstances {

	private final String language;

	private final Map<String, Object> instances = new HashMap<>();

	public ScopedInstances(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	/**
	 * Renvoie l'objet enregistré.
	 * @param name
	 * @param instance
	 * @return
	 */
	public Object register(String name, Object instance) {
		instances.put(name, instance);
		return instance;
	}

	/**
	 * Renvoie un objet précédament enregistré, ou null.
	 * @param name
	 * @return
	 */
	public Object getInstance(String name) {
		return instances.get(name);
	}

	/**
	 * Déréférence un bean de la liste des instances. Renvoie ce bean s'il a été trouvé.
	 * @param name
	 * @return
	 */
	public Object removeInstance(String name) {
		return instances.remove(name);
	}

	public boolean containsInstance(Object key) {
		return instances.containsKey(key);
	}

}
