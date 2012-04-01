package fr.dush.test.dblog.dao.context;

import java.io.IOException;
import java.sql.SQLException;
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
	public DataSource createDataSource() {
		final String language = getCurrentLocale().getLanguage();
		LOGGER.info("Creating datasource for {} language, from properties file.", language);

		return readDatasource(language);
	}

//	@Bean
//	@Scope("language")
//	public SessionFactory createSessionFactory(DataSource datasource,
//			@Value("application.package") String applicationPackage,
//			@Value("hibernate.propertiesPath") String hibernatePropsPath)
//					throws IOException {
//
//		final Properties hibernateProperties = PropertiesLoaderUtils.loadAllProperties(hibernatePropsPath);
//
//		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(datasource);
//		sessionFactory.setPackagesToScan(applicationPackage);
//		sessionFactory.setHibernateProperties(hibernateProperties);
//
//		return sessionFactory.getObject();
//	}

	protected DataSource readDatasource(final String language) {
		// TODO Exception si pas de datasource spécifiée.

		final BasicDataSource datasource = new BasicDataSource(){
			private final Logger LOGGER = LoggerFactory.getLogger(PropertiesDatasourceFactoryImpl.class.getCanonicalName() + ".BasicDataSource");


			@Override
			public synchronized void close() throws SQLException {
				LOGGER.error("Mais qui cherche à fermer cette datasource ?!");
				throw new RuntimeException("Mais qui cherche à fermer cette datasource ?!");
//				super.close();
			}

		};
		datasource.setDriverClassName(datasourcesProperties.getProperty(language + ".datasource.driverClassName"));
		datasource.setUrl(datasourcesProperties.getProperty(language + ".datasource.url"));
		datasource.setUsername(datasourcesProperties.getProperty(language + ".datasource.username"));
		datasource.setPassword(datasourcesProperties.getProperty(language + ".datasource.password"));

		return datasource;
	}

	protected Locale getCurrentLocale() {
		return contextLocator.getCurrentLocale();
	}
}
