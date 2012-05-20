package fr.dush.test.dblog.dto.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import fr.dush.test.dblog.dao.events.AutoCreationDate;
import fr.dush.test.dblog.dto.security.User;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ticket implements AutoCreationDate, Serializable {

	private static final long serialVersionUID = -4065056584175302382L;

	private Integer idTicket;

	/**
	 * Date de création du ticket.
	 */
	private Date creationDate;

	/**
	 * Date de création du ticket.
	 */
	private Date lastUpdate;

	/**
	 * Titre du billet.
	 */
	private String title;

	/**
	 * Contenu du billet
	 */
	private String message;

	/**
	 * Nom de l'auteur.
	 */
	private User author;

	/**
	 * Commentaires sur le billet.
	 */
	private Collection<Comment> comments = new HashSet<>();

	public Ticket() {
	}

	public Ticket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public Ticket(Date date, String title, String message, User user) {
		this.creationDate = date;
		this.title = title;
		this.message = message;
		this.author = user;
	}

	@Id
	@GeneratedValue
	@Column(name = "id_ticket")
	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(final Integer idTicket) {
		this.idTicket = idTicket;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(final Date date) {
		this.creationDate = date;
	}

	@Column(name = "last_update")
	// @Version // Pose problème quand on sauvegarde qu'un commentaire.
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "ticket", fetch = FetchType.EAGER)
	@OrderBy(value = "creationDate")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Valid
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Transient
	public String getAuthorName() {
		return author == null ? "" : author.getLogin();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	@NotNull
	@Valid
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = user;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", date=" + creationDate + ", title=" + title + ", message=" + message + ", autor login=" + getAuthorName()
				+ ", comments size=" + comments.size() + "]";
	}

}
