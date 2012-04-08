package fr.dush.test.dblog.dao.context;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.engine.AbstractSimpleSpringJunitTest;

public class DatasourceComponantTest extends AbstractSimpleSpringJunitTest {

	@Inject
	private ApplicationContext context;

	@Inject
	private DataSource datasource;

	@Test
	public void testCreateDatasource() throws Exception {
		assertNotNull(datasource);
		if (datasource instanceof BasicDataSource) {
			assertTrue(((BasicDataSource) datasource).getUrl().contains("fr"));
		} else {
			fail("datasource NOT instanceof BasicDataSource");
		}
	}

	@Test
	public void testCreateDatasourceWithName() throws Exception {
		final DataSource datasource = (DataSource) context.getBean("datasource");
		assertNotNull(datasource);
		if (datasource instanceof BasicDataSource) {
			assertTrue(((BasicDataSource) datasource).getUrl().contains("fr"));
		} else {
			fail("datasource NOT instanceof BasicDataSource");
		}
	}

	@Test
	public void testSessionFactory() throws Exception {
		final SessionFactory sessionFactory = context.getBean(SessionFactory.class);
		assertNotNull(sessionFactory);
	}

}
