package fr.dush.test.dblog.junit.dbunitapi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class DBUnitJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBUnitJUnit4ClassRunner.class);

	public DBUnitJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Statement methodInvoker(FrameworkMethod frameworkMethod, Object obj) {
		ArrayDeque<DatabaseScripts> dsStack = new ArrayDeque<>();

		// Retrieve the database population scripts location if the test class is annotated
		DatabaseScripts dsMethod = frameworkMethod.getAnnotation(DatabaseScripts.class);
		boolean dumpDatabe = false;
		if (dsMethod != null) {
			dsStack.push(dsMethod);
			dumpDatabe = dsMethod.dumpDatabase();
		}

		// Retrieve the database population scripts location if the test class or its super-classes is annotated
		@SuppressWarnings("rawtypes")
		Class testClass = obj.getClass();
		do {
			@SuppressWarnings("unchecked")
			DatabaseScripts dsClass = (DatabaseScripts) testClass.getAnnotation(DatabaseScripts.class);
			if (dsClass != null) {
				dsStack.push(dsClass);
			}

			testClass = testClass.getSuperclass();
		} while (testClass != null);

		// Build the locations list, starting from the top-level superclass down to the running class, then the method
		List<String> locationsList = new ArrayList<>();

		while (!dsStack.isEmpty()) {
			DatabaseScripts ds = dsStack.pop();
			String[] locations = ds.locations();

			if (!ds.inheritLocations()) {
				locationsList.clear();
			}
			for (String location : locations) {
				locationsList.add(location);
			}
		}
		logger.debug("There are {} database script.", locationsList.size());

		// Set (or erase) the database scripts location for this method
		if (obj instanceof IDatabaseScriptsReader) {
			((IDatabaseScriptsReader) obj).setDatabasePopulationScripts(locationsList);
			((IDatabaseScriptsReader) obj).setDumpDatabase(dumpDatabe);
		} else throw new RuntimeException("Test class must implement IDatabaseScriptsReader to use @DatabaseScripts.");

		return super.methodInvoker(frameworkMethod, obj);
	}
}
