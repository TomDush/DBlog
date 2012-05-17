package fr.dush.test.dblog.dao.model.hibernate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dto.model.Comment;

@Named
@Transactional
@Scope("language")
public class CommentDAOImpl implements ICommentDAO {

	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Comment findById(final Integer id) {
		try {
			return (Comment) sessionFactory.getCurrentSession().load(Comment.class, id);
		} catch (final ObjectNotFoundException e) {
			return null;
		}
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
	public List<Comment> findByTicket(final Integer ticket, final int firstResult, final int maxResult) {
		final Criteria c = sessionFactory.getCurrentSession().createCriteria(Comment.class);
		c.add(Restrictions.eq("ticket.idTicket", ticket)).addOrder(Order.desc("date"));
		c.setFirstResult(firstResult).setMaxResults(maxResult);

		return c.list();
	}

	@Override
	public long countByTicket(final Integer idTicket) {
		final Query q = sessionFactory.getCurrentSession()
				.createQuery("SELECT COUNT(c) FROM Comment c WHERE c.ticket.idTicket = :idTicket");
		q.setInteger("idTicket", idTicket);

		return (long) q.uniqueResult();
	}

	@Override
	public void merge(final Comment comment) {
		sessionFactory.getCurrentSession().merge(comment);
	}

	@Override
	public void delete(final Integer commentId) {
		final Comment c = findById(commentId);
		c.getTicket().getComments().remove(c);
		sessionFactory.getCurrentSession().delete(c);
	}

}
