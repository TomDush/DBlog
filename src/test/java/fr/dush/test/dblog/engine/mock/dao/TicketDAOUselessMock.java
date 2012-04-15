package fr.dush.test.dblog.engine.mock.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

public class TicketDAOUselessMock implements ITicketDAO {

	private Map<Integer, Ticket> tickets = new HashMap<>();

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
