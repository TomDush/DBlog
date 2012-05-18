package fr.dush.test.dblog.dao.model;

import java.util.List;

import fr.dush.test.dblog.dao.IGenericDAO;
import fr.dush.test.dblog.dto.model.Comment;

public interface ICommentDAO extends IGenericDAO<Comment> {

	List<Comment> findLast(int ticketId, int nb);

	List<Comment> findByTicket(int ticket, int firstResult, int maxResults);

	long countByTicket(int idTicket);
}
