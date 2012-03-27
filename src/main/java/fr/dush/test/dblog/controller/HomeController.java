package fr.dush.test.dblog.controller;

import javax.inject.Named;


@Named(value = "home")
public class HomeController {

	public String getText() {
		return "This is a dynamic controller provided text.";
	}

	/**
	 * Méthode appelée par les EL (dans les textes)
	 * @return
	 */
	public String getSearchAction() {
		return "search";
	}

	/**
	 * Méthode appelée dans les <i>actions</i> des formulaires/buttons de submit.
	 * @return
	 */
	public String searchAction() {
		return "search";
	}

}
