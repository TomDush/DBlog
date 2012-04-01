package fr.dush.test.dblog.dao.context;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests unitaires comprenant l'injection de dépendance mais sans le mock des contextes (celui qui fait se confondre tous les contectes...).
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:WEB-INF/spring/context-global.xml",
		"classpath:WEB-INF/spring/context-persistence.xml",
		"classpath:WEB-INF/spring/context-scope.xml",
		"classpath:WEB-INF/spring/web-session-scopes.xml",
		"classpath:WEB-INF/spring/mock-controller.xml" })
public abstract class AbstractSpringJunitTest {

}
