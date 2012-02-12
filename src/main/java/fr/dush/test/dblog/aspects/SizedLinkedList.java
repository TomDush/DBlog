package fr.dush.test.dblog.aspects;

import java.util.Collection;
import java.util.LinkedList;

public class SizedLinkedList<Type> extends LinkedList<Type> {

	private static final long serialVersionUID = -3253128770952729330L;

	private int size = 0;

	public SizedLinkedList() {
		super();
	}

	public SizedLinkedList(final Collection<? extends Type> c) {
		super(c);
	}

	@Override
	public int size() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getRealSize() {
		return super.size();
	}
}
