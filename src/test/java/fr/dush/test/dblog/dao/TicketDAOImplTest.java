package fr.dush.test.dblog.dao;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = { "/bdd/tickets.xml" })
public class TicketDAOImplTest extends AbstractJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TicketDAOImplTest.class);

	@Inject
	private ITicketDAO ticketDAO;

	@Test
	public void testRead() throws Exception {
		final Ticket t = ticketDAO.findById(4);
		assertNotNull(t);
		assertEquals(new Integer(4), t.getIdTicket());
		assertEquals("Dush", t.getAuthorName());
		assertEquals("It's mine !", t.getMessage());
		assertEquals("Oct", t.getTitle());
		assertEquals(0, t.getComments().size());
		assertEquals("2011-10-08 00:00:00.0", t.getCreationDate().toString());
	}

	@Test
	public void testCreate() throws Exception {
		LOGGER.debug("---> testCreate()");

		final int size = ticketDAO.findAll().size();

		final Ticket t = new Ticket();
		t.setAuthorName("me");
		t.setCreationDate(new Date());
		t.setMessage("J'ai rien a dire");
		t.setTitle("Mon super message");

		ticketDAO.merge(t);

		assertEquals(1, ticketDAO.findAll().size() - size);

		LOGGER.debug("<---- testCreate()");
	}

	@Test
	public void testCount() {
		assertEquals(5, ticketDAO.count());
	}

	@Test
	public void readAll() {
		final Collection<Ticket> tickets = ticketDAO.findAll();

		assertEquals(5, ticketDAO.findAll().size());
		for (final Ticket t : tickets) {
			LOGGER.debug("Ticket : {}", t);
		}
	}

	@Test
	public void testDelete() {
		final Integer id = 4;

		Ticket t = ticketDAO.findById(id);
		assertNotNull("Test init failed.", t);

		ticketDAO.delete(id);

		t = ticketDAO.findById(id);
		assertNull("Test init failed.", t);
	}

	@Test
	public void testPage() {
		// complet
		List<Ticket> tickets = ticketDAO.findPage(0, 10);
		assertNotNull(tickets);
		assertEquals(5, tickets.size());
		assertEquals("Nov", tickets.get(0).getTitle());
		assertEquals("Jan", tickets.get(4).getTitle());

		// first page of 2
		tickets = ticketDAO.findPage(0, 2);
		assertNotNull(tickets);
		assertEquals(2, tickets.size());
		assertEquals("Nov", tickets.get(0).getTitle());
		assertEquals("Oct", tickets.get(1).getTitle());

		// second page of 2
		tickets = ticketDAO.findPage(2, 2);
		assertNotNull(tickets);
		assertEquals(2, tickets.size());
		assertEquals("Mai", tickets.get(0).getTitle());
		assertEquals("Mar", tickets.get(1).getTitle());

		// third page of 3
		tickets = ticketDAO.findPage(3, 3);
		assertNotNull(tickets);
		assertEquals(2, tickets.size());
		assertEquals("Mar", tickets.get(0).getTitle());
		assertEquals("Jan", tickets.get(1).getTitle());
	}

	@Test
	public void testCachedQuery() throws Exception {
		// il faut activer les requetes SQL et lire le log pour valider..

		List<Ticket> tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		LOGGER.info("next call of 5 tickets");
		tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		LOGGER.info("next call of 10 tickets");
		tickets = ticketDAO.findPage(0, 10);
		assertEquals(5, tickets.size());

		LOGGER.info("next call of 5 tickets");
		tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		LOGGER.info("next call of 10 tickets");
		tickets = ticketDAO.findPage(0, 10);
		assertEquals(5, tickets.size());
	}

	@Test
	public void testCachedGet() throws Exception {
		LOGGER.info("first call of ticket 4");
		Ticket t1 = ticketDAO.findById(4);
		assertNotNull(t1);

		LOGGER.info("next call of ticket 4");
		Ticket t2 = ticketDAO.findById(4);
		assertNotNull(t2);
		assertFalse(t1.equals(t2));

		LOGGER.info("next call of ticket 4");
		Ticket t3 = ticketDAO.findById(4);
		assertNotNull(t3);
		assertFalse(t1.equals(t3));
	}

	@Test
	public void testSaveBetweenCachedQuery() throws Exception {

		List<Ticket> tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		Ticket t = new Ticket();
		t.setAuthorName("me");
		t.setCreationDate(new Date());
		t.setMessage("New message");
		t.setTitle("New message");

		LOGGER.info("Merge 1 ticket");
		ticketDAO.merge(t);

		LOGGER.info("next call of 5 tickets");
		tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		LOGGER.info("next call of 5 tickets");
		tickets = ticketDAO.findPage(0, 5);
		assertEquals(5, tickets.size());

		boolean newTicketPresent = false;
		for(Ticket ti : tickets) {
			if(ti.getTitle().equals(t.getTitle())) newTicketPresent = true;
		}
		assertTrue("Le nouveau ticket n'a pas été trouvé ...", newTicketPresent);
	}

}
