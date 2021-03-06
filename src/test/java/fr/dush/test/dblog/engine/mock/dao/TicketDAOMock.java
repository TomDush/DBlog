package fr.dush.test.dblog.engine.mock.dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.dto.security.User;

/**
 * Classe permettant de simuler l'existance de quelques tickets afin que les tests aient du sens...
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class TicketDAOMock implements ITicketDAO {

	private final Map<Integer, Ticket> tickets = new HashMap<>();

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private int ticketId = 0;

	public TicketDAOMock() throws ParseException {

		merge(new Ticket(dateFormatter.parse("2012-12-21 12:21:12"), "FIN DU MONDE", "Les incas l'avaient prévu, la fin du monde aura lieu le 21/12/12 !", new User("Gaia", "gaia@terre.ga")));
		merge(new Ticket(new Date(), "Pas grand chose à dire", "Message à la date du jour.<br/>Ici j'ai des\nretours<br/>à la ligne.", new User("me", "me@gmail.com")));
		merge(new Ticket(dateFormatter.parse("2012-04-14 18:00:81"), "Hello World !", "Bonjour à tous le monde ici présent !", new User("Dush", "tomdush@gmail.com")));
		merge(new Ticket(
				dateFormatter.parse("2010-12-25 00:00:00"),
				"Et pourquoi on ne se retrouverai pas avec un titre assez long commme ça ?",
				"Proinde concepta rabie saeviore, quam desperatio incendebat et fames, amplificatis viribus ardore incohibili in excidium urbium matris Seleuciae efferebantur, quam comes tuebatur Castricius tresque legiones bellicis sudoribus induratae.",
				new User("Dush", "tomdush@gmail.com")));
		merge(new Ticket(
				dateFormatter.parse("0240-12-15 11:11:11"),
				"Texte latin",
				"Excitavit hic ardor milites per municipia plurima, quae isdem conterminant, dispositos et castella, sed quisque serpentes latius pro viribus repellere moliens, nunc globis confertos, aliquotiens et dispersos multitudine superabatur ingenti, quae nata et educata inter editos recurvosque ambitus montium eos ut loca plana persultat et mollia, missilibus obvios eminus lacessens et ululatu truci perterrens.",
				new User("Dominus", "dominus@rome.it")));
	}

	@Override
	public List<Ticket> findAll() {
		final List<Ticket> list = new LinkedList<>(tickets.values());
		Collections.sort(list, new Comparator<Ticket>() {

			@Override
			public int compare(Ticket date1, Ticket date2) {
				return date2.getCreationDate().compareTo(date1.getCreationDate());
			}
		});
		return list;
	}

	@Override
	public List<Ticket> findPage(final int page, final int pageLength) {
		final List<Ticket> list = new LinkedList<>(tickets.values());
		Collections.sort(list, new Comparator<Ticket>() {

			@Override
			public int compare(Ticket date1, Ticket date2) {
				return date2.getCreationDate().compareTo(date1.getCreationDate());
			}
		});
		return list;
	}

	@Override
	public Ticket findById(Serializable id) {
		return tickets.get(id);
	}

	@Override
	public Ticket merge(Ticket ticket) {
		if (ticket.getIdTicket() == null) {
			synchronized (this) {
				ticket.setIdTicket(ticketId++);
			}
		}

		tickets.put(ticket.getIdTicket(), ticket);

		return ticket;
	}

	@Override
	public void deleteById(Serializable ticket) {
		tickets.remove(ticket);
	}

	@Override
	public long count() {
		return tickets.size();
	}

	@Override
	public void delete(Ticket ticket) {
		deleteById(ticket.getIdTicket());
	}

	@Override
	public void save(Ticket ticket) {
		merge(ticket);
	}

}
