package fr.dush.test.dblog.controller;

import javax.inject.Named;


@Named(value = "home")
public class HomeController {

	public String getText() {
		return "This is a dynamic controller provided text.";
	}

	public String getSearchAction() {
		return "search";
	}

}
