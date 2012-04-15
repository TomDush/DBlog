package fr.dush.test.dblog.services;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.services.page.Page;

@Named
@Scope("language")
public class TicketManagerImpl implements ITicketManager {

	@Inject
	private ITicketDAO ticketDAO;

	@Override
	public Page<Ticket> getTicketPage(final int pageNumber, final int pageSize) {
		final Page<Ticket> page = new Page<Ticket>(pageSize, ticketDAO.count());
		page.setPageNumber(pageNumber);
		page.setElements(ticketDAO.findPage(pageNumber, pageSize));

		// TODO Rendre les collections de ticket paginées, par intercepteur
		// remplacer la collection prxyfoxifiée d'hibernate par une perso.

		return page;
	}

	/**
	 * Sauvegarde le ticket.
	 * @param ticket
	 */
	@Override
	public void saveTicket(final Ticket ticket) {
		// TODO protéger (bbcode ?) les champs de texte.
		ticketDAO.merge(ticket);
	}

	@Override
	public Ticket findTicket(final int id) {
		return ticketDAO.findById(id);
	}


}
