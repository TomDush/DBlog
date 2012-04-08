package fr.dush.test.dblog.engine;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Super classe de base pour les tests JUNIT. Comprend :
 * <ul>
 * <li>Injection des dépendances SPRING et configuration <i>main</i></li>
 * <li>Mock du controlle i18n : mode thread pour permettre les tests multi-langue</li>
 * </ul>
 *
 * @author Thomas Duchatelle (duchatelle.thomas@gmail.com)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:WEB-INF/spring/context-global.xml",
		"classpath:WEB-INF/spring/context-persistence.xml",
		"classpath:WEB-INF/spring/context-scope.xml",
		"classpath:WEB-INF/spring/mock-controller.xml" })
public abstract class AbstractSimpleSpringJunitTest {

}
