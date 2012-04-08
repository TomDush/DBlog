package fr.dush.test.dblog.controller;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.engine.AbstractSimpleSpringJunitTest;

public class I18nControllerTest extends AbstractSimpleSpringJunitTest {

	@Inject
	private I18nController i18nController;

	@Test
	public void testGetAvailableLocalesList() {
		final AvailableLocale[] locales = i18nController.getAvailableLocalesList();
		assertNotNull(locales);
		assertEquals(2, locales.length);

		for(final AvailableLocale l : locales) {
			assertNotNull(l.getLocale());
			assertTrue(l.getFullLocale() + " is invalid.", "fr_FR".equals(l.getFullLocale()) || "en_US".equals(l.getFullLocale()));
			assertTrue(l.getFullLocale() + " is invalid.", l.getIcon().endsWith(".png"));
		}
	}
}
