package fr.dush.test.dblog.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

/**
 * Controlleur founissant les données globale de l'application (version, host, ...)
 *
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
@Named("dblog")
public class DBlogController {

	@Inject @Value("dblog.version")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
