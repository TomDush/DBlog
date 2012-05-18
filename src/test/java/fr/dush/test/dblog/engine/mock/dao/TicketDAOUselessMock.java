package fr.dush.test.dblog.engine.mock.dao;

import java.io.Serializable;
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
	public Ticket findById(Serializable id) {
		return tickets.get(id);
	}

	@Override
	public Ticket merge(Ticket ticket) {
		tickets.put(ticket.getIdTicket(), ticket);
		return ticket;
	}

	@Override
	public void delete(Ticket ticket) {
		tickets.remove(ticket.getIdTicket());
	}

	@Override
	public long count() {
		return tickets.size();
	}

	@Override
	public void deleteById(Serializable id) {
		tickets.remove(id);
	}

	@Override
	public void save(Ticket ticket) {
		merge(ticket);
	}

}
