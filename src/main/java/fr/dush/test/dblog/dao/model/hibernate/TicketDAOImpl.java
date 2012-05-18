package fr.dush.test.dblog.dao.model.hibernate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import fr.dush.test.dblog.dao.AbstractGenericDAOImpl;
import fr.dush.test.dblog.dao.model.ITicketDAO;
import fr.dush.test.dblog.dto.model.Ticket;

@Named
@Transactional
@Scope("language")
public class TicketDAOImpl extends AbstractGenericDAOImpl<Ticket> implements ITicketDAO {

	public TicketDAOImpl() {
		super(Ticket.class, true);
	}

	@Inject
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> findPage(int firstResult, int maxResults) {
		final Criteria c = sessionFactory.getCurrentSession().createCriteria(Ticket.class);
		c.addOrder(Order.desc("creationDate"));
		c.setMaxResults(maxResults).setFirstResult(firstResult);
		c.setCacheable(true);

		return c.list();
	}

}
