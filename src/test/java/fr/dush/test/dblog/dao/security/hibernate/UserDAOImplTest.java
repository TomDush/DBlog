package fr.dush.test.dblog.dao.security.hibernate;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dao.security.IUserDAO;
import fr.dush.test.dblog.dto.security.User;
import fr.dush.test.dblog.engine.AbstractJunitTest;
import fr.dush.test.dblog.engine.dbunitapi.DatabaseScripts;

@DatabaseScripts(locations = "/bdd/users.xml")
public class UserDAOImplTest extends AbstractJunitTest {

	@Inject
	private IUserDAO userDAO;

	@Test
	public void testFindByIdentifier() {
		User user = userDAO.findByIdentifier("dush", "dushpass");
		assertNotNull(user);

		assertEquals(new Integer(2), user.getUserId());
		assertEquals("Dush", user.getLogin());
		assertEquals("dushpass", user.getPassword());
		assertEquals("tomdush@gmail.com", user.getEmail());
	}

	@Test
	public void testFindByEmail() {
		User user = userDAO.findByEmail("tomdush@gmail.com");
		assertNotNull(user);

		assertEquals(new Integer(2), user.getUserId());
		assertEquals("Dush", user.getLogin());
		assertEquals("dushpass", user.getPassword());
		assertEquals("tomdush@gmail.com", user.getEmail());
	}

	@Test
	public void testFindById() {
		User you = userDAO.findById(2);
		assertNotNull(you);

		assertEquals("Dush", you.getLogin());
		assertEquals("dushpass", you.getPassword());
		assertEquals("tomdush@gmail.com", you.getEmail());
	}

	@Test
	@DatabaseScripts(inheritLocations = false)
	public void testSave() {
		User u = new User();
		u.setLogin("you");
		u.setPassword("youPass");
		u.setEmail("you@gmail.com");

		userDAO.save(u);
		assertNotNull(u.getUserId());

		User you = userDAO.findById(u.getUserId());
		assertNotNull(you);

		assertEquals("you", you.getLogin());
		assertEquals("youPass", you.getPassword());
		assertEquals("you@gmail.com", you.getEmail());

	}

}
