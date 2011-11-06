package fr.dush.test.dblog.dto.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Ticket {

	@Id
	@GeneratedValue
	@Column(name = "id_ticket")
	private Integer idTicket;

	/**
	 * Date de création du ticket.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date date;

	/**
	 * Titre du billet.
	 */
	@NotNull
	private String title;

	/**
	 * Contenu du billet
	 */
	private String message;

	/**
	 * Nom de l'auteur.
	 */
	@NotNull
	@Column(name = "author_name")
	private String authorName;

	/**
	 * Commentaires sur le billet.
	 */
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "ticket")
	@OrderBy(value = "date")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Comment> comments = new HashSet<>();

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", date=" + date + ", title=" + title + ", message=" + message + ", authorName="
				+ authorName + ", comments size=" + comments.size() + "]";
	}

}
