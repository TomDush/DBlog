package fr.dush.test.dblog.dao.scope.impl;

import static org.junit.Assert.*;

import java.util.Locale;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.dao.context.AbstractSpringJunitTest;
import fr.dush.test.dblog.dao.scope.IDatasourceFactory;
import fr.dush.test.dblog.engine.context.ContextedThread;
import fr.dush.test.dblog.exceptions.ConfigurationException;

public class PropertiesDatasourceFactoryImplTest extends AbstractSpringJunitTest {

	@Inject
	private ApplicationContext context;

	@Inject
	private IDatasourceFactory datasourceFactory;

	@Test
	public void testCreateDataSourceFR() throws Exception {
		assertNotNull(datasourceFactory);

		final DataSource datasource = datasourceFactory.createDataSource();

		assertNotNull(datasource);
		if (datasource instanceof BasicDataSource) {
			assertNotNull(((BasicDataSource) datasource).getUrl());
			assertTrue("contains FR", ((BasicDataSource) datasource).getUrl().contains("fr"));
		} else
			fail(datasource + "is not instance BasicDataSource");
	}

	@Test
	public void testCreateDataSourceEN() throws Throwable {
		assertNotNull(datasourceFactory);

		final ContextedThread t = new ContextedThread(context, new Locale("en", "US")) {

			@Override
			public void run() {
				try {
					final DataSource datasource = datasourceFactory.createDataSource();

					assertNotNull(datasource);
					if (datasource instanceof BasicDataSource) {
						assertNotNull(((BasicDataSource) datasource).getUrl());
						assertTrue("contains FR", ((BasicDataSource) datasource).getUrl().contains("en"));
					} else {
						fail(datasource + "is not instance BasicDataSource");
					}
				} catch (final ConfigurationException e) {
					throw new RuntimeException(e);
				}
			}

		};

		t.startAndWait();
		t.throwError();
	}

}
