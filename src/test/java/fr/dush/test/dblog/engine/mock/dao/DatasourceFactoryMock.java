package fr.dush.test.dblog.engine.mock.dao;

import java.io.IOException;

import javax.sql.DataSource;

import fr.dush.test.dblog.dao.scope.IDatasourceFactory;
import fr.dush.test.dblog.dao.scope.impl.PropertiesDatasourceFactoryImpl;
import fr.dush.test.dblog.exceptions.ConfigurationException;

public class DatasourceFactoryMock extends PropertiesDatasourceFactoryImpl implements IDatasourceFactory {

	public DatasourceFactoryMock(String datasourcesConfigurationPath) throws IOException {
		super(datasourcesConfigurationPath);
	}

	@Override
	public DataSource createDataSource() throws ConfigurationException {
		// Une seule source de données : FR !
		return readDatasource("fr");
	}

}
