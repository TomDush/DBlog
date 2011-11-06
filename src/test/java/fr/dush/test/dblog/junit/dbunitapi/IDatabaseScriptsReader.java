package fr.dush.test.dblog.junit.dbunitapi;

import java.util.List;

public interface IDatabaseScriptsReader {

	void setDatabasePopulationScripts(List<String> databaseScripts);

	void setDropDatabase(boolean dropDatabase);

}
