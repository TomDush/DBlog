package fr.dush.test.dblog.aspects;

import javax.inject.Inject;

import org.aspectj.lang.annotation.Aspect;

import fr.dush.test.dblog.dao.model.ICommentDAO;
import fr.dush.test.dblog.dto.model.Comment;
import fr.dush.test.dblog.dto.model.Ticket;

/**
 * Change la collection présente dans les objects ticket pour que seuls les derniers commentaires soient chargés. TODO faire que ces
 * chargements soient lazyfifés.
 *
 * TODO intercepter les execption hibernate.
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
@Aspect
public class CommentPagination {

	@Inject
	private ICommentDAO commentDAO;

	public void setCommentDAO(final ICommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	/**
	 * Change la collection de tickets pour en avoir une en sens inverse et limitée à 5.
	 */
//	@AfterReturning(pointcut = "fr.dush.test.dblog.services.getTicketPage(..)", returning = "retVal")
	public void replaceCommentsCollection(final Object retVal) {
		if (retVal instanceof Ticket) {
			final Ticket t = (Ticket) retVal;

			final SizedLinkedList<Comment> comms = new SizedLinkedList<>(commentDAO.findLast(t.getIdTicket(), 5));
			comms.setSize((int) commentDAO.countByTicket(t.getIdTicket()));

			t.setComments(comms);
		}
	}

}
