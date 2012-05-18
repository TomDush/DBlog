package fr.dush.test.dblog.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<Dto extends Serializable> {
	List<Dto> findAll();

	Dto findById(Serializable id);

	Dto merge(Dto ticket);

	void save(Dto ticket);

	void delete(Dto ticket);

	void deleteById(Serializable id);

	long count();
}
