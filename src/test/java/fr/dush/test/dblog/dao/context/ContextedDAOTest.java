package fr.dush.test.dblog.dao.context;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.engine.AbstractSimpleSpringJunitTest;
import fr.dush.test.dblog.engine.context.ContextedThread;

/**
 * Test les DAO dans le contexte <i>language</i>.
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */

public class ContextedDAOTest extends AbstractSimpleSpringJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextedDAOTest.class);

	@Inject
	private ApplicationContext context;

	@Test
	public void testReadSave() throws Throwable {

		// ** Sauvegarde sur 2 contextes
		final SaverThread frSaverThread = new SaverThread(context, new Locale("fr"), "Bonjour");
		final SaverThread enSaverThread = new SaverThread(context, new Locale("en"), "Hello");

		frSaverThread.startAndWait();
		frSaverThread.throwError();
		enSaverThread.startAndWait();
		enSaverThread.throwError();

		// ** Lecture sur 2 contextes
		final ReaderThread frReaderThread = new ReaderThread(context, new Locale("fr"), "Bonjour");
		final ReaderThread enReaderThread = new ReaderThread(context, new Locale("en"), "Hello");

		frReaderThread.startAndWait();
		frReaderThread.throwError();
		enReaderThread.startAndWait();
		enReaderThread.throwError();

		System.out.println("OK.");
	}

	public class SaverThread extends ContextedThread {

		private final String ticketTitle;

		public SaverThread(ApplicationContext context, Locale locale, String ticketTitle) {
			super(context, locale);
			this.ticketTitle = ticketTitle;
		}

		@Override
		public void test() {
			final ITicketDAO ticketDAO = getTicketDAO();

			final Ticket t = new Ticket();
			t.setCreationDate(new Date());
			t.setTitle(ticketTitle);
			t.setMessage(ticketTitle);
			t.setAuthorName(ticketTitle);

			ticketDAO.merge(t);
			LOGGER.info("Ticket {} saved on locale {}", t, locale);
		}

	}

	public class ReaderThread extends ContextedThread {

		private final String ticketTitle;

		public ReaderThread(ApplicationContext context, Locale locale, String ticketTitle) {
			super(context, locale);
			this.ticketTitle = ticketTitle;
		}

		@Override
		public void test() {
			final ITicketDAO ticketDAO = getTicketDAO();

			final List<Ticket> tickets = ticketDAO.findAll();

			LOGGER.info("Reading {} ticket(s) on locale {}", tickets.size(), locale);
			assertEquals(1, tickets.size());
			assertEquals(ticketTitle, tickets.get(0).getTitle());
		}

	}

}
