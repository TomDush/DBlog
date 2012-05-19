package fr.dush.test.dblog.dao.security.hibernate;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;

import fr.dush.test.dblog.dao.AbstractGenericDAOImpl;
import fr.dush.test.dblog.dao.security.IUserDAO;
import fr.dush.test.dblog.dto.security.User;

@Named
@Scope("language")
public class UserDAOImpl extends AbstractGenericDAOImpl<User> implements IUserDAO {

	public UserDAOImpl() {
		super(User.class);
	}

	@Override
	public User findByIdentifier(String login, String password) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
		c.add(Restrictions.eq("login", login).ignoreCase());
		c.add(Restrictions.eq("password", password));

		return (User) c.uniqueResult();
	}

	@Override
	public User findByEmail(String email) {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
		c.add(Restrictions.eq("email", email));

		return (User) c.uniqueResult();
	}

}
