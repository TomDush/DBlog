package fr.dush.test.dblog.services.scopes;

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
