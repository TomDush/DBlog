package fr.dush.test.dblog.dao.model;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import fr.dush.test.dblog.annotations.DaoQualifier;
import fr.dush.test.dblog.dto.model.Comment;

@DaoQualifier
public class CommentDAOImpl implements ICommentDAO {

	@Inject
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Comment findById(Integer id) {
		try {
			return (Comment) sessionFactory.getCurrentSession().load(Comment.class, id);
		} catch (ObjectNotFoundException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findLast(int nb) {
		Query q = sessionFactory.getCurrentSession().createQuery("FROM Comment ORDER BY date");
		q.setMaxResults(nb);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByTicket(final Integer ticket, final int firstResult, final int maxResult) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(Comment.class);
		c.add(Restrictions.eq("ticket.idTicket", ticket)).addOrder(Order.desc("date"));
		c.setFirstResult(firstResult).setMaxResults(maxResult);

		return c.list();
	}

	@Override
	public long countByTicket(Integer idTicket) {
		Query q = sessionFactory.getCurrentSession().createQuery("SELECT COUNT(c) FROM Comment c WHERE c.ticket.idTicket = :idTicket");
		q.setInteger("idTicket", idTicket);

		return (long) q.uniqueResult();
	}

	@Override
	public void merge(Comment comment) {
		sessionFactory.getCurrentSession().merge(comment);
	}

	@Override
	public void delete(Integer commentId) {
		Comment c = findById(commentId);
		c.getTicket().getComments().remove(c);
		sessionFactory.getCurrentSession().delete(c);
	}

}
