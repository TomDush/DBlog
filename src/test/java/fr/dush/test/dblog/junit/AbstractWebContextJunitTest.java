package fr.dush.test.dblog.junit;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:WEB-INF/spring/web-session-scopes.xml" })
public abstract class AbstractWebContextJunitTest extends AbstractJunitTest {

}
