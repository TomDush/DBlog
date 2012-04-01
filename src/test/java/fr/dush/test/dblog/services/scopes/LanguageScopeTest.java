package fr.dush.test.dblog.services.scopes;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import fr.dush.test.dblog.controller.I18nController;
import fr.dush.test.dblog.engine.AbstractWebContextJunitTest;
import fr.dush.test.dblog.engine.mock.context.ContextLocatorMock;

public class LanguageScopeTest extends AbstractWebContextJunitTest {

	@Inject
	private ApplicationContext context;

	@Inject
	private I18nController i18nController;

	// TODO Utiliser ce mock dans tous les TU
	private final ContextLocatorMock contextLocator = new ContextLocatorMock();

	@Test
	public void testGet() {
		// le test repose sur la comparaison des références d'objet
		assertFalse(new Integer(3) == new Integer(3));

		final LanguageScope scope = new LanguageScope();
		scope.setContextLocator(contextLocator);

		final ObjectFactory<Integer> factory = new ObjectFactory<Integer>() {

			@Override
			public Integer getObject() throws BeansException {
				return new Integer(42);
			}
		};

		// Test du GET et RESOLVE
		final Integer i1 = (Integer) scope.get("int", factory);
		final Integer i2 = (Integer) scope.get("int", factory);

		assertTrue(i1 == i2);
		assertTrue(i1 == scope.resolveContextualObject("int"));

		contextLocator.setLocale(new Locale("en"));
		final Integer i3 = (Integer) scope.get("int", factory);
		final Integer i4 = (Integer) scope.get("int", factory);
		assertTrue(i3 == i4);
		assertTrue(i3 == scope.resolveContextualObject("int"));
		assertFalse(i1 == i3);

		contextLocator.setLocale(new Locale("fr"));
		final Integer i5 = (Integer) scope.get("int", factory);
		assertTrue(i1 == i5);
		assertFalse(i3 == i5);

		// Test remove + callback
		final Set<String> callbackForContext = new HashSet<>();
		scope.registerDestructionCallback("int", new Runnable() {

			@Override
			public void run() {
				callbackForContext.add(contextLocator.getLanguageKey());
			}
		});

		assertTrue(i1 == scope.remove("int"));
		assertNull(scope.resolveContextualObject("int"));
		assertFalse(i1 == scope.get("int", factory));

		contextLocator.setLocale(new Locale("en"));
		assertTrue(i3 == scope.remove("int"));
		assertNull(scope.resolveContextualObject("int"));
		assertFalse(i3 == scope.get("int", factory));

		assertEquals(1, callbackForContext.size());
		assertTrue(callbackForContext.contains("fra"));
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
