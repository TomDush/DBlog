package fr.dush.test.dblog.dao.model.hibernate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import fr.dush.test.dblog.dao.AbstractGenericDAOImpl;
import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dto.model.Comment;

@Named
@Transactional
@Scope("language")
public class CommentDAOImpl extends AbstractGenericDAOImpl<Comment> implements ICommentDAO {

	public CommentDAOImpl() {
		super(Comment.class, true);
	}

	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findLast(final int idTicket, final int nb) {
		final Query q = sessionFactory.getCurrentSession().createQuery("FROM Comment WHERE ticket.idTicket = :idTicket ORDER BY creationDate");
		q.setInteger("idTicket", idTicket);
		q.setMaxResults(nb);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByTicket(int ticket, final int firstResult, final int maxResult) {
		final Criteria c = sessionFactory.getCurrentSession().createCriteria(Comment.class);
		c.add(Restrictions.eq("ticket.idTicket", ticket)).addOrder(Order.desc("creationDate"));
		c.setFirstResult(firstResult).setMaxResults(maxResult);

		return c.list();
	}

	@Override
	public long countByTicket(int idTicket) {
		final Query q = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(c) FROM Comment c WHERE c.ticket.idTicket = :idTicket");
		q.setInteger("idTicket", idTicket);

		return (long) q.uniqueResult();
	}

}
