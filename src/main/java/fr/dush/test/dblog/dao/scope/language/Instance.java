package fr.dush.test.dblog.dao.scope.language;

/**
 * Conserve l'instance d'un objet dont le scope est limité au langage. Cette classe est utilisée par {@link LanguageScope}.
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class Instance {

	private String name;

	private Object instance;

	private Runnable callback;

	public Instance(String name, Object instance) {
		this.name = name;
		this.instance = instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public Runnable getCallback() {
		return callback;
	}

	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

}
