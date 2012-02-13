package fr.dush.test.dblog.services;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.internal.util.config.ConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import fr.dush.test.dblog.dto.i18n.AvailableLocale;

/**
 * Liste les locales disponible pour l'application.
 *
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
@Named("i18nManager")
public class I18nManagerImpl implements II18nManager {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(I18nManagerImpl.class);

	private Set<AvailableLocale> availableLocales = new HashSet<>();

	@Inject
	public I18nManagerImpl(@Value("${directory.i18n}") final String localesDir, final ResourceLoader loader) throws IOException {
		final Resource i18nRessource = loader.getResource("classpath:" + localesDir);
		logger.debug("Scan {} for locales resources.", i18nRessource);

		if (!i18nRessource.getFile().exists()) {
			throw new ConfigurationException("Directory " + i18nRessource + " for locales not found.");
		}

		loadAvailableLocales(i18nRessource.getFile());
	}

	/**
	 * Revoie les langues disponibles pour l'application
	 * @return
	 */
	@Override
	public Set<AvailableLocale> getAvailableLocales() {
		return availableLocales;
	}

	/**
	 * Charge les fichiers de langue trouv�.
	 * @param i18nDir
	 * @throws IOException
	 */
	private void loadAvailableLocales(final File i18nDir) throws IOException {
		final File[] locales = i18nDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(final File pathname) {
				return "properties".equals(FilenameUtils.getExtension(pathname.getAbsolutePath()));
			}
		});

		for (final File f : locales) {
			availableLocales.add(convertAvailableLocale(f));
		}
	}

	/**
	 * Convertit le fichier de langue un contexte (langue, pays, nom du contexte) disponible.
	 * @param langFile fichier de langue
	 * @return Locale disponible.
	 * @throws IOException
	 */
	private AvailableLocale convertAvailableLocale(final File langFile) throws IOException {
		final Properties props = new Properties();
		props.load(new FileReader(langFile));

		final AvailableLocale locale = new AvailableLocale();
		locale.setIcon(props.getProperty("icon"));
		locale.setLocale(new Locale(props.getProperty("locale")));
		locale.setName(props.getProperty("name"));

		return locale;
	}

}