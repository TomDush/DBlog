package fr.dush.test.dblog.mock.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

/**
 * Classe permettant de simuler l'existance de quelques tickets afin que les tests aient du sens...
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class TicketDAOMock implements ITicketDAO {

	private final Map<Integer, Ticket> tickets = new HashMap<>();

	private int ticketId = 0;

	public TicketDAOMock() {
		merge(new Ticket(new Date(), "First title", "Ceci est un message cours", "me"));
		merge(new Ticket(new Date(), "Hello World !", "Bonjour à tous le monde ici présents !", "Dush"));
		merge(new Ticket(
				new Date(),
				"Et pourquoi on ne se retrouverai pas avec un titre assez long commme ça ?",
				"Excitavit hic ardor milites per municipia plurima, quae isdem conterminant, dispositos et castella, sed quisque serpentes latius pro viribus repellere moliens, nunc globis confertos, aliquotiens et dispersos multitudine superabatur ingenti, quae nata et educata inter editos recurvosque ambitus montium eos ut loca plana persultat et mollia, missilibus obvios eminus lacessens et ululatu truci perterrens.",
				"Dominus"));
		merge(new Ticket(new Date(), "Pas grand chose à dire", "Ici j'ai des\nretours<br/>à la ligne.", "Dush"));
		merge(new Ticket(
				new Date(),
				"Ca rend quoi tout ça ?",
				"Proinde concepta rabie saeviore, quam desperatio incendebat et fames, amplificatis viribus ardore incohibili in excidium urbium matris Seleuciae efferebantur, quam comes tuebatur Castricius tresque legiones bellicis sudoribus induratae.",
				"Dush"));
	}

	@Override
	public List<Ticket> findAll() {
		return new LinkedList<>(tickets.values());
	}

	@Override
	public List<Ticket> findPage(final int page, final int pageLength) {
		return new LinkedList<>(tickets.values());
	}

	@Override
	public Ticket findById(final Integer id) {
		return tickets.get(id);
	}

	@Override
	public void merge(final Ticket ticket) {
		if (ticket.getIdTicket() == null) {
			synchronized (this) {
				ticket.setIdTicket(ticketId++);
			}
		}

		tickets.put(ticket.getIdTicket(), ticket);
	}

	@Override
	public void delete(final Integer ticket) {
		tickets.remove(ticket);
	}

	@Override
	public long count() {
		return tickets.size();
	}

}
