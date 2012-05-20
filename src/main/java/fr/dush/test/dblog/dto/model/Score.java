package fr.dush.test.dblog.dto.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Range;

import com.sun.istack.internal.NotNull;

import fr.dush.test.dblog.dto.security.User;

@Entity
@IdClass(ScoreId.class)
public class Score implements Serializable {

	private static final long serialVersionUID = -2844640061241427620L;

	private Ticket ticket;

	private User user;

	private int score;

	@Id
	@ManyToOne(cascade = {})
	@JoinColumn(name = "ticket_id", insertable = false, updatable = false)
//	@JoinColumn(name = "ticket_id")
	@Cascade({})
	@NotNull
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Id
	@ManyToOne(cascade = {})
	@Cascade({})
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
//	@JoinColumn(name = "user_id")
	@NotNull
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Range(min = 0, max = 10)
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
