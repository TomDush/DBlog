package fr.dush.test.dblog.aspects;

import static org.junit.Assert.*;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Comment;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = { "/bdd/comments.xml" })
public class CommentPaginationTest extends AbstractJunitTest {

	private final CommentPagination commentPagination = new CommentPagination();

	@Inject
	private ICommentDAO commentDAO;

	@Inject
	private ITicketDAO ticketDAO;

	@Before
	public void manualInject() {
		commentPagination.setCommentDAO(commentDAO);
	}

	@Test
	@Ignore
	public void testReplaceCommentsCollection() throws Exception {
		assertNotNull("L'aspect n'est pas inject�e", commentPagination);

		final Ticket t = ticketDAO.findById(3);
		assertNotNull("Jeu de donn�e invalide", t);

		assertEquals(4, t.getComments().size()); // FIXME en mettre plus pour le test

		commentPagination.replaceCommentsCollection(t);

		assertEquals(5, t.getComments().size());
		// Test ordre
		Date d = null;
		for (final Comment c : t.getComments()) {
			if (d != null) {
				assertTrue(d.after(c.getCreationDate()));
			}

			d = c.getCreationDate();
		}

	}
}
