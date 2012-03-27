package fr.dush.test.dblog.controller;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.engine.AbstractWebContextJunitTest;

public class I18nControllerTest extends AbstractWebContextJunitTest {

	@Inject
	private I18nController i18nController;

	@Before
	public void initScope() {
		startSession();
	}

	@Test
	public void testGetAvailableLocalesList() {
		final AvailableLocale[] locales = i18nController.getAvailableLocalesList();
		assertNotNull(locales);
		assertEquals(2, locales.length);

		for(final AvailableLocale l : locales) {
			assertNotNull(l.getLocale());
			assertTrue(l.getFullLocale() + " is invalid.", "fr_fr".equals(l.getFullLocale()) || "en_us".equals(l.getFullLocale()));
			assertTrue(l.getFullLocale() + " is invalid.", l.getIcon().endsWith(".png"));
		}
	}
}
