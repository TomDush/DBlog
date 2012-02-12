package fr.dush.test.dblog.dto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Comment {

	@Id
	@GeneratedValue
	@Column(name = "id_comment")
	private Integer idComment;

	@NotNull
	@Column(name = "author_name")
	private String authorName;

	@ManyToOne
	@NotNull
	//@Column(name = "id_ticket")
	//@LazyToOne(LazyToOneOption.FALSE)
	private Ticket ticket;

	@NotNull
	private Date date;

	private String message;

	public Integer getIdComment() {
		return idComment;
	}

	public void setIdComment(final Integer idComment) {
		this.idComment = idComment;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(final Ticket ticket) {
		this.ticket = ticket;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Comment [idComment=" + idComment + ", authorName=" + authorName + ", ticket=" + ticket + ", date=" + date + ", message="
				+ message + "]";
	}

}
