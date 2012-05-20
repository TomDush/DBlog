package fr.dush.test.dblog.services;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.test.dblog.dto.model.Comment;
import fr.dush.test.dblog.dto.model.Ticket;
import fr.dush.test.dblog.dto.security.User;
import fr.dush.test.dblog.engine.AbstractSimpleSpringJunitTest;

public class ValidatorTest extends AbstractSimpleSpringJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorTest.class);

	@Test
	public void testValidator() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Ticket t = new Ticket();
		// PAS de nom d'auteur (obligatoire) : t.setAuthorName("me");
		t.setCreationDate(new Date());
		t.setMessage("New message");
		t.setTitle("New message");

		Set<ConstraintViolation<Ticket>> violations = validator.validate(t);
		assertNotNull(violations);
		assertEquals(1, violations.size());
		LOGGER.info("Ticket's errors : {}", violations);
	}

	@Test
	public void testRecursiveValidator() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Ticket t = new Ticket();
		t.setAuthor(new User("Me", "me.dblog.fr", "pass")); // email invalid
		t.setCreationDate(new Date());
		t.setMessage(" "); // blank
		t.setTitle("New message");

		t.getComments().add(new Comment(new User("Him", "me.dblog.fr"), t, "useless")); // user non validé
		t.getComments().add(new Comment(new User("You", "me@dblog.fr"), t, " ")); // comment blank...

		Set<ConstraintViolation<Ticket>> violations = validator.validate(t);
		assertNotNull(violations);
		LOGGER.info("{} ticket's errors : {}", violations.size(), violations);

		assertEquals(3, violations.size());

		boolean authorEmail = false;
		boolean message = false;
		boolean comments = false;
		boolean unexepted = false;
		for(ConstraintViolation<Ticket> violation : violations) {
			if("author.email".equals(violation.getPropertyPath().toString())) {
				authorEmail = true;
				User user = (User) violation.getLeafBean();
				assertEquals("Me", user.getLogin());
				assertEquals("{fr.dush.dblog.invalidemail.message}", violation.getMessageTemplate());
				assertEquals("T'es un gros NAZ : email n'est pas correct.", violation.getMessage());
			} else if("message".equals(violation.getPropertyPath().toString())) {
				message = true;
			} else if("comments[].message".equals(violation.getPropertyPath().toString())) {
				comments = true;
			} else {
				LOGGER.warn("Unexpected error : {}", violation);
				unexepted = true;
			}
		}

		assertTrue(authorEmail);
		assertTrue(message);
		assertTrue(comments);
		assertFalse(unexepted);
	}
}
