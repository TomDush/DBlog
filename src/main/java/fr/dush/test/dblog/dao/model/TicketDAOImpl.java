package fr.dush.test.dblog.dao.model;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import fr.dush.test.dblog.dto.model.Ticket;

@Repository
public class TicketDAOImpl implements ITicketDAO {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Ticket> findAll() {
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = getHibernateTemplate().find("FROM Ticket ORDER BY date");

		logger.debug("{} ticket(s) found", tickets.size());

		return tickets;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> findPage(final int firstResult, final int maxResults) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Ticket>>() {

			@Override
			public List<Ticket> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Ticket.class).addOrder(Order.desc("date")).setFirstResult(firstResult)
						.setMaxResults(maxResults).list();
			}

		});
	}

	@Override
	public Ticket findById(Integer id) {
		try {
			return getHibernateTemplate().load(Ticket.class, id);
		} catch (HibernateObjectRetrievalFailureException e) {
			return null;
		}
	}

	@Override
	public void merge(Ticket ticket) {
		logger.debug("Save ticket : {}", ticket);
		getHibernateTemplate().merge(ticket);
	}

	@Override
	public void delete(Integer ticket) {
		getHibernateTemplate().delete(findById(ticket));
	}

	@Override
	public long count() {
		return (long) getHibernateTemplate().find("SELECT COUNT(t) FROM Ticket t").get(0);
	}

}
