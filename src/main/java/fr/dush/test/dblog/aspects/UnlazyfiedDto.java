package fr.dush.test.dblog.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.test.dblog.dto.model.Ticket;

/**
 * Délazyfie un objet DTO de ses collection hibernate.
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
//@Named
@Aspect
public class UnlazyfiedDto {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnlazyfiedDto.class);

	public UnlazyfiedDto() {
		super();
		LOGGER.warn("UnlazyfiedDto contructor");
	}

	@AfterReturning(pointcut = "execution(* fr.dush.test.dblog.dao.model.hibernate.TicketDAOImpl.*(..))", returning = "object")
	public void delazyfiedDto(Object object) {
		if (object instanceof Ticket) {
			Ticket t = (Ticket) object;
			Hibernate.initialize(t);
			t.setMessage("Hacked message : " + t.getMessage());
			LOGGER.info("{} is initialized !", object);
		} else {
			LOGGER.error("Object {} is not a Ticket...", object);
		}

	}

	@AfterReturning("execution(* fr.dush.test.dblog.dao.model.hibernate.TicketDAOImpl.*(..))")
	public void logAfter() {
		LOGGER.warn("Hello world !");
	}

}
