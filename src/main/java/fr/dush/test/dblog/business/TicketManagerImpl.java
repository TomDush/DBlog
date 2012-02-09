package fr.dush.test.dblog.business;

import javax.inject.Inject;

import fr.dush.test.dblog.business.page.Page;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

public class TicketManagerImpl implements ITicketManager {

	@Inject
	private ITicketDAO ticketDAO;

	@Override
	public Page<Ticket> getTicketPage(int pageNumber, int pageSize) {
		Page<Ticket> page = new Page<Ticket>(pageSize, ticketDAO.count());
		page.setPageNumber(pageNumber);
		page.setElements(ticketDAO.findPage(pageNumber, pageSize));

		// TODO Rendre la collection de ticket paginée, par intercepteur
		// remplacer la collection prxyfoxifiée d'hibernate par une perso.

		return page;
	}

	/**
	 * Sauvegarde le ticket.
	 * @param ticket
	 */
	public void saveTicket(Ticket ticket) {
		ticketDAO.merge(ticket);
	}


}
