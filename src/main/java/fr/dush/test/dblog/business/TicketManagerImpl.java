package fr.dush.test.dblog.business;

import javax.inject.Inject;

import fr.dush.test.dblog.business.page.Page;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

public class TicketManagerImpl implements ITicketManager {

	@Inject
	private ITicketDAO ticketDAO;

	/* (non-Javadoc)
	 * @see fr.dush.test.dblog.business.ITicketManager#getTicketPage(int, int)
	 */
	@Override
	public Page<Ticket> getTicketPage(int pageNumber, int pageSize) {
		Page<Ticket> page = new Page<Ticket>(pageSize, ticketDAO.count());
		page.setPageNumber(pageNumber);
		page.setElements(ticketDAO.findPage(pageNumber, pageSize));

		return page;
	}
}
