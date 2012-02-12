package fr.dush.test.dblog.services.page;

import java.util.LinkedList;
import java.util.List;

public class Page<Type> {

	/**
	 * Eléments de cette page
	 */
	private List<Type> elements = new LinkedList<>();

	/**
	 * Nombre d'éléments en tout
	 */
	private long elementsSize;

	/**
	 * Numéro de la page.
	 */
	private int pageNumber;

	/**
	 * Taille d'une page
	 */
	private int pageSize;

	public Page(int pageSize, long elementsSize) {
		this.pageSize = pageSize;
		this.elementsSize = elementsSize;
	}

	public List<Type> getElements() {
		return elements;
	}

	public void setElements(List<Type> elements) {
		this.elements = elements;
	}

	public long getElementsSize() {
		return elementsSize;
	}

	public void setElementsSize(long elementsSize) {
		this.elementsSize = elementsSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
