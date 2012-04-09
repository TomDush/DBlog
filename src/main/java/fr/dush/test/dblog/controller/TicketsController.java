package fr.dush.test.dblog.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.services.ITicketManager;

@Named("ticketsController")
@Scope("request")
public class TicketsController {

	@Inject
	private ITicketManager ticketManager;

	public List<Ticket> getTicketPage() {
		return ticketManager.getTicketPage(0, 5).getElements();
	}
}
