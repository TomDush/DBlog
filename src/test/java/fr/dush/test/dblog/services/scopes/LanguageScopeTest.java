package fr.dush.test.dblog.services.scopes;

import java.util.Locale;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.controller.I18nController;
import fr.dush.test.dblog.engine.AbstractWebContextJunitTest;

public class LanguageScopeTest extends AbstractWebContextJunitTest {

	@Inject
	private ApplicationContext context;

	@Inject
	private I18nController i18nController;

	@Test
	@Ignore
	public void testGet() {
		// TODO MOCKER le SpringContext et implémenter la méthode
		fail("Not yet implemented");
	}

	@Test
	public void testScopeEnGeneral() throws Exception {
		assertNotNull(context);
		i18nController.setLocale(new Locale("fr", "FR"));

		final BeanLanguageScoped beanLanguageScoped1 = context.getBean(BeanLanguageScoped.class);
		assertNotNull(beanLanguageScoped1);

		final BeanLanguageScoped beanLanguageScoped2 = context.getBean(BeanLanguageScoped.class);
		assertNotNull(beanLanguageScoped2);

		assertEquals(beanLanguageScoped1, beanLanguageScoped2);
		assertTrue(beanLanguageScoped1 == beanLanguageScoped2);
	}

	@Test
	public void testLocale() throws Exception {
		final Locale l1 = new Locale("fr");
		final Locale l2 = new Locale("fr", "FR");
		final Locale l3 = new Locale("fr", "fr");
		final Locale l4 = new Locale("fr", "BE");
		final Locale l5 = new Locale("en", "GB");

		assertEquals(l1.getISO3Language(), l2.getISO3Language());
		assertEquals(l1.getISO3Language(), l3.getISO3Language());
		assertEquals(l2.getISO3Language(), l3.getISO3Language());

		assertEquals(l1.getISO3Language(), l4.getISO3Language());
		assertEquals(l2.getISO3Language(), l4.getISO3Language());

		assertFalse(l1.getISO3Language().equals(l5.getISO3Language()));

	}

}
