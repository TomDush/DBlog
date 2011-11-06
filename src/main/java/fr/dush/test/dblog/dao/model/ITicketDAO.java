package fr.dush.test.dblog.dao.model;

import java.util.List;

import fr.dush.test.dblog.dto.model.Ticket;

public interface ITicketDAO {
	List<Ticket> findAll();

	List<Ticket> findPage(int page, int pageLength);

	Ticket findById(Integer id);

	void merge(Ticket ticket);

	void delete(Integer ticket);

	long count();
}
