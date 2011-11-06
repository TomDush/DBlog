package fr.dush.test.dblog.dao.model;

import java.beans.ConstructorProperties;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import fr.dush.test.dblog.dto.model.Comment;

@Repository
public class CommentDAOImpl extends HibernateDaoSupport implements ICommentDAO {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CommentDAOImpl.class);

	@Autowired
	@ConstructorProperties(value = { "sessionFactory" })
	public CommentDAOImpl(SessionFactory sessionFactory) {
		logger.debug("CommentDAOImpl with session factory : {}", sessionFactory);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Comment findById(Integer id) {
		try {
			return getHibernateTemplate().load(Comment.class, id);
		} catch (HibernateObjectRetrievalFailureException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findLast(int nb) {
		getHibernateTemplate().setMaxResults(nb);
		return getHibernateTemplate().find("FROM Comment ORDER BY date");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByTicket(final Integer ticket, final int firstResult, final int maxResult) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Comment>>() {

			@Override
			public List<Comment> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Comment.class).add(Restrictions.eq("ticket.idTicket", ticket)).addOrder(Order.desc("date"))
						.setFirstResult(firstResult).setMaxResults(maxResult).list();
			}

		});
	}

	@Override
	public long countByTicket(Integer idTicket) {
		String query = "SELECT COUNT(c) FROM Comment c WHERE c.ticket.idTicket = :idTicket";
		return (long) getHibernateTemplate().findByNamedParam(query, "idTicket", idTicket).get(0);
	}

	@Override
	public void merge(Comment comment) {
		getHibernateTemplate().merge(comment);
	}

	@Override
	public void delete(Integer commentId) {
		getHibernateTemplate().delete(findById(commentId));
	}

}
