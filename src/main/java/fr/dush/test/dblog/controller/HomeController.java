package fr.dush.test.dblog.controller;

import javax.faces.bean.ManagedBean;


@ManagedBean(name = "home")
public class HomeController {

	public String getText() {
		return "Welcome in DBlog !";
	}

}
