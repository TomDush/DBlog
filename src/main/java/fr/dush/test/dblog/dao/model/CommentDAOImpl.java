package fr.dush.test.dblog.dao.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import fr.dush.test.dblog.dto.model.Comment;

@Repository
public class CommentDAOImpl implements ICommentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Comment findById(Integer id) {
		try {
			return (Comment) sessionFactory.getCurrentSession().load(Comment.class, id);
		} catch (HibernateObjectRetrievalFailureException e) {
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
		sessionFactory.getCurrentSession().delete(findById(commentId));
	}

}
