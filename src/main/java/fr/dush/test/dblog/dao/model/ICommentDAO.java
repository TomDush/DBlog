package fr.dush.test.dblog.dao.model;

import java.util.List;

import fr.dush.test.dblog.dto.model.Comment;

public interface ICommentDAO {

	List<Comment> findLast(int nb);

	List<Comment> findByTicket(Integer ticket, int firstResult, int maxResults);

	void merge(Comment comment);

	void delete(Integer coment);

	Comment findById(Integer id);

	long countByTicket(Integer idTicket);
}
