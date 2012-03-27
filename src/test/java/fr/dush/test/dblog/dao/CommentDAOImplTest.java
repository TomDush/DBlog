package fr.dush.test.dblog.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Comment;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = { "/bdd/comments.xml" })
public class CommentDAOImplTest extends AbstractJunitTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CommentDAOImplTest.class);

	@Inject
	private ICommentDAO commentDAO;

	@Inject
	private ITicketDAO ticketDAO;

	@Test
	public void readAll() {
		final Collection<Comment> comments = commentDAO.findByTicket(3, 0, 50);

		assertEquals(4, comments.size());
		for (final Comment c : comments) {
			logger.debug("Comment : {}", c);
		}
	}

	@Test
	public void testDelete() {
		final Integer id = 5;

		Comment c = commentDAO.findById(id);
		assertNotNull("Test init failed.", c);

		commentDAO.delete(id);

		c = commentDAO.findById(id);
		assertNull("Test init failed.", c);
	}

	@Test
	public void testFindById() {
		final Comment comment = commentDAO.findById(3);
		assertNotNull(comment);
		assertEquals(new Integer(3), comment.getIdComment());
		assertEquals(new Integer(2), comment.getTicket().getIdTicket());
		assertEquals("My Message", comment.getMessage());
		assertEquals("2011-10-09 00:00:00.0", comment.getDate().toString());
		assertEquals("NiceGirl", comment.getAuthorName());
	}

	@Test
	public void testFindByTicket() {
		final int idTicket = 3;

		// complet
		List<Comment> comments = commentDAO.findByTicket(idTicket, 0, 10);
		assertNotNull(comments);
		assertEquals(4, comments.size());
		assertEquals("Nov", comments.get(0).getMessage());
		assertEquals("Jan", comments.get(3).getMessage());

		// first page of 2
		comments = commentDAO.findByTicket(idTicket, 0, 2);
		assertNotNull(comments);
		assertEquals(2, comments.size());
		assertEquals("Nov", comments.get(0).getMessage());
		assertEquals("Oct", comments.get(1).getMessage());

		// second page of 2
		comments = commentDAO.findByTicket(idTicket, 2, 2);
		assertNotNull(comments);
		assertEquals(2, comments.size());
		assertEquals("Mar", comments.get(0).getMessage());
		assertEquals("Jan", comments.get(1).getMessage());

		// third page de 3
		comments = commentDAO.findByTicket(idTicket, 3, 3);
		assertNotNull(comments);
		assertEquals(1, comments.size());
		assertEquals("Jan", comments.get(0).getMessage());
	}

	@Test
	public void testCountByTicket() {
		assertEquals(4, commentDAO.countByTicket(3));
		assertEquals(1, commentDAO.countByTicket(2));
	}

	@Test
	public void testMerge() {
		final long count = commentDAO.countByTicket(2);

		final Comment c = new Comment();
		c.setAuthorName("me");
		c.setDate(new Date());
		c.setMessage("J'ai rien a dire");
		c.setTicket(ticketDAO.findById(2));

		commentDAO.merge(c);

		assertEquals(1, commentDAO.countByTicket(2) - count);
	}

}
