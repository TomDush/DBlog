package fr.dush.test.dblog.services;

import javax.inject.Inject;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.services.page.Page;

public class TicketManagerImpl implements ITicketManager {

	@Inject
	private ITicketDAO ticketDAO;

	@Override
	public Page<Ticket> getTicketPage(final int pageNumber, final int pageSize) {
		final Page<Ticket> page = new Page<Ticket>(pageSize, ticketDAO.count());
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
	@Override
	public void saveTicket(final Ticket ticket) {
		ticketDAO.merge(ticket);
	}

	@Override
	public void findTicket(final int id) {
		ticketDAO.findById(id);
	}


}
