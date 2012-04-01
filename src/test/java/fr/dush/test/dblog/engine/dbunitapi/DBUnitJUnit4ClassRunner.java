package fr.dush.test.dblog.engine.dbunitapi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Runner JUNIT permettant la lecture des annotations chargeant ou déchargeant la base de données.
 *
 *
 * @author Thomas Duchatelle (thomas.duchatelle@capgemini.com)
 */
public class DBUnitJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBUnitJUnit4ClassRunner.class);

	public DBUnitJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod frameworkMethod, Object obj) {
		// List of all annotation found in method/class hierarchy.
		final ArrayDeque<DatabaseScripts> dbScriptsAnnotations = new ArrayDeque<>();

		// Retrieve the database population scripts location if the test class is annotated
		final DatabaseScripts methodAnnotation = frameworkMethod.getAnnotation(DatabaseScripts.class);
		boolean dumpDatabase = false;
		if (methodAnnotation != null) {
			dbScriptsAnnotations.push(methodAnnotation);
			dumpDatabase = methodAnnotation.dumpDatabase();
		}

		// Retrieve the database population scripts location if the test class or its super-classes is annotated
		@SuppressWarnings("rawtypes")
		Class testClass = obj.getClass();
		do {
			@SuppressWarnings("unchecked")
			final
			DatabaseScripts classAnnotation = (DatabaseScripts) testClass.getAnnotation(DatabaseScripts.class);
			if (classAnnotation != null) {
				dbScriptsAnnotations.push(classAnnotation);
			}

			testClass = testClass.getSuperclass();
		} while (testClass != null);

		// Build the locations list, starting from the top-level superclass down to the running class, then the method
		final List<String> locationsList = new ArrayList<>();

		while (!dbScriptsAnnotations.isEmpty()) {
			final DatabaseScripts ds = dbScriptsAnnotations.pop();
			final String[] locations = ds.locations();

			if (!ds.inheritLocations()) {
				locationsList.clear();
			}
			for (final String location : locations) {
				locationsList.add(location);
			}
		}
		logger.debug("There are {} database scripts for method {}.{}.", new Object[] { locationsList.size(),
				frameworkMethod.getClass().getName(), frameworkMethod.getMethod().getName() });
		logger.debug("This scripts are : {}", locationsList);

		// Set (or erase) the database scripts location for this method
		if (obj instanceof IDatabaseScriptsReader) {
			((IDatabaseScriptsReader) obj).setDatabasePopulationScripts(locationsList);
			((IDatabaseScriptsReader) obj).setDumpDatabase(dumpDatabase);
			((IDatabaseScriptsReader) obj).setDumpFilename(frameworkMethod.getMethod().getName());

		} else throw new RuntimeException("Test class must implement IDatabaseScriptsReader to use @DatabaseScripts.");

		// Method execution
		return super.methodInvoker(frameworkMethod, obj);
	}
}
