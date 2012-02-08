package fr.dush.test.dblog.business;

import fr.dush.test.dblog.business.page.Page;
import fr.dush.test.dblog.dto.model.Ticket;

public interface ITicketManager {

	public abstract Page<Ticket> getTicketPage(int pageNumber, int pageSize);

}