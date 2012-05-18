package fr.dush.test.dblog.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractGenericDAOImpl<Dto extends Serializable> implements IGenericDAO<Dto> {

	@Inject
	protected SessionFactory sessionFactory;

	private Class<Dto> clazz;

	private boolean cacheable;

	public AbstractGenericDAOImpl(Class<Dto> clazz) {
		this.clazz = clazz;
	}

	public AbstractGenericDAOImpl(Class<Dto> clazz, boolean cacheable) {
		this.clazz = clazz;
		this.cacheable = cacheable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dto> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(clazz).setCacheable(cacheable).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dto findById(Serializable id) {
		return (Dto) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dto merge(Dto dto) {
		return (Dto) sessionFactory.getCurrentSession().merge(dto);
	}

	@Override
	public void delete(Dto dto) {
		sessionFactory.getCurrentSession().delete(dto);
	}

	@Override
	public void deleteById(Serializable id) {
		delete(findById(id));
	}

	@Override
	public long count() {
		return (long) sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM " + clazz.getName()).uniqueResult();
	}

	@Override
	public void save(Dto dto) {
		sessionFactory.getCurrentSession().saveOrUpdate(dto);
	}

}
