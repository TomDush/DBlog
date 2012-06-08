package fr.dush.test.dblog.dao.model;

import java.util.List;

import fr.dush.test.dblog.dao.IGenericDAO;
import fr.dush.test.dblog.dto.model.Ticket;

public interface ITicketDAO extends IGenericDAO<Ticket> {
	List<Ticket> findPage(int page, int pageLength);
}
