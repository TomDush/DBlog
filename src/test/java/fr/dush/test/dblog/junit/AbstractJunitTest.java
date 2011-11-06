package fr.dush.test.dblog.junit;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;

import fr.dush.test.dblog.junit.dbunitapi.DBUnitJUnit4ClassRunner;
import fr.dush.test.dblog.junit.dbunitapi.IDatabaseScriptsReader;

@RunWith(DBUnitJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:WEB-INF/spring/context-global.xml", "classpath:WEB-INF/spring/context-persistence.xml" })
public abstract class AbstractJunitTest extends DBTestCase implements IDatabaseScriptsReader {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractJunitTest.class);

	@Autowired
	private BasicDataSource dataSource;

	@Autowired
	private LocalSessionFactoryBean sessionFactory;

	/**
	 * Database population scripts. The scripts are executed in the list order, ie from first to last The list may be left empty so that
	 * there are no rows in the database (only tables)
	 */
	private List<String> databasePopulationScripts;

	private boolean dropDatabase = false;

	@PostConstruct
	public void initConnexion() throws Exception {
		logger.debug("Initialisation of AbstractJunitTest.");

		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, dataSource.getDriverClassName());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, dataSource.getUrl());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, dataSource.getUsername());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, dataSource.getPassword());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		logger.debug("---> getDataSet()");
		List<IDataSet> dataSets = new LinkedList<>();

		DataFileLoader loader = new FlatXmlDataFileLoader();
		for (String script : databasePopulationScripts) {
			dataSets.add(loader.load(script));
		}

		IDataSet dataset = new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));

		logger.debug("<--- getDataSet()");
		return dataset;
	}

	/**
	 * Populate the in-memory database with test data
	 */
	@Before
	public void populateDatabase() throws Exception {

		if (databasePopulationScripts == null) {
			return;
		}

		if (databasePopulationScripts != null) {
			sessionFactory.createDatabaseSchema();
			setUp();
		} else logger.warn("No databasePopulationScripts.");
	}

	/**
	 * Drops the database schema so that it can be created on the next test
	 */
	@After
	public void dropDatabase() throws Exception {
		if (dropDatabase) {
			File file = new File("target/bdd/");
			if (!file.exists()) file.mkdir();

			String fileName = "target/bdd/" + this.getClass().getName() + "-db-"
					+ new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss.S").format(new java.util.Date()) + ".xml";

			IDataSet fullDataSet = new DatabaseConnection(dataSource.getConnection()).createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream(fileName));
		}

		sessionFactory.dropDatabaseSchema();
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	@Override
	public void setDatabasePopulationScripts(List<String> databaseScripts) {
		this.databasePopulationScripts = databaseScripts;
	}

	@Override
	public void setDropDatabase(boolean dropDatabase) {
		this.dropDatabase = dropDatabase;
	}
}
