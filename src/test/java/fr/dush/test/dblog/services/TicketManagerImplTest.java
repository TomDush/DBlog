package fr.dush.test.dblog.services;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.services.page.Page;

// FIXME Cette classe est totalmement inutile...
@ContextConfiguration(locations = { "classpath:WEB-INF/spring/mock-dao.xml"})
public class TicketManagerImplTest extends AbstractJunitTest {

	@Inject
	private ITicketDAO ticketDAOMock;

	@Inject
	private ITicketManager ticketManager;

	@Before
	public void addTickets() {
		ticketDAOMock.merge(generateTicket(2));
		ticketDAOMock.merge(generateTicket(3));
		ticketDAOMock.merge(generateTicket(4));
	}

	@After
	public void deleteCreatedTickets() {
		ticketDAOMock.delete(1);
	}

	private Ticket generateTicket(final int id) {
		final Ticket t = new Ticket();
		t.setIdTicket(id);

		return t;
	}

	@Test
	public void testFindTicket() throws Exception {
		assertNull(ticketManager.findTicket(12));
		assertNotNull(ticketManager.findTicket(2));
		assertNotNull(ticketManager.findTicket(3));
	}

	@Test
	public void testGetTicketPage() throws Exception {
		final Page<Ticket> p = ticketManager.getTicketPage(0, 3);
		assertNotNull(p);
		assertEquals(3, p.getElementsSize());
	}

	@Test
	public void testSaveTicket() throws Exception {
		ticketManager.saveTicket(generateTicket(1));
		assertNotNull(ticketDAOMock.findById(1));
	}

}
