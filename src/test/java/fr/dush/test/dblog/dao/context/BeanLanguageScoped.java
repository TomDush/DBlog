package fr.dush.test.dblog.dao.context;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope("language")
public class BeanLanguageScoped {

	private String message;

	public BeanLanguageScoped() {
		System.out.println("Création d'un bean scopé.");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
