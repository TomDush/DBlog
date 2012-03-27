package fr.dush.test.dblog.engine.test;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.engine.AbstractJunitTest;

public class AlternativeTests extends AbstractJunitTest {

	@Inject
	private ICommentDAO commentDAO;

	@Test
	public void testImplementation() throws Exception {
		assertNotNull(commentDAO);

		long count = commentDAO.countByTicket(12);
		assertEquals(0, count);
	}
}
