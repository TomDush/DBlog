package fr.dush.test.dblog.aspects;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;
import fr.dush.test.dblog.services.ITicketManager;

@DatabaseScripts(locations = { "/bdd/tickets.xml" })
@ContextConfiguration(locations = { "classpath:WEB-INF/spring/context-aop.xml" })
public class UnlazyfiedDtoTest extends AbstractJunitTest {

	@Inject
	private ITicketDAO ticketDAO;

	@Inject
	private ITicketManager ticketManager;

	@Test
	public void testAMethod() throws Exception {
		final Ticket t = ticketDAO.findById(4);

		ticketDAO.findPage(0, 12);
		ticketDAO.findPage(0, 12);
		ticketManager.getTicketPage(0, 12);
		ticketManager.getTicketPage(0, 12);
		ticketManager.getTicketPage(0, 12);

		assertTrue(t.getMessage() + " not begin with Hacked.", t.getMessage().startsWith("Hacked"));

	}

}
