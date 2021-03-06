package fr.dush.test.dblog.services;

import fr.dush.test.dblog.dao.events.AutoCreationDate;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.services.page.Page;

public interface ITicketManager {

	public abstract Page<Ticket> getTicketPage(int pageNumber, int pageSize);

	public abstract AutoCreationDate findTicket(final int id);

	public abstract void saveTicket(final Ticket ticket);

}