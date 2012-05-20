package fr.dush.test.dblog.dao.model.hibernate;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.dao.AbstractGenericDAOImpl;
import fr.dush.test.dblog.dao.model.IScoreDAO;
import fr.dush.test.dblog.dto.model.Score;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.dto.security.User;

@Named
@Scope("language")
public class ScoreDAOImpl extends AbstractGenericDAOImpl<Score> implements IScoreDAO {

	public ScoreDAOImpl() {
		super(Score.class);
	}

	@Override
	public double calculScore(Ticket ticket) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT AVG(score) FROM Score WHERE ticket = :ticket");
		query.setParameter("ticket", ticket);

		Object result = query.uniqueResult();
		return result == null ? new Double(0) : (double) result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Score> findByTicket(Ticket ticket) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Score.class);
		criteria.add(Restrictions.eq("ticket", ticket));

		return criteria.list();
	}

	@Override
	public void save(Score score) {
		Ticket t = (Ticket) sessionFactory.getCurrentSession().merge(score.getTicket());
		User u = (User) sessionFactory.getCurrentSession().merge(score.getUser());

		score.setTicket(t);
		score.setUser(u);

		super.save(score);
	}

}
