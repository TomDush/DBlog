package fr.dush.test.dblog.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.junit.AbstractJunitTest;
import fr.dush.test.dblog.junit.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = { "/bdd/tickets.xml" })
public class TicketDAOImplTest extends AbstractJunitTest {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketDAOImplTest.class);

	@Inject
	private ITicketDAO ticketDAO;

	@Test
	public void testRead() throws Exception {
		Ticket t = ticketDAO.findById(4);
		assertNotNull(t);
		assertEquals(new Integer(4), t.getIdTicket());
		assertEquals("Dush", t.getAuthorName());
		assertEquals("It's mine !", t.getMessage());
		assertEquals("Oct", t.getTitle());
		assertEquals(0, t.getComments().size());
		assertEquals("2011-10-08 00:00:00.0", t.getDate().toString());
	}

	@Test
	public void testCreate() throws Exception {
		logger.debug("---> testCreate()");

		int size = ticketDAO.findAll().size();

		Ticket t = new Ticket();
		t.setAuthorName("me");
		t.setDate(new Date());
		t.setMessage("J'ai rien a dire");
		t.setTitle("Mon super message");

		ticketDAO.merge(t);

		assertEquals(1, ticketDAO.findAll().size() - size);

		logger.debug("<---- testCreate()");
	}

	@Test
	public void testCount() {
		assertEquals(5, ticketDAO.count());
	}

	@Test
	public void readAll() {
		Collection<Ticket> tickets = ticketDAO.findAll();

		assertEquals(5, ticketDAO.findAll().size());
		for (Ticket t : tickets) {
			logger.debug("Ticket : {}", t);
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

}
