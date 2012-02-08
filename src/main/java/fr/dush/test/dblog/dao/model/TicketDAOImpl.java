package fr.dush.test.dblog.dao.model;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import fr.dush.test.dblog.dto.model.Ticket;

@Named
@Transactional
public class TicketDAOImpl implements ITicketDAO {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketDAOImpl.class);

	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ticket> findAll() {
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = sessionFactory.getCurrentSession().createQuery("FROM Ticket ORDER BY date").list();

		logger.debug("{} ticket(s) found", tickets.size());

		return tickets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> findPage(final int firstResult, final int maxResults) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
		c.addOrder(Order.desc("date"));
		c.setMaxResults(maxResults).setFirstResult(firstResult);

		return c.list();
	}

	@Override
	public Ticket findById(Integer id) {
		return (Ticket) sessionFactory.getCurrentSession().get(Ticket.class, id);
	}

	@Override
	public void merge(Ticket ticket) {
		logger.debug("Save ticket : {}", ticket);
		sessionFactory.getCurrentSession().merge(ticket);
	}

	@Override
	public void delete(Integer ticket) {
		sessionFactory.getCurrentSession().delete(findById(ticket));
	}

	@Override
	public long count() {
		return (long) sessionFactory.getCurrentSession().createQuery("SELECT COUNT(t) FROM Ticket t").uniqueResult();
	}

}
