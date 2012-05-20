package fr.dush.test.dblog.dto.model;

import java.io.Serializable;

/**
 * Classe d'identifiant de {@link Score}.
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
public class ScoreId implements Serializable {

	private static final long serialVersionUID = -2009241719793929054L;

	private Integer ticket;

	private Integer user;

	public ScoreId() {
	}

	public ScoreId(Integer ticket, Integer user) {
		this.ticket = ticket;
		this.user = user;
	}

	public Integer getTicket() {
		return ticket;
	}

	public void setTicket(Integer ticket) {
		System.out.println("Set ticket : " + ticket);
		this.ticket = ticket;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		System.out.println("Set user : " + user);
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ticket == null ? 0 : ticket.hashCode());
		result = prime * result + (user == null ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ScoreId other = (ScoreId) obj;
		if (ticket == null) {
			if (other.ticket != null) return false;
		} else if (!ticket.equals(other.ticket)) return false;
		if (user == null) {
			if (other.user != null) return false;
		} else if (!user.equals(other.user)) return false;
		return true;
	}

}
