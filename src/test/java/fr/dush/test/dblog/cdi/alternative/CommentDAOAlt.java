package fr.dush.test.dblog.cdi.alternative;

import java.util.List;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dto.model.Comment;

//@Named
//@Alternative
public class CommentDAOAlt implements ICommentDAO {

	@Override
	public List<Comment> findLast(int nb) {
		return null;
	}

	@Override
	public List<Comment> findByTicket(Integer ticket, int firstResult, int maxResults) {
		return null;
	}

	@Override
	public void merge(Comment comment) {

	}

	@Override
	public void delete(Integer coment) {

	}

	@Override
	public Comment findById(Integer id) {
		return null;
	}

	@Override
	public long countByTicket(Integer idTicket) {
		return idTicket / 2;
	}

}
