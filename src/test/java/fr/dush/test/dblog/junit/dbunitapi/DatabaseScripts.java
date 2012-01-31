package fr.dush.test.dblog.junit.dbunitapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To be used with test methods or classes on a {@link AbstractTestHsql}
 * Sample use : @DatabaseScripts(locations = {"bdd/custumers.xml"})
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.TYPE})
public @interface DatabaseScripts {
	String[] locations() default {};

	/**
	 * Controls whether locations on a method enrich or replace locations defined on its class
	 */
	boolean inheritLocations() default true;

	/**
	 * Used only on method : drop the database's final contents in "target/bdd/the_date.xml".
	 */
	boolean dumpDatabase() default false;

}
