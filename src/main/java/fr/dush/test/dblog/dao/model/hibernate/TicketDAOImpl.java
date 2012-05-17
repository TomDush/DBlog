package fr.dush.test.dblog.dao.model.hibernate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

@Named
@Transactional
@Scope("language")
public class TicketDAOImpl implements ITicketDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(TicketDAOImpl.class);

	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ticket> findAll() {
		@SuppressWarnings("unchecked")
		final
		List<Ticket> tickets = sessionFactory.getCurrentSession().createQuery("FROM Ticket ORDER BY creationDate").list();

		LOGGER.debug("{} ticket(s) found", tickets.size());

		return tickets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> findPage(int firstResult, int maxResults) {
		final Criteria c = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
		c.addOrder(Order.desc("creationDate"));
		c.setMaxResults(maxResults).setFirstResult(firstResult);
		c.setCacheable(true);

		return c.list();
	}

	@Override
	public Ticket findById(Integer id) {
		return (Ticket) sessionFactory.getCurrentSession().get(Ticket.class, id);
	}

	@Override
	public void merge(Ticket ticket) {
		LOGGER.debug("Save ticket : {}", ticket);
		sessionFactory.getCurrentSession().merge(ticket);
	}

	@Override
	public void delete(Integer ticket) {
		sessionFactory.getCurrentSession().delete(findById(ticket));
	}

	@Override
	public long count() {
		return (long) sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM Ticket t").uniqueResult();
	}

}
