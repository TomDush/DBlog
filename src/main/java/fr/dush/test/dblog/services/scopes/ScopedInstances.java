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

	private final Map<String, Instance> instances = new HashMap<>();

	public ScopedInstances(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	/**
	 * Renvoie l'objet enregistré.
	 *
	 * @param name
	 * @param instance
	 * @return
	 */
	public Object register(String name, Object instance) {
		instances.put(name, new Instance(name, instance));
		return instance;
	}

	/**
	 * Renvoie un objet précédament enregistré, ou null.
	 *
	 * @param name
	 * @return
	 */
	public Object getInstance(String name) {
		if (instances.containsKey(name)) return instances.get(name).getInstance();

		return null;
	}

	/**
	 * Déréférence un bean de la liste des instances. Appel le callback s'il y en a un.
	 *
	 * @param name
	 * @return Renvoie ce bean s'il a été trouvé.
	 */
	public Object removeInstance(String name) {
		if (instances.containsKey(name)) {
			final Instance instance = instances.remove(name);
			if (instance.getCallback() != null)
				instance.getCallback().run();

			return instance.getInstance();
		}

		return null;
	}

	/**
	 * Contient une instance de ce nom
	 *
	 * @param key Nom de l'instance.
	 * @return TRUE si une instance correpond.
	 */
	public boolean containsInstance(Object key) {
		return instances.containsKey(key);
	}

	/**
	 * Ajoute un callback qui sera appelé quand l'objet de ce nom sera retiré.
	 *
	 * @param name
	 * @param callback
	 */
	public void setCallback(String name, Runnable callback) {
		final Instance instance = instances.get(name);
		if (instance != null) instance.setCallback(callback);
	}

}
