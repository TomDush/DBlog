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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import fr.dush.test.dblog.dao.events.AutoCreationDate;


@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ticket implements AutoCreationDate, Serializable {

	private static final long serialVersionUID = -4065056584175302382L;

	@Id
	@GeneratedValue
	@Column(name = "id_ticket")
	private Integer idTicket;

	/**
	 * Date de création du ticket.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	/**
	 * Date de création du ticket.
	 */
//	@Version // Pose problème quand on sauvegarde qu'un commentaire.
	@Column(name = "last_update")
	private Date lastUpdate;

	/**
	 * Titre du billet.
	 */
	@NotNull
	private String title;

	/**
	 * Contenu du billet
	 */
	@NotBlank
	private String message;

	/**
	 * Nom de l'auteur.
	 */
	@NotBlank
	@Column(name = "author_name")
	private String authorName;

	/**
	 * Commentaires sur le billet.
	 */
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "ticket", fetch = FetchType.EAGER)
	@OrderBy(value = "creationDate")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Valid
	private Collection<Comment> comments = new HashSet<>();

	public Ticket() {
	}

	public Ticket(Date date, String title, String message, String authorName) {
		this.creationDate = date;
		this.title = title;
		this.message = message;
		this.authorName = authorName;
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(final Integer idTicket) {
		this.idTicket = idTicket;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(final Date date) {
		this.creationDate = date;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Ticket [idTicket=" + idTicket + ", date=" + creationDate + ", title=" + title + ", message=" + message + ", authorName="
				+ authorName + ", comments size=" + comments.size() + "]";
	}

}
