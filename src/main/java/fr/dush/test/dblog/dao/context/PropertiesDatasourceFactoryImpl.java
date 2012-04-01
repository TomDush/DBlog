package fr.dush.test.dblog.dao.context;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import fr.dush.test.dblog.exceptions.ConfigurationException;

@Named("DatasourceFactory")
public class PropertiesDatasourceFactoryImpl implements IDatasourceFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesDatasourceFactoryImpl.class);

	@Inject
	private IContextLocator contextLocator;

	private final Properties datasourcesProperties;

	@Inject
	public PropertiesDatasourceFactoryImpl(@Value("${datasources.filepath}") String datasourcesConfigurationPath) throws IOException {

		datasourcesProperties = PropertiesLoaderUtils.loadAllProperties(datasourcesConfigurationPath);
	}

	@Override
	public DataSource createDataSource() throws ConfigurationException {
		final String language = getCurrentLocale().getLanguage();
		LOGGER.info("Creating datasource for {} language, from properties file.", language);

		return readDatasource(language);
	}

	protected DataSource readDatasource(final String language) throws ConfigurationException {
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(getDatasourceProperty(language, "driverClassName"));
		datasource.setUrl(getDatasourceProperty(language, "url"));
		datasource.setUsername(getDatasourceProperty(language, "username"));
		datasource.setPassword(getDatasourceProperty(language, "password"));

		return datasource;
	}

	protected String getDatasourceProperty(final String language, final String field) throws ConfigurationException {
		final String prop = datasourcesProperties.getProperty(language + ".datasource."+ field);
		if(prop == null) throw new ConfigurationException("Field " + field + "is required for language "+ language);

		return prop;
	}

	protected Locale getCurrentLocale() {
		return contextLocator.getCurrentLocale();
	}
}
