package fr.dush.test.dblog.dao.context;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.dush.test.dblog.exceptions.ConfigurationException;

/**
 * Factory vue par Spring pour créer des datasources. L'implémentation de la véritable factory est paramétrable afin de permettre la configuration des datasource de
 * plusieurs façon (datasource, pool, JNDI).
 *
 * TODO Créer une factory de datasource par JNDI
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
@Component
public class DatasourceComponant {

	@Inject
	private IDatasourceFactory datasourceFactory;

	@Bean(name = "datasource")
	@Scope("language")
	public DataSource createDatasource() throws ConfigurationException {
		return datasourceFactory.createDataSource();
	}

}
