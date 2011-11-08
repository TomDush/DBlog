package fr.dush.test.dblog.controller;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "main", eager = true)
public class HomeController {

	public String getText() {
		return "Welcome in DBlog !";
	}

}
