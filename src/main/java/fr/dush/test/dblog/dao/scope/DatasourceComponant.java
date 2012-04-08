package fr.dush.test.dblog.dao.scope;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.dush.test.dblog.exceptions.ConfigurationException;

/**
 * Fournit, à partir d'une factory, la Datasource adaptée au contexte "language".
 *
 * <p>
 * L'implémentation de la véritable factory dépend du contexte d'exécution :
 * <ul>
 * <li>Tests unitaires : sources de données décrites dans un fichier properties</li>
 * <li>Déploiement en recette et production : sources de données définies dans un annuaire JNDI.
 * </ul>
 * </p>
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
