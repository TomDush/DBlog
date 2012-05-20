package fr.dush.test.dblog.dao.model.hibernate;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dao.model.IScoreDAO;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dao.security.IUserDAO;
import fr.dush.test.dblog.dto.model.Score;
import fr.dush.test.dblog.dto.model.ScoreId;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.dto.security.User;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = { "/bdd/tickets.xml", "/bdd/scores.xml" })
public class ScoreDAOImplTest extends AbstractJunitTest {

	@Inject
	private IScoreDAO scoreDAO;

	@Inject
	private ITicketDAO ticketDAO;

	@Inject
	private IUserDAO userDAO;

	@Test
	public void testCalculScore() {
		assertEquals(new Double(5), new Double(scoreDAO.calculScore(new Ticket(4))));
		assertEquals(new Double(3.5), new Double(scoreDAO.calculScore(new Ticket(5))));
		assertEquals(new Double(0), new Double(scoreDAO.calculScore(new Ticket(7))));
	}

	@Test
	public void testFindByTicket() {
		assertEquals(3, scoreDAO.findByTicket(new Ticket(4)).size());
		assertEquals(2, scoreDAO.findByTicket(new Ticket(5)).size());
	}

	@Test
	public void testFindById() {
		Score s = scoreDAO.findById(new ScoreId(4, 2));
		assertNotNull(s);
		assertEquals(5, s.getScore());
	}

	@Test
	// @DatabaseScripts(dumpDatabase = true)
	public void testSave() {
		User user = userDAO.findById(2);
		// User user = new User();
		// user.setEmail("my@email.fr");
		// user.setLogin("him");
		assertNotNull(user);
		Ticket ticket = ticketDAO.findById(6);
		// Ticket ticket = new Ticket();
		// ticket.setAuthor(user);
		// ticket.setCreationDate(new Date());
		// ticket.setTitle("Nouvelle fonctionnalité !");
		// ticket.setMessage("Ajout de notation des tickets");
		assertNotNull(ticket);

		Score score = new Score();
		score.setScore(3);
		score.setTicket(ticket);
		score.setUser(user);

		scoreDAO.save(score);

		assertEquals(1, scoreDAO.findByTicket(ticket).size());
	}

}
