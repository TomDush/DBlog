package fr.dush.test.dblog.services;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;
import fr.dush.test.dblog.engine.AbstractSimpleSpringJunitTest;

public class I18nManagerImplTest extends AbstractSimpleSpringJunitTest {

	@Inject
	private I18nManagerImpl i18nManager;

	@Test
	public void testGetAvailableLocales() {
		final Collection<AvailableLocale> locales = i18nManager.getAvailableLocales();
		assertNotNull(locales);
		assertEquals(2, locales.size());
	}
}
