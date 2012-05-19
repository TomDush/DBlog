package fr.dush.test.dblog.dao.security;

import fr.dush.test.dblog.dao.IGenericDAO;
import fr.dush.test.dblog.dto.security.User;

public interface IUserDAO extends IGenericDAO<User> {

	/**
	 * Retrouve l'utilisateur avec ses identifiant (login/password)
	 *
	 * @param login Nom d'utilisateur (case insensitive)
	 * @param password Mot de passe au format tel qu'il est en base de données
	 * @return L'utilisateur ou nul.
	 */
	User findByIdentifier(String login, String password);

	/**
	 * Retrouve l'utilisateur via son email. Les email sont unique, il ne peut donc y avoir qu'un seul utilisateur qui réponde.
	 *
	 * @param email
	 * @return L'utilisateur, ou NULL.
	 */
	User findByEmail(String email);
}
