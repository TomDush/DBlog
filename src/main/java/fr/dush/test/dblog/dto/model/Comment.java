package fr.dush.test.dblog.dto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;

import fr.dush.test.dblog.dto.security.User;


@Entity
@Proxy(lazy = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

	private static final long serialVersionUID = -6763893228021416944L;

	private Integer idComment;

	private User author;

	private Ticket ticket;

	private Date creationDate;

	private String message;

	@Id
	@GeneratedValue
	@Column(name = "id_comment")
	public Integer getIdComment() {
		return idComment;
	}

	public void setIdComment(final Integer idComment) {
		this.idComment = idComment;
	}

	@ManyToOne
	@JoinColumn(name = "id_ticket")
	@NotNull
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(final Ticket ticket) {
		this.ticket = ticket;
	}

	@Column(name = "creation_date")
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final Date date) {
		this.creationDate = date;
	}

	@NotBlank
	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Transient
	public String getAuthorName() {
		return author == null ? "" : author.getLogin();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = user;
	}

	@Override
	public String toString() {
		return "Comment [idComment=" + idComment + ", authorName=" + getAuthorName() + ", ticket=" + ticket + ", date=" + creationDate + ", message=" + message + "]";
	}

}
